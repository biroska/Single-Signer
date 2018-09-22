package br.com.experian.single.signer.rest.api;

import br.com.experian.single.signer.rest.model.*;

import io.swagger.annotations.ApiParam;
//import io.swagger.jaxrs.*;

import br.com.experian.single.signer.rest.model.OlaMundoMsg;

import java.util.List;

import java.io.InputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/olaMundo")


public interface OlaMundoApi  {

    @GET
    @Path("/{msg}")
    
    @Produces({ "application/json" })
    public Response getMsg(@ApiParam(value = "Company ID",required=true) @PathParam("msg") String msg);
}
