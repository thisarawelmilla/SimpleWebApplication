package com.thisara.web.Controller;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.thisara.web.Database.DatabaseConnection;
import com.thisara.web.Database.Person;
import com.thisara.web.Services.Validate;
import com.thisara.web.Services.Serialize;
import org.xml.sax.SAXException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/test")
public class Controller {

    /*
    @GET
    @Path("/hello")
    public Response hello() {
        return Response.status(200).entity("hello").build();
    }
*/
    // xml validation
    private Validate valid = new Validate();

    @Path("/validate")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response xmlValidate(String data) throws SAXException {
        if (valid.valid(data)) {
            return Response.status(200).entity("Valid Data").build();  //if xml is valide the output will "valid data"

        } else {
            return Response.status(400).entity("Invalid Data").build(); //if xml is not valide the output will "invalid data"
        }

    }


    // convert xml into jason
    @Path("/serialize")
    @POST
    @Consumes("application/xml")
    @Produces("application/json")
    public Response serialize(String data) {
        String n="not";
        if (Serialize.make(data)!= "Error") {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                Person per = xmlMapper.readValue(data, Person.class);
                DatabaseConnection.addToDb(per);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return Response.status(200).entity(Serialize.make(data)).build(); //
        } else {
            return Response.status(400).entity("Invalid Data").build();
        }
    }


}