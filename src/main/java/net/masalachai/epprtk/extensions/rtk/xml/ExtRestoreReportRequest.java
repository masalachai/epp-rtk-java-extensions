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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import javax.xml.bind.DatatypeConverter;
import org.apache.xerces.dom.DocumentImpl;
import org.openrtk.idl.epprtk.epp_Extension;
import org.openrtk.idl.epprtk.epp_XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * EPP extension to generate and send restore report
 */
public class ExtRestoreReportRequest extends EPPXMLBase implements epp_Extension {

	private static final String prefix = "rgp:";

	private String cd_cmd_;
	protected ExtRestoreReport report;

	/**
	 * Default constructor
	 */
	public ExtRestoreReportRequest() {
	}

	/**
	 * Constructor with restore EPP XML to parse.
	 *
	 * @param xml The EPP Contact Info response XML String
	 * @throws org.openrtk.idl.epprtk.epp_XMLException if the response XML is
	 * not parsable or does not contain the expected data
	 * @see #fromXML(String)
	 */
	public ExtRestoreReportRequest(String xml) throws epp_XMLException {
		String method_name = "ExtRestoreRequest(String)";
		debug(DEBUG_LEVEL_TWO, method_name, "xml is [" + xml + "]");
		fromXML(xml);
	}

	/**
	 * Constructor with restore object for initialization.
	 *
	 * @param rep	the (@link package net.masalachai.epprtk.extensions.rtk.xml.ExtRestoreReport) object
	 *				containing the restore data
	 * @throws org.openrtk.idl.epprtk.epp_XMLException if the response XML is
	 * not parsable or does not contain the expected data
	 * @see #fromXML(String)
	 */
	public ExtRestoreReportRequest(ExtRestoreReport rep) throws epp_XMLException {
		String method_name = "ExtRestoreRequest(ExtRestoreReport)";
		debug(DEBUG_LEVEL_TWO, method_name, "");
		this.report = rep;
		cd_cmd_ = "update";
		toXML();
	}

	//public void setCommand(String command)  { cd_cmd_ = command; }
	/**
	 * Returns the XML command string
	 *
	 * @return	the xml command string
	 */
	public String getCommand() {
		return cd_cmd_;
	}
	
	protected void appendTextElement(Document doc, Element elem, String tag, String value) {
		Element newElement = doc.createElement(tag);
		if(value != null && value.length() > 0) {
			Text textNode = doc.createTextNode(value);
			newElement.appendChild(textNode);
		}
		elem.appendChild(newElement);
	}

	/**
	 * Converts the (@link package net.masalachai.epprtk.extensions.rtk.xml.ExtRestoreReport) object to be
	 * put into the extension section of the request. Implemented method from
	 * org.openrtk.idl.epprtk.epp_Unspec interface.
	 *
	 * @throws org.openrtk.idl.epprtk.epp_XMLException if required data is
	 * missing
	 * @see org.openrtk.idl.epprtk.epp_Extension
	 */
	public String toXML() throws epp_XMLException {
		String method_name = "toXML()";
		debug(DEBUG_LEVEL_THREE, method_name, "Entered");

		if (cd_cmd_ == null) {
			throw new epp_XMLException("RGP Restore command");
		}

		Document doc = new DocumentImpl();

		Element tmp = doc.createElement(prefix + cd_cmd_);

		tmp.setAttribute("xmlns:rgp", "urn:ietf:params:xml:ns:rgp-1.0");
		tmp.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		tmp.setAttribute("xsi:schemaLocation", "urn:ietf:params:xml:ns:rgp-1.0 rgp-1.0.xsd");

		Element rest = doc.createElement(prefix + "restore");

		if (report != null && cd_cmd_.equals("update")) {
			rest.setAttribute("op", "report");
			Element restReport = doc.createElement(prefix + "report");
			
			appendTextElement(doc, restReport, prefix + "preData", report.getPreDeleteWhois());
			appendTextElement(doc, restReport, prefix + "postData", report.getPostDeleteWhois());
			appendTextElement(doc, restReport, prefix + "resReason", report.getReason());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			TimeZone tz = TimeZone.getTimeZone("UTC");
			df.setTimeZone(tz);
			String delTime = df.format(report.getDelDate());
			String resTime = df.format(report.getResDate());
			appendTextElement(doc, restReport, prefix + "delTime", delTime);
			appendTextElement(doc, restReport, prefix + "resTime", resTime);
			appendTextElement(doc, restReport, prefix + "statement", report.getStatement1());
			appendTextElement(doc, restReport, prefix + "statement", report.getStatement2());
			if (report.getOther() != null) {
				appendTextElement(doc, restReport, prefix + "other", report.getOther());
			}
			rest.appendChild(restReport);
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
	 * Parses an XML String of restore report data from the extension section of
	 * a response from the Registry. Implemented method from
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
				if (!opAttr.equals("report")) {
					throw new epp_XMLException("no restore child elements");
				}
			}

			debug(DEBUG_LEVEL_TWO, method_name, "detail_node_list's node count [" + detail_node_list.getLength() + "]");
			int stmtCtr = 1;
			for (int count = 0; count < detail_node_list.getLength(); count++) {
				Node node_ = detail_node_list.item(count);

				if (node_.getNodeName().equals(prefix + "report")) {
					NodeList cedD = node_.getChildNodes();
					report = new ExtRestoreReport();
					for (int count1 = 0; count1 < cedD.getLength(); count1++) {
						Node a_node = cedD.item(count1);

						if (a_node.getNodeName().equals(prefix + "preData") && a_node.getFirstChild() != null) {
							report.setPreDeleteWhois(a_node.getFirstChild().getNodeValue());
						}
						if (a_node.getNodeName().equals(prefix + "postData") && a_node.getFirstChild() != null) {
							report.setPostDeleteWhois(a_node.getFirstChild().getNodeValue());
						}
						if (a_node.getNodeName().equals(prefix + "delTime") && a_node.getFirstChild() != null) {
							String delDate = a_node.getFirstChild().getNodeValue();
							Calendar cl = DatatypeConverter.parseDateTime(delDate);
							report.setDelDate(cl.getTime());
						}
						if (a_node.getNodeName().equals(prefix + "resTime") && a_node.getFirstChild() != null) {
							String delDate = a_node.getFirstChild().getNodeValue();
							Calendar cl = DatatypeConverter.parseDateTime(delDate);
							report.setResDate(cl.getTime());
						}
						if (a_node.getNodeName().equals(prefix + "resReason") && a_node.getFirstChild() != null) {
							report.setReason(a_node.getFirstChild().getNodeValue());
						}
						if (a_node.getNodeName().equals(prefix + "statement") && a_node.getFirstChild() != null) {
							if (stmtCtr == 1) {
								report.setStatement1(a_node.getFirstChild().getNodeValue());
								++stmtCtr;
							} else {
								report.setStatement2(a_node.getFirstChild().getNodeValue());
							}
						}
						if (a_node.getNodeName().equals(prefix + "other") && a_node.getFirstChild() != null) {
							report.setOther(a_node.getFirstChild().getNodeValue());
						}
					}
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
