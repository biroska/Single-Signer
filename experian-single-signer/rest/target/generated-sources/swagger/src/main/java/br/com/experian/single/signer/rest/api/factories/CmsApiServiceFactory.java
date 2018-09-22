package br.com.experian.single.signer.rest.api.factories;

import br.com.experian.single.signer.rest.api.CmsApiService;
import br.com.experian.single.signer.rest.api.impl.CmsApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-09-22T09:31:54.497-03:00")
public class CmsApiServiceFactory {
    private final static CmsApiService service = new CmsApiServiceImpl();

    public static CmsApiService getCmsApi() {
        return service;
    }
}
