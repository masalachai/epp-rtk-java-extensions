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
import java.util.ArrayList;
import org.apache.xerces.dom.DocumentImpl;
import org.openrtk.idl.epprtk.contact.epp_ContactNameAddress;
import org.openrtk.idl.epprtk.contact.epp_ContactVoice;
import org.openrtk.idl.epprtk.epp_Extension;
import org.openrtk.idl.epprtk.epp_XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExtUniCreateDomain extends EPPXMLBase implements epp_Extension {

	protected static final String prefix = "urc";

	private String cd_cmd_;
	public epp_ContactNameAddress m_intContact;
	public epp_ContactNameAddress m_locContact;
	public epp_ContactVoice m_voice;
	public epp_ContactVoice m_fax;
	public epp_ContactVoice m_mobile;
	public String m_email;
	public String m_emailAlt;
	public ExtUniSecurityChallenge[] m_securityChallenges;

	public ExtUniCreateDomain() {
		cd_cmd_ = "registrant";
	}

	public ExtUniCreateDomain(String xml) throws epp_XMLException {
		String method_name = "ExtUniCreateDomain(String)";
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

		Element tmp = doc.createElementNS("http://ns.uniregistry.net/centric-1.0", prefix + ":" + cd_cmd_);

		tmp.setAttribute("xmlns:" + prefix, "http://ns.uniregistry.net/centric-1.0");
		tmp.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		
		Element intPostalInfoElement = doc.createElement(prefix + ":postalInfo");
		intPostalInfoElement.setAttribute("type", "int");
		Element intNameElement = doc.createElement(prefix + ":name");
		intNameElement.setTextContent(m_intContact.getName());
		intPostalInfoElement.appendChild(intNameElement);
		if(m_intContact.getOrg() != null) {
			Element intOrgElement = doc.createElement(prefix + ":org");
			intOrgElement.setTextContent(m_intContact.getOrg());
			intPostalInfoElement.appendChild(intOrgElement);
		}
		Element intAddressElement = doc.createElement(prefix + ":addr");
		Element intStreetElement1 = doc.createElement(prefix + ":street");
		intStreetElement1.setTextContent(m_intContact.getAddress().m_street1);
		intAddressElement.appendChild(intStreetElement1);
		if(m_intContact.getAddress().m_street2 != null) {
			Element intStreetElement2 = doc.createElement(prefix + ":street");
			intStreetElement2.setTextContent(m_intContact.getAddress().m_street2);
			intAddressElement.appendChild(intStreetElement2);
		}
		if(m_intContact.getAddress().m_street3 != null) {
			Element intStreetElement3 = doc.createElement(prefix + ":street");
			intStreetElement3.setTextContent(m_intContact.getAddress().m_street3);
			intAddressElement.appendChild(intStreetElement3);
		}
		Element intCityElement = doc.createElement(prefix + ":city");
		intCityElement.setTextContent(m_intContact.getAddress().m_city);
		intAddressElement.appendChild(intCityElement);
		if(m_intContact.getAddress().m_state_province != null) {
			Element intSpElement = doc.createElement(prefix + ":sp");
			intSpElement.setTextContent(m_intContact.getAddress().m_state_province);
			intAddressElement.appendChild(intSpElement);
		}
		if(m_intContact.getAddress().m_postal_code != null) {
			Element intPcElement = doc.createElement(prefix + ":pc");
			intPcElement.setTextContent(m_intContact.getAddress().m_postal_code);
			intAddressElement.appendChild(intPcElement);
		}
		Element intCcElement = doc.createElement(prefix + ":cc");
		intCcElement.setTextContent(m_intContact.getAddress().m_country_code);
		intAddressElement.appendChild(intCcElement);
		
		intPostalInfoElement.appendChild(intAddressElement);
		tmp.appendChild(intPostalInfoElement);
		
		Element locPostalInfoElement = doc.createElement(prefix + ":postalInfo");
		locPostalInfoElement.setAttribute("type", "loc");
		Element locNameElement = doc.createElement(prefix + ":name");
		locNameElement.setTextContent(m_locContact.getName());
		locPostalInfoElement.appendChild(locNameElement);
		if(m_locContact.getOrg() != null) {
			Element locOrgElement = doc.createElement(prefix + ":org");
			locOrgElement.setTextContent(m_locContact.getOrg());
			locPostalInfoElement.appendChild(locOrgElement);
		}
		Element locAddressElement = doc.createElement(prefix + ":addr");
		Element locStreetElement1 = doc.createElement(prefix + ":street");
		locStreetElement1.setTextContent(m_locContact.getAddress().m_street1);
		locAddressElement.appendChild(locStreetElement1);
		if(m_locContact.getAddress().m_street2 != null) {
			Element locStreetElement2 = doc.createElement(prefix + ":street");
			locStreetElement2.setTextContent(m_locContact.getAddress().m_street2);
			locAddressElement.appendChild(locStreetElement2);
		}
		if(m_locContact.getAddress().m_street3 != null) {
			Element locStreetElement3 = doc.createElement(prefix + ":street");
			locStreetElement3.setTextContent(m_locContact.getAddress().m_street3);
			locAddressElement.appendChild(locStreetElement3);
		}
		Element locCityElement = doc.createElement(prefix + ":city");
		locCityElement.setTextContent(m_locContact.getAddress().m_city);
		locAddressElement.appendChild(locCityElement);
		if(m_locContact.getAddress().m_state_province != null) {
			Element locSpElement = doc.createElement(prefix + ":sp");
			locSpElement.setTextContent(m_locContact.getAddress().m_state_province);
			locAddressElement.appendChild(locSpElement);
		}
		if(m_locContact.getAddress().m_postal_code != null) {
			Element locPcElement = doc.createElement(prefix + ":pc");
			locPcElement.setTextContent(m_locContact.getAddress().m_postal_code);
			locAddressElement.appendChild(locPcElement);
		}
		Element locCcElement = doc.createElement(prefix + ":cc");
		locCcElement.setTextContent(m_locContact.getAddress().m_country_code);
		locAddressElement.appendChild(locCcElement);
		
		locPostalInfoElement.appendChild(locAddressElement);
		
		tmp.appendChild(locPostalInfoElement);
		
		if(m_voice != null) {
			Element voiceElement = doc.createElement(prefix + ":voice");
			voiceElement.setTextContent(m_voice.m_value);
			if(m_voice.m_extension != null)
				voiceElement.setAttribute("x", m_voice.m_extension);
			tmp.appendChild(voiceElement);
		}
		
		if(m_voice != null) {
			Element faxElement = doc.createElement(prefix + ":fax");
			faxElement.setTextContent(m_fax.m_value);
			if(m_fax.m_extension != null)
				faxElement.setAttribute("x", m_fax.m_extension);
			tmp.appendChild(faxElement);
		}
		if(m_mobile != null) {
			Element mobileElement = doc.createElement(prefix + ":mobile");
			mobileElement.setTextContent(m_mobile.m_value);
			tmp.appendChild(mobileElement);
		}
		
		Element emailElement = doc.createElement(prefix + ":email");
		emailElement.setTextContent(m_email);
		tmp.appendChild(emailElement);
		Element emailAltElement = doc.createElement(prefix + ":emailAlt");
		emailAltElement.setTextContent(m_emailAlt);
		tmp.appendChild(emailAltElement);
		
		int limit = m_securityChallenges.length > 5 ? 
				5 : m_securityChallenges.length;
		
		
		if(m_securityChallenges.length > 0) {
			Element securityElement = doc.createElement(prefix + ":security");
		
			for(int i = 0; i < limit; ++i) {
				Element secElement = doc.createElement(prefix + ":challenge");
				Element questionElement = doc.createElement(prefix + ":question");
				questionElement.setTextContent(m_securityChallenges[i].getQuestion());
				Element answerElement = doc.createElement(prefix + ":answer");
				answerElement.setTextContent(m_securityChallenges[i].getAnswer());
				secElement.appendChild(questionElement);
				secElement.appendChild(answerElement);

				securityElement.appendChild(secElement);
			}
			tmp.appendChild(securityElement);
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

				ArrayList<ExtUniSecurityChallenge> challenges = new ArrayList<ExtUniSecurityChallenge>();
				if (node_.getNodeName().equals(prefix + ":postalInfo")) {
					NamedNodeMap map = node_.getAttributes();
					epp_ContactNameAddress contact = new epp_ContactNameAddress();
					NodeList contactNodes = node_.getChildNodes();
					for(int k = 0; k < contactNodes.getLength(); ++k) {
						Node contactNode = contactNodes.item(k);
						if(contactNode.getNodeName().equals("name"))
							contact.m_name = contactNode.getNodeValue();
						if(contactNode.getNodeName().equals("org"))
							contact.m_org = contactNode.getNodeValue();
						if(contactNode.getNodeName().equals("addr")) {
							int streetCtr = 1;
							NodeList addrNodes = contactNode.getChildNodes();
							for(int l = 0; l < contactNodes.getLength(); ++l) {
								Node addrNode = addrNodes.item(l);
								if(addrNode.getNodeName().equals("street")) {
									if(streetCtr == 1) {
										contact.m_address.m_street1 = addrNode.getNodeValue();
										++streetCtr;
									} else if(streetCtr == 2) {
										contact.m_address.m_street2 = addrNode.getTextContent();
										++streetCtr;
									} else if(streetCtr == 3) {
										contact.m_address.m_street3 = addrNode.getTextContent();
										++streetCtr;
									}
								}
								if(addrNode.getNodeName().equals("city"))
									contact.m_address.m_city = addrNode.getNodeValue();
								if(addrNode.getNodeName().equals("sp"))
									contact.m_address.m_state_province = addrNode.getNodeValue();
								if(addrNode.getNodeName().equals("pc"))
									contact.m_address.m_postal_code = addrNode.getNodeValue();
								if(addrNode.getNodeName().equals("cc"))
									contact.m_address.m_country_code = addrNode.getNodeValue();
							}
						}
					}
					
					if(map.getNamedItem("type") != null) {
						if(map.getNamedItem("type").getNodeValue().equals("int"))
							m_intContact = contact;
						else if(map.getNamedItem("type").getNodeValue().equals("loc"))
							m_locContact = contact;
					}
				} else if (node_.getNodeName().equals(prefix + ":voice")) {
					m_voice = new epp_ContactVoice();
					m_voice.m_value = node_.getNodeValue();
					NamedNodeMap map = node_.getAttributes();
					if(map.getNamedItem("x") != null)
						m_voice.m_extension = map.getNamedItem("x").getNodeValue();
				} else if (node_.getNodeName().equals(prefix + ":fax")) {
					m_fax = new epp_ContactVoice();
					m_fax.m_value = node_.getNodeValue();
					NamedNodeMap map = node_.getAttributes();
					if(map.getNamedItem("x") != null)
						m_fax.m_extension = map.getNamedItem("x").getNodeValue();
				} else if (node_.getNodeName().equals(prefix + ":mobile")) {
					m_mobile = new epp_ContactVoice();
					m_mobile.m_value = node_.getNodeValue();
				} else if (node_.getNodeName().equals(prefix + ":email")) {
					m_email = node_.getNodeValue();
				} else if (node_.getNodeName().equals(prefix + ":emailAlt")) {
					m_emailAlt = node_.getNodeValue();
				} else if (node_.getNodeName().equals(prefix + ":security")) {
					NodeList challengeNodes = node_.getChildNodes();
					for(int a = 0; a < challengeNodes.getLength(); ++a) {
						Node challengeNode = challengeNodes.item(a);
						if(challengeNode.getNodeName().equals(prefix + ":challenge")) {
							NodeList chNodes = node_.getChildNodes();
							String question = null;
							String answer = null;
							for (int count1 = 0; count1 < chNodes.getLength(); count1++) {
								Node a_node = chNodes.item(count1);

								if (a_node.getNodeName().equals(prefix + ":question")) {
									question = a_node.getNodeValue();
								} else if (a_node.getNodeName().equals(prefix + ":answer")) {
									answer = a_node.getNodeValue();
								}
							}
							if(question != null && answer != null)
								challenges.add(new ExtUniSecurityChallenge(question, answer));
						}
					}
				}
				
				if(challenges.size() > 0) {
					m_securityChallenges = new ExtUniSecurityChallenge[challenges.size()];
					challenges.toArray(m_securityChallenges);
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

		int indexOfStart = xml.indexOf("<" + prefix + ":");
		xml = xml.substring(indexOfStart);
		int indexOfEnd = xml.lastIndexOf("</" + prefix + ":");
		int realIndexOfEnd = xml.indexOf(">", indexOfEnd);
		xml = xml.substring(0, realIndexOfEnd + 1);

		return xml;
	}
}
