package br.com.experian.single.signer.rest.api.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.owasp.esapi.CustomLogger;
import org.owasp.esapi.CustomLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.experian.single.signer.core.constants.Constantes;
import br.com.experian.single.signer.rest.api.DebtssummariesApi;
import br.com.experian.single.signer.rest.model.Resumo;


@Component
public class DebtssummariesApiServiceImpl implements DebtssummariesApi {
	
	private CustomLogger logger = CustomLoggerFactory.getLogger(this.getClass().getName());
	
	private static Long SERASA_COMPANY_ID = 62173620000180L;
	
	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public Response retrieveDebtsSummaries(String contentType, String authorization, Long companyId) {
		
		logger.info(">retrieveDebtsSummaries(): " + dataSource );
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parameters: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " contentType: " + contentType + 
					 Constantes.Formatting.TAB_CONFIG_4 + " authorization: " + authorization +
				     Constantes.Formatting.TAB_CONFIG_4 + " companyId: " + companyId );
		
		if(!companyId.equals(SERASA_COMPANY_ID)){
			throw new WebApplicationException(Response.status(500).entity("Invalid Company").build());
		}
		
		List<Resumo> resumos = new ArrayList<Resumo>();
		
		for(int i=0;i<5;i++){
			Resumo resumo = new Resumo();
			resumo.setQuantity(new BigDecimal(5));
			resumo.setType("tipo");
			resumo.setUpdateDate(new Date());
			resumo.setTotal(new BigDecimal(138792357.67));
			resumo.getTotal().setScale(2, RoundingMode.CEILING);
			resumos.add(resumo);
		}
		
		logger.info("<retrieveDebtsSummaries()");
		
		return Response.ok(resumos.toArray(new Resumo[0])).build();
	}
}