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

import static com.tucows.oxrs.epprtk.rtk.RTKBase.DEBUG_LEVEL_ONE;
import static com.tucows.oxrs.epprtk.rtk.RTKBase.DEBUG_LEVEL_THREE;
import static com.tucows.oxrs.epprtk.rtk.RTKBase.DEBUG_LEVEL_TWO;
import com.tucows.oxrs.epprtk.rtk.xml.EPPXMLBase;
import java.io.IOException;
import org.apache.xerces.dom.DocumentImpl;
import org.openrtk.idl.epprtk.epp_Extension;
import org.openrtk.idl.epprtk.epp_XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ritesh
 */
public class ExtLaunchCheckDomain extends EPPXMLBase implements epp_Extension {

	protected static final String prefix = "launch";

	private String cd_cmd_;
	public String m_phase = null;
	public String m_type = null;

	public ExtLaunchCheckDomain() {
		cd_cmd_ = "check";
	}

	public ExtLaunchCheckDomain(String xml) throws epp_XMLException {
		String method_name = "ExtLaunchCheckDomain(String)";
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
                    throw new epp_XMLException("No launch extension command");
		}

		Document doc = new DocumentImpl();

		Element tmp = doc.createElementNS("urn:ietf:params:xml:ns:launch-1.0", prefix + ":check");

		tmp.setAttribute("xmlns:" + prefix, "urn:ietf:params:xml:ns:launch-1.0");
		if(m_type != null)
			tmp.setAttribute("type", m_type);
		
		Element phaseElement = doc.createElement(prefix + ":phase");
		phaseElement.setTextContent(m_phase);
		
		tmp.appendChild(phaseElement);

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

		try {
			if (xml_ == null
					|| xml_.length() == 0) {
				// no xml string to parse
				return;
			}

			Element extension_node = getDocumentElement();

			if (extension_node == null) {
				throw new epp_XMLException("unparsable or missing extension");
			}

			NodeList detail_node_list = extension_node.getChildNodes();

			debug(DEBUG_LEVEL_TWO, method_name, "detail_node_list's node count [" + detail_node_list.getLength() + "]");
			for (int count = 0; count < detail_node_list.getLength(); count++) {
				Node node_ = detail_node_list.item(count);

				if (node_.getNodeName().equals(prefix + ":phase")) {
					m_phase = node_.getTextContent();
				}
			}

		} catch (SAXException xcp) {
			debug(DEBUG_LEVEL_ONE, method_name, xcp);
			throw new epp_XMLException("unable to parse xml [" + xcp.getClass().getName() + "] [" + xcp.getMessage() + "]");
		} catch (IOException xcp) {
			debug(DEBUG_LEVEL_ONE, method_name, xcp);
			throw new epp_XMLException("unable to parse xml [" + xcp.getClass().getName() + "] [" + xcp.getMessage() + "]");
		}
	}

	protected String getInnerXML(String xml) {
		if (xml == null || xml.length() == 0) {
			return xml;
		}

		int indexOfStart = xml.indexOf("<launch:");
		xml = xml.substring(indexOfStart);
		int indexOfEnd = xml.lastIndexOf("</launch:");
		int realIndexOfEnd = xml.indexOf(">", indexOfEnd);
		xml = xml.substring(0, realIndexOfEnd + 1);

		return xml;
	}
}
