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

public class ExtFeeDomainCreate extends EPPXMLBase implements epp_Extension {

	protected static final String prefix = "fee";

	private String cd_cmd_ = "create";
	public String m_currency_ = null;
	public double m_fee_ = -1.0;

	public ExtFeeDomainCreate() {
	}

	public ExtFeeDomainCreate(String xml) throws epp_XMLException {
		String method_name = "ExtFeeDomainCreate(String)";
		debug(DEBUG_LEVEL_TWO, method_name, "xml is [" + xml + "]");
		fromXML(xml);
	}

	public String getCommand() {
		return cd_cmd_;
	}

	public String toXML() throws epp_XMLException {
		String method_name = "toXML()";
		debug(DEBUG_LEVEL_THREE, method_name, "Entered");
		
		if(m_currency_ == null || m_fee_ < 0)
			throw new epp_XMLException("No fee data");

		if (cd_cmd_ == null) {
			throw new epp_XMLException("No launch extension command");
		}

		Document doc = new DocumentImpl();

		Element tmp = doc.createElementNS("urn:ietf:params:xml:ns:fee-0.6", prefix + ":" + cd_cmd_);

		tmp.setAttribute("xmlns:" + prefix, "urn:ietf:params:xml:ns:fee-0.6");
		tmp.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		tmp.setAttribute("xsi:schemaLocation", "urn:ietf:params:xml:ns:fee-0.6 fee-0.6.xsd");

		Element currencyElement = doc.createElement(prefix + ":currency");
		currencyElement.setTextContent(m_currency_);
		
		Element fee = doc.createElement(prefix + ":fee");
		fee.setTextContent(String.valueOf(m_fee_));
		
		tmp.appendChild(currencyElement);
		tmp.appendChild(fee);
		
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
