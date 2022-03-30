/******************************************************************************
 * Copyright (C) 2019 Martin Schönbeck Beratungen GmbH                        *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package de.schoenbeck.beluga.copyDiscount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MOrder;
import org.compiere.model.MProduct;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MDiscountSchemaBreak;
import org.compiere.process.M_Production_Run;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class CopyDiscountProcess extends SvrProcess {
	
	private int copyFrom, copyTo;

	@Override
	protected void prepare() {
		//get Parameters 
		ProcessInfoParameter[] paras = getParameter();
		for (ProcessInfoParameter p : paras) {
			String name = p.getParameterName();
			if (name.equalsIgnoreCase("M_DiscountSchema_ID")) {
				copyFrom = p.getParameterAsInt();
			}else {
				log.severe("Unknown Parameter: " + name);
			}
		}
		copyTo = getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception {
		if (!(copyTo > 0)) {
			throw new AdempiereSystemError(Msg.getMsg(Env.getCtx(), "Notargetdiscountschema"));
		}
		MDiscountSchema source = new MDiscountSchema(getCtx(), copyFrom, get_TrxName());
		if (!(source.getDiscountType().equals(MDiscountSchema.DISCOUNTTYPE_Breaks))) {
			throw new AdempiereSystemError(Msg.getMsg(Env.getCtx(), "Cancopyonlyschemawithbreaks"));
		}
		
		MDiscountSchemaBreak lines[] = source.getBreaks(true);
		MDiscountSchemaBreak target;
		int count = 0;
		for (MDiscountSchemaBreak l :lines) {
			target = new MDiscountSchemaBreak(getCtx(), 0, get_TrxName());
			MDiscountSchemaBreak.copyValues(l, target);
			target.setM_DiscountSchema_ID(copyTo);
			target.saveEx(get_TrxName());
			count++;
		}
		
		return "Copied "+count+" lines.";
	}
}
