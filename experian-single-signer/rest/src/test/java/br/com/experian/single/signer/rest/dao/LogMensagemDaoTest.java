package br.com.experian.single.signer.rest.dao;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.experian.single.signer.core.model.MensagemLog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContextTest.xml"})
public class LogMensagemDaoTest {
	
	@Autowired
	private LogMensagemDao dao;

	public void setDao(LogMensagemDao dao) {
		this.dao = dao;
	}
	
	@Test
	public void testGetMensagemPorId() {
		
		Assert.assertNotNull( dao );
		
		MensagemLog mensagemLog = new MensagemLog();
		mensagemLog.setIdMensagem( "14" );
		
		List<MensagemLog> listMensagemLog = dao.getMensagemLog( mensagemLog, null, null );
		
		Assert.assertFalse( listMensagemLog.isEmpty() );
		
	}
	
	@Test
	public void testGetMensagemPorTipo() {
		
		Assert.assertNotNull( dao );
		
		MensagemLog mensagemLog = new MensagemLog();
		mensagemLog.setTipo( "R" );
		
		List<MensagemLog> listMensagemLog = dao.getMensagemLog( mensagemLog, null, null );
		
		Assert.assertFalse( listMensagemLog.isEmpty() );
		
	}
	
	@Test
	public void testGetMensagemTodosParametros() {
		
		Assert.assertNotNull( dao );
		
		MensagemLog mensagemLog = new MensagemLog();
		mensagemLog.setIdMensagem( "1" );
		mensagemLog.setTipo( "R" );
		mensagemLog.setFuncao( "SIGN CMS" );
		mensagemLog.setCliente( "INTERVALOR" );
		
		String dtIni = "05/12/17 16:00:00";
		String dtFinal = "06/12/17 16:00:00";
		
		List<MensagemLog> listMensagemLog = dao.getMensagemLog( mensagemLog, dtIni, dtFinal );
		
		Assert.assertFalse( listMensagemLog.isEmpty() );
		
	}
	
	@Test
	public void testInserirMensagemLog() {
		
		Assert.assertNotNull( dao );
		
		MensagemLog mensagemLog = new MensagemLog();
		mensagemLog.setTipo( new Random().nextInt( 2 ) == 0 ? "R" : "D" );
		mensagemLog.setFuncao( "SIGN CMS" );
		mensagemLog.setCliente( "INTERVALOR" );
		mensagemLog.setMensagem( "\"[{\"updateDate\": \"2017-11-28T17:08:12Z\",\r\n" + 
								 " \"quantity\": 5,\r\n" + 
								 " \"total\": 138792357.6699999868869781494140625,\r\n" + 
								 " \"type\": \"tipo\" }]\"" );

		mensagemLog = dao.inserirMensagemLog( mensagemLog );
		
		Assert.assertNotNull( mensagemLog.getIdMensagem() );
	}

}
