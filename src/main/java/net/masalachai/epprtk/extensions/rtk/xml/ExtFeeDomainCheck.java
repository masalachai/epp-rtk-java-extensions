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

import static com.tucows.oxrs.epprtk.rtk.RTKBase.DEBUG_LEVEL_THREE;
import static com.tucows.oxrs.epprtk.rtk.RTKBase.DEBUG_LEVEL_TWO;
import com.tucows.oxrs.epprtk.rtk.xml.EPPXMLBase;
import java.io.IOException;
import org.apache.xerces.dom.DocumentImpl;
import org.openrtk.idl.epprtk.epp_Extension;
import org.openrtk.idl.epprtk.epp_XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExtFeeDomainCheck extends EPPXMLBase implements epp_Extension {

	protected static final String prefix = "fee";

	private String cd_cmd_ = "check";
	public ExtFeeDomain[] m_domains = null;

	public ExtFeeDomainCheck() {
		cd_cmd_ = "check";
	}

	public ExtFeeDomainCheck(String xml) throws epp_XMLException {
		String method_name = "ExtFeeLaunchCheck(String)";
		debug(DEBUG_LEVEL_TWO, method_name, "xml is [" + xml + "]");
		fromXML(xml);
	}

	public String getCommand() {
		return cd_cmd_;
	}

	public String toXML() throws epp_XMLException {
		String method_name = "toXML()";
		debug(DEBUG_LEVEL_THREE, method_name, "Entered");
		
		if(m_domains == null)
			throw new epp_XMLException("No fee domain data");

		if (cd_cmd_ == null) {
			throw new epp_XMLException("No launch extension command");
		}

		Document doc = new DocumentImpl();

		Element tmp = doc.createElementNS("urn:ietf:params:xml:ns:fee-0.6", prefix + ":" + cd_cmd_);

		tmp.setAttribute("xmlns:" + prefix, "urn:ietf:params:xml:ns:fee-0.6");
		tmp.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		tmp.setAttribute("xsi:schemaLocation", "urn:ietf:params:xml:ns:fee-0.6 fee-0.6.xsd");

		for(int i = 0; i < m_domains.length; ++i) {
			ExtFeeDomain m_domain = m_domains[i];
			
			if(m_domain == null || m_domain.m_period == null)
				continue;
			
			Element phaseElement = doc.createElement(prefix + ":domain");

			Element domainName = doc.createElement(prefix + ":name");
			domainName.setTextContent(m_domain.m_name);

			phaseElement.appendChild(domainName);

			Element currency = doc.createElement(prefix + ":currency");
			currency.setTextContent(m_domain.m_currency);

			phaseElement.appendChild(currency);

			Element command = doc.createElement(prefix + ":command");
			command.setTextContent(m_domain.m_cmd);
			
			if(m_domain.m_cmd_phase != null) {
				command.setAttribute("phase", m_domain.m_cmd_phase);
			}

			phaseElement.appendChild(command);

			Element period = doc.createElement(prefix + ":period");
			period.setTextContent(String.valueOf(m_domain.m_period.m_value));
			period.setAttribute("unit", m_domain.m_period.m_unit.toString());

			phaseElement.appendChild(period);

			tmp.appendChild(phaseElement);
		}
		
		doc.appendChild(tmp);

		String resXml;

		try {
			resXml = createXMLSnippetFromDoc(doc);
		} catch (IOException xcp) {
			throw new epp_XMLException("IOException in building XML [" + xcp.getMessage() + "]");
		}

		debug(DEBUG_LEVEL_THREE, method_name, "Leaving");

		return resXml;
	}

	public void fromXML(String xml) throws epp_XMLException {
		
	}
}
