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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Extension for RGP restore request
 */
public class ExtRestoreRequest extends EPPXMLBase implements epp_Extension {

	private static final String prefix = "rgp:";

	private String cd_cmd_;
	protected ExtRestoreReport report;

	/**
	 * Default constructor
	 */
	public ExtRestoreRequest() {
		cd_cmd_ = "update";
	}

	/**
	 * Constructor with RGP report XML string to automatically parse.
	 *
	 * @param xml The EPP Contact Info response XML String
	 * @throws org.openrtk.idl.epprtk.epp_XMLException if the response XML is
	 * not parsable or does not contain the expected data
	 * @see #fromXML(String)
	 */
	public ExtRestoreRequest(String xml) throws epp_XMLException {
		String method_name = "ExtRestoreRequest(String)";
		debug(DEBUG_LEVEL_TWO, method_name, "xml is [" + xml + "]");
		fromXML(xml);
	}

	/**
	 * Returns the XML command string
	 *
	 * @return	the xml command string
	 */
	public String getCommand() {
		return cd_cmd_;
	}

	/**
	 * Creates the EPP XML extension to be put into the extension section of the
	 * request. Implemented method from org.openrtk.idl.epprtk.epp_Unspec
	 * interface.
	 *
	 * @throws org.openrtk.idl.epprtk.epp_XMLException if required data is
	 * missing
	 * @see org.openrtk.idl.epprtk.epp_Extension
	 */
	public String toXML() throws epp_XMLException {
		String method_name = "toXML()";
		debug(DEBUG_LEVEL_THREE, method_name, "Entered");

		if (cd_cmd_ == null) {
			throw new epp_XMLException("RGP Request command");
		}

		Document doc = new DocumentImpl();

		Element tmp = doc.createElement(prefix + cd_cmd_);

		tmp.setAttribute("xmlns:rgp", "urn:ietf:params:xml:ns:rgp-1.0");
		tmp.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		tmp.setAttribute("xsi:schemaLocation", "urn:ietf:params:xml:ns:rgp-1.0 rgp-1.0.xsd");

		Element rest = doc.createElement(prefix + "restore");

		if (cd_cmd_.equals("update")) {
			rest.setAttribute("op", "request");
		} else {
			throw new epp_XMLException("Invalid restore command " + cd_cmd_);
		}

		tmp.appendChild(rest);

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

	/**
	 * Parses an XML String of RGP request data from the extension section of a
	 * response from the Registry. Implemented method from
	 * org.openrtk.idl.epprtk.epp_Unspec interface.
	 *
	 * @param xml new contactCED Unspec XML String to parse
	 * @throws org.openrtk.idl.epprtk.epp_XMLException if the response XML is
	 * not parsable or does not contain the expected data
	 * @see org.openrtk.idl.epprtk.epp_Action
	 */
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

			if (detail_node_list.getLength() == 0) {
                // no contact child elements
				//throw new epp_XMLException("no contactCED child elements");
				NamedNodeMap attrMap = detail_node_list.item(0).getAttributes();
				Node attrNode = attrMap.getNamedItem("op");
				String opAttr = attrNode.getTextContent();
				if (!opAttr.equals("request")) {
					throw new epp_XMLException("no restore child elements");
				}
				cd_cmd_ = "restore";
			}

		} catch (SAXException xcp) {
			debug(DEBUG_LEVEL_ONE, method_name, xcp);
			throw new epp_XMLException("unable to parse xml [" + xcp.getClass().getName() + "] [" + xcp.getMessage() + "]");
		} catch (IOException xcp) {
			debug(DEBUG_LEVEL_ONE, method_name, xcp);
			throw new epp_XMLException("unable to parse xml [" + xcp.getClass().getName() + "] [" + xcp.getMessage() + "]");
		}
	}

	/**
	 * Get XML from within the RGP extension tags
	 *
	 * @param xml	the xml to be parsed
	 * @return	the inner XML data of the XML string supplied
	 */
	protected String getInnerXML(String xml) {
		if (xml == null || xml.length() == 0) {
			return xml;
		}

		int indexOfStart = xml.indexOf("<rgp:");
		xml = xml.substring(indexOfStart);
		int indexOfEnd = xml.lastIndexOf("</rgp:");
		int realIndexOfEnd = xml.indexOf(">", indexOfEnd);
		xml = xml.substring(0, realIndexOfEnd + 1);

		return xml;
	}
}
