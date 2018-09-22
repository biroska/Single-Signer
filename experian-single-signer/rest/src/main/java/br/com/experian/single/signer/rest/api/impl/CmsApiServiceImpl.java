package br.com.experian.single.signer.rest.api.impl;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;

import org.owasp.esapi.CustomLogger;
import org.owasp.esapi.CustomLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.experian.single.signer.core.constants.Constantes;
import br.com.experian.single.signer.rest.api.CmsApi;
import br.com.experian.single.signer.rest.model.Validation;

@Service
public class CmsApiServiceImpl implements CmsApi {
	
	private CustomLogger logger = CustomLoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;

	@Override
	public Response validate(String contentType, String authorization, Validation validationRequest) {

		logger.info(">validate()");
		
		System.out.println("CmsApiServiceImpl.validate(): " + dataSource );
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parameters: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " contentType: " + contentType + 
					 Constantes.Formatting.TAB_CONFIG_4 + " authorization: " + authorization +
				     Constantes.Formatting.TAB_CONFIG_4 + " validationRequest: " + validationRequest );
		
		logger.info("<validate()");

		return Response.status(200).entity( "Valido!!!" ).build();
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
