package br.com.experian.single.signer.rest.api.factories;

import br.com.experian.single.signer.rest.api.DebtssummariesApiService;
import br.com.experian.single.signer.rest.api.impl.DebtssummariesApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-09-22T09:31:54.497-03:00")
public class DebtssummariesApiServiceFactory {
    private final static DebtssummariesApiService service = new DebtssummariesApiServiceImpl();

    public static DebtssummariesApiService getDebtssummariesApi() {
        return service;
    }
}
