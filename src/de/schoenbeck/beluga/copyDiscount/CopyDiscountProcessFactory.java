/******************************************************************************
 * Copyright (C) 2019 Martin Sch�nbeck Beratungen GmbH                        *
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

import org.adempiere.base.IProcessFactory;
import org.compiere.process.ProcessCall;

public class CopyDiscountProcessFactory implements IProcessFactory {

	@Override
	public ProcessCall newProcessInstance(String className) {
		if ("de.schoenbeck.beluga.copyDiscount.CopyDiscountProcess".equals(className)) {
			return new CopyDiscountProcess();
			
		}
		return null;
	}

}
