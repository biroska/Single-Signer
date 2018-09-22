package br.com.experian.single.signer.rest.api;

import br.com.experian.single.signer.rest.model.*;

import io.swagger.annotations.ApiParam;
//import io.swagger.jaxrs.*;

import br.com.experian.single.signer.rest.model.Resumo;

import java.util.List;

import java.io.InputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/debtssummaries")


public interface DebtssummariesApi  {

    @GET
    @Path("/{companyId}")
    
    @Produces({ "application/json" })
    public Response retrieveDebtsSummaries(@ApiParam(value = "Content-Type field is to describe the data contained in the body." ,required=true, defaultValue="application/json")@HeaderParam("Content-Type") String contentType, @ApiParam(value = "The client sends the access token preceded by Bearer." ,required=true)@HeaderParam("Authorization") String authorization, @ApiParam(value = "Company ID",required=true) @PathParam("companyId") Long companyId);
}
