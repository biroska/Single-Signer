package br.com.experian.single.signer.rest.dao;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.experian.single.signer.core.model.IdentificacaoMobile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContextTest.xml"})
public class IdentificacaoMobileDaoTest {
	
	@Autowired
	private IdentificacaoMobileDao dao;

	public void setDao(IdentificacaoMobileDao dao) {
		this.dao = dao;
	}
	
	@Test
	public void testGetIdentificacaoMobile( ){
		
		Assert.assertNotNull( dao );
		
		IdentificacaoMobile idMobile = new IdentificacaoMobile();

		idMobile.setIdIdentificacaoMobile( "22" );
		idMobile.setDocumento( "62935426142" );
		idMobile.setHashPush("4649979124000046499791263000");
		
		List<IdentificacaoMobile> listIdentificacaoMobile = dao.getIdentificacaoMobile( idMobile );
		
		Assert.assertFalse( listIdentificacaoMobile.isEmpty() );
	}
	
	@Test
	public void testAtualizarIdentificacaoMobile() {
		
		Assert.assertNotNull( dao );
		
		IdentificacaoMobile idMobile = new IdentificacaoMobile();

		idMobile.setIdIdentificacaoMobile( "14" );
		idMobile.setHashPush( RandomStringUtils.randomAlphabetic( 100 ) );
		idMobile.setDocumento( RandomStringUtils.randomNumeric( 11 ) );
		idMobile.setNoNome( RandomStringUtils.randomAlphabetic( 10 ) + " " + 
		                    RandomStringUtils.randomAlphabetic( 10 ) + " " + 
				            RandomStringUtils.randomAlphabetic( 10 ) );
		idMobile.setEmEmail( RandomStringUtils.randomAlphabetic(10) + "@teste.com"  );
		
		Long qtdRegsAfetados = dao.atualizarIdentificacaoMobile( idMobile );
		
		Assert.assertTrue( qtdRegsAfetados > 0);
		
	}
	
	@Test
	public void testAtualizarIdentificacaoMobileSemEmail() {
		
		Assert.assertNotNull( dao );
		
		IdentificacaoMobile idMobile = new IdentificacaoMobile();
		
		idMobile.setIdIdentificacaoMobile( "14" );
		idMobile.setHashPush( RandomStringUtils.randomAlphabetic( 100 ) );
		idMobile.setDocumento( RandomStringUtils.randomNumeric( 11 ) );
		idMobile.setNoNome( RandomStringUtils.randomAlphabetic( 10 ) + " " + 
				RandomStringUtils.randomAlphabetic( 10 ) + " " + 
				RandomStringUtils.randomAlphabetic( 10 ) );
		idMobile.setEmEmail( null  );
		
		Long qtdRegsAfetados = dao.atualizarIdentificacaoMobile( idMobile );
		
		Assert.assertTrue( qtdRegsAfetados > 0);
	}
	
	@Test
	public void testInserirIdentificacaoMobile() {
		
		Assert.assertNotNull( dao );
		
		IdentificacaoMobile idMobile = new IdentificacaoMobile();
		
		idMobile.setHashPush( RandomStringUtils.randomAlphabetic( 100 ) );
		idMobile.setDocumento( RandomStringUtils.randomNumeric( 11 ) );
		idMobile.setEmEmail( RandomStringUtils.randomAlphabetic(10) + "@teste.com" );
		idMobile.setNoNome( RandomStringUtils.randomAlphabetic( 10 ) + " " + 
							RandomStringUtils.randomAlphabetic( 10 ) + " " + 
							RandomStringUtils.randomAlphabetic( 10 ) );

		idMobile = dao.inserirIdentificacaoMobile( idMobile );
		
		Assert.assertNotNull( idMobile.getIdIdentificacaoMobile() );
		
	}

}
