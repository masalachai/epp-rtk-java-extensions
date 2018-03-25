/**
 * EPP RTK Java Extensions
 * Copyright (C) 2018 masalachai.net.

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details. 

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.masalachai.epprtk.extensions.rtk.xml;

import org.openrtk.idl.epprtk.domain.epp_DomainPeriod;

public class ExtFeeDomain {
	public String m_name;
	public String m_currency;
	public String m_cmd;
	public String m_cmd_phase = null;
	public epp_DomainPeriod m_period;
}
