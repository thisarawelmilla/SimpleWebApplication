package com.thisara.web.Services;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

public class Validate {
    public boolean valid(String xml) {
        String xsd = "<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                     "<xs:element name=\"test\">\n" +
                          " <xs:complexType>\n" +
                                  "<xs:sequence>\n" +
                                    "<xs:element type=\"xs:short\" name=\"id\"/>\n" +
                                    "<xs:element type=\"xs:string\" name=\"firstName\"/>\n" +
                                  "</xs:sequence>\n" +
                           "</xs:complexType>\n" +
                      "</xs:element>\n" +
                     "</xs:schema>";
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {

            Schema schema = schemaFactory.newSchema(new StreamSource(new StringReader(xsd)));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));
            return true;
        } catch (SAXException e) {
            return false;
        } catch (IOException e) {
            return false;
        }


    }
}
