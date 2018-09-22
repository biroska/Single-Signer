package br.com.experian.single.signer.rest.api.impl;

import br.com.experian.single.signer.rest.api.*;
import br.com.experian.single.signer.rest.model.*;

import com.sun.jersey.multipart.FormDataParam;

import br.com.experian.single.signer.rest.model.OlaMundoMsg;

import java.util.List;
import br.com.experian.single.signer.rest.api.NotFoundException;

import java.io.InputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-09-22T09:31:54.497-03:00")
public class OlaMundoApiServiceImpl extends OlaMundoApiService {
    @Override
    public Response getMsg(String msg, SecurityContext securityContext)
    throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
