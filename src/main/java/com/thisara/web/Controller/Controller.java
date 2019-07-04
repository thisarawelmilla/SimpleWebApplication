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

    // xml validation against xsd
    private Validate valid = new Validate();

    @Path("/validate")
    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response xmlValidate(String data) {
        if (valid.valid(data)) {
            //if xml is valide the output will "valid data"
            return Response.status(HttpStatus.SC_OK).entity("Valid Data").build();
        } else {
            //if xml is not valide the output will "invalid data"
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("Invalid Data").build();
        }
    }


    // convert xml into jason and add the object to java
    @Path("/serialize")
    @POST
    @Consumes("application/xml")
    @Produces("application/json")
    public Response serialize(String data) {
        String serialOut = Serialize.make(data);
        if (! "Error".equals(serialOut)) {
            // if not error occur during xml to json convertion add object to database.
            try {
                XmlMapper xmlMapper = new XmlMapper();
                Person per = xmlMapper.readValue(data, Person.class);
                DatabaseConnection.addToDb(per);
                return Response.status(HttpStatus.SC_OK).entity(serialOut).build();
            } catch (Exception e) {
                return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity("Unable to save in database").build();
            }
        } else {
            return Response.status(HttpStatus.SC_BAD_REQUEST).entity("Invalid Data").build();
        }
    }


}