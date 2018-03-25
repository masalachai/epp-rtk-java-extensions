/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.masalachai.epprtk.extensions.rtk.xml;

import org.junit.Test;
import static org.junit.Assert.*;
import org.openrtk.idl.epprtk.epp_XMLException;

/**
 *
 * @author ritesh
 */
public class ExtNeuLevelTest {
	
	@Test
	public void shouldGenerateCorrectXmlTag() throws epp_XMLException {
		String tagText = "<neulevel:extension xmlns:neulevel=\"urn:ietf:params:xml:ns:neulevel-1.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:ietf:params:xml:ns:launch-1.0 launch-1.0.xsd\"/>";
		ExtNeuLevel extNeuLevel = new ExtNeuLevel();
		assertEquals(extNeuLevel.toXML(), tagText);
	}
	
}
