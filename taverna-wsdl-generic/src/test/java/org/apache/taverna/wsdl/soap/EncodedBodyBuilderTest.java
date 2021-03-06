/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.apache.taverna.wsdl.soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import org.apache.taverna.wsdl.parser.WSDLParser;
import org.apache.taverna.wsdl.testutils.LocationConstants;
import org.apache.taverna.wsdl.testutils.WSDLTestHelper;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.w3c.dom.Element;

public class EncodedBodyBuilderTest implements LocationConstants {
	
	private String wsdlResourcePath(String wsdlName) throws Exception {
		return WSDLTestHelper.wsdlResourcePath(wsdlName);
	}

	@Test
	public void testSimpleCase() throws Exception {
		Map<String,Object> inputMap = new HashMap<String, Object>();
		
		BodyBuilder builder = createBuilder(wsdlResourcePath("TestServices-rpcencoded.wsdl"), "countString");
		
		assertTrue("Wrong type of builder created",builder instanceof EncodedBodyBuilder);
		
		inputMap.put("str", "Roger Ramjet");
		SOAPElement body = builder.build(inputMap);
                
                Iterator<SOAPElement> children = body.getChildElements();
                
                assertTrue("empty body content", children.hasNext());
                
                SOAPElement child = children.next();
                
                assertTrue("wrong body content (must be '{}str')", "str".equals(child.getLocalName()) && child.getNamespaceURI() == null);
                
                String type = child.getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type");
                assertNotNull("missing xsi:type", type);
                assertTrue("wrong xsi:type", "xsd:string".equals(type));
                assertTrue("wrong text value", "Roger Ramjet".equals(child.getTextContent()));
	}
	
	@Test
	public void testStringArray() throws Exception {
		Map<String,Object> inputMap = new HashMap<String, Object>();
		
		BodyBuilder builder = createBuilder(wsdlResourcePath("TestServices-rpcencoded.wsdl"), "countStringArray");
		
		assertTrue("Wrong type of builder created",builder instanceof EncodedBodyBuilder);
		List<String> array = new ArrayList<String>();
		array.add("one");
		array.add("two");
		array.add("three");
		inputMap.put("array", array);
		SOAPElement body = builder.build(inputMap);
                
                Iterator<SOAPElement> children = body.getChildElements();
                assertTrue("missing body element", children.hasNext());
                SOAPElement child = children.next();
                assertTrue("wrong body element (must be '{}array')", "array".equals(child.getLocalName()) && child.getNamespaceURI() == null);
                String type = child.getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type");
                assertNotNull("missing xsi:type", type);
                assertTrue("wrong xsi:type", "soapenc:Array".equals(type));

                Iterator<Element> elements = child.getChildElements();
                for (int i = 0, n = array.size(); i < n; i++) {
                    assertTrue("missing array element", elements.hasNext());
                    Element element = elements.next();
                    assertTrue("wrong array element (must be '{}string')", "string".equals(element.getLocalName()) && element.getNamespaceURI() == null);
                    assertTrue("wrong array text content", array.get(i).equals(element.getTextContent()));
                }
	}
	
	@Test
	public void testComplexType() throws Exception {
		BodyBuilder builder = createBuilder(wsdlResourcePath("TestServices-rpcencoded.wsdl"), "personToString");
		
		assertTrue("Wrong type of builder created",builder instanceof EncodedBodyBuilder);
		
		String p = "<Person xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><name xsi:type=\"xsd:string\">bob</name><age xsi:type=\"xsd:int\">12</age></Person>";
		
		Map<String,Object> inputMap = new HashMap<String, Object>();
		
		inputMap.put("p",p);
		SOAPElement body = builder.build(inputMap);
		
                Iterator<SOAPElement> persons = body.getChildElements(new QName("", "p"));
                
                assertTrue("'Person' tag is missing", persons.hasNext());
                SOAPElement person = persons.next();
                assertFalse("more than one 'Person' tag found", persons.hasNext());
                
                String personType = person.getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type");
                assertNotNull("type definition of Person is missing", personType);
                assertTrue("wrong xsi:type for the 'Person' tag", "ns1:Person".equals(personType));

                assertTrue("wrong type definition for Person", "ns1:Person".equals(personType));

                Iterator<SOAPElement> names = person.getChildElements(new QName("", "name"));
                
                assertTrue("'name' tag is missing", names.hasNext());
                SOAPElement name = names.next();
                assertFalse("More than one 'name' tag found", names.hasNext());

                String nameType = name.getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type");
                assertNotNull("type definition of name is missing", nameType);
                assertTrue("wrong name's xsi:type", "xsd:string".equals(nameType));
                assertTrue("wrong 'name' tag value (must be 'bob')", "bob".equals(name.getTextContent()));

                Iterator<SOAPElement> ages = person.getChildElements(new QName("", "age"));
                
                assertTrue("'age' tag is missing", ages.hasNext());
                SOAPElement age = ages.next();
                assertFalse("more than one 'age' tag found", ages.hasNext());

                String ageType = age.getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type");
                assertNotNull("missing type definition for the 'age' tag", ageType);
                assertTrue("wrong xsi:type for 'age' tag", "xsd:int".equals(ageType));
                assertTrue("wrong 'age' tag value (must be 12)", "12".equals(age.getTextContent()));
	}
	
	protected BodyBuilder createBuilder(String wsdl, String operation) throws Exception {
		WSDLParser parser = new WSDLParser(wsdl);
		
		return BodyBuilderFactory.instance().create(parser, operation, parser.getOperationInputParameters(operation));
	}
}
