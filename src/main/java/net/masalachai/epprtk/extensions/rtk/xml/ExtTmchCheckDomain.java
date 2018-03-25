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

import com.tucows.oxrs.epprtk.rtk.xml.EPPXMLBase;
import java.io.IOException;
import org.apache.xerces.dom.DocumentImpl;
import org.openrtk.idl.epprtk.epp_Extension;
import org.openrtk.idl.epprtk.epp_XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExtTmchCheckDomain extends EPPXMLBase implements epp_Extension {

	protected static final String prefix = "tmch:";

	private String cd_cmd_;

	public ExtTmchCheckDomain() {
		cd_cmd_ = "check";
	}

	public ExtTmchCheckDomain(String xml) throws epp_XMLException {
		String method_name = "ExtTmchCheckDomain(String)";
		debug(DEBUG_LEVEL_TWO, method_name, "xml is [" + xml + "]");
		fromXML(xml);
	}

	public String getCommand() {
		return cd_cmd_;
	}

	public String toXML() throws epp_XMLException {
		String method_name = "toXML()";
		debug(DEBUG_LEVEL_THREE, method_name, "Entered");

		if (cd_cmd_ == null) {
			throw new epp_XMLException("No tmch extension command");
		}

		Document doc = new DocumentImpl();

		Element tmp = doc.createElementNS("urn:ar:params:xml:ns:tmch-1.0", prefix + "check");

		tmp.setAttribute("xmlns:tmch", "urn:ar:params:xml:ns:tmch-1.0");

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
		String method_name = "fromXML()";
		debug(DEBUG_LEVEL_THREE, method_name, "Entered");

		xml_ = getInnerXML(xml);

		if (xml_ == null
				|| xml_.length() == 0) {
			// no xml string to parse
			return;
		}
	}

	protected String getInnerXML(String xml) {
		if (xml == null || xml.length() == 0) {
			return xml;
		}

		int indexOfStart = xml.indexOf("<tmch:");
		xml = xml.substring(indexOfStart);
		int indexOfEnd = xml.lastIndexOf("</tmch:");
		int realIndexOfEnd = xml.indexOf(">", indexOfEnd);
		xml = xml.substring(0, realIndexOfEnd + 1);

		return xml;
	}
}
