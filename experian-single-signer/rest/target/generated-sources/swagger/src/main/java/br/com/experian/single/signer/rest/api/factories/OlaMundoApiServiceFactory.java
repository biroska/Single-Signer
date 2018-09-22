package br.com.experian.single.signer.rest.api.factories;

import br.com.experian.single.signer.rest.api.OlaMundoApiService;
import br.com.experian.single.signer.rest.api.impl.OlaMundoApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-09-22T09:31:54.497-03:00")
public class OlaMundoApiServiceFactory {
    private final static OlaMundoApiService service = new OlaMundoApiServiceImpl();

    public static OlaMundoApiService getOlaMundoApi() {
        return service;
    }
}
