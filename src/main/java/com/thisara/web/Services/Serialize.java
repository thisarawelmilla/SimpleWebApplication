package com.thisara.web.Services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class Serialize {

    public static String make(String xml)
    {
        String data = xml;

        try
        {
            // Create a new XmlMapper to read XML tags
            XmlMapper xmlMapper = new XmlMapper();

            //Reading the XML
            JsonNode jsonNode = xmlMapper.readTree(data.getBytes());

            //Create a new ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            //Get JSON as a string
            String value = objectMapper.writeValueAsString(jsonNode);

            return (value);


        } catch (JsonParseException e)
        {
            return ("Error");
        } catch (JsonMappingException e)
        {
            return ("Error");
        } catch (IOException e)
        {
            return ("Error");
        }
    }
}