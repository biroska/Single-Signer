package br.com.experian.single.signer.rest.api.impl;

import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.owasp.esapi.CustomLogger;
import org.owasp.esapi.CustomLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import br.com.experian.single.signer.core.util.TesteCore;
import br.com.experian.single.signer.rest.dao.ParametrosDao;

@Component
@Path("/hello")
public class HelloWorldService {
	
	private CustomLogger logger = CustomLoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;
	
	@Autowired
	private TesteCore teste;
	
	@Autowired
	private ParametrosDao paramDao;
	
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		
		logger.info(">getMsg()");

		System.out.println("HelloWorldService.getMsg(): " + dataSource );
		System.out.println("HelloWorldService.getMsg(): " + teste.teste );
		System.out.println("HelloWorldService.getMsg(): " + paramDao.getParameterResultSet( "REQUISICAOWS", "controleAcessoIpHabilitado") );
		System.out.println("HelloWorldService.getMsg(): " + paramDao.getParameter( "REQUISICAOWS", "controleAcessoIpHabilitado") );
		
		
		String output = "Jersey say : " + msg;

		logger.info("<getMsg()");
		
		return Response.status(200).entity(output).build();

	}

	public void setTeste(TesteCore teste) {
		this.teste = teste;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setParamDao(ParametrosDao paramDao) {
		this.paramDao = paramDao;
	}
	
}