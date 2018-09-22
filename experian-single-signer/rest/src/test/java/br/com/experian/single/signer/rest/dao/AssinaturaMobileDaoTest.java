package br.com.experian.single.signer.rest.dao;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.experian.single.signer.core.model.AssinaturaMobile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContextTest.xml"})
public class AssinaturaMobileDaoTest {
	
	@Autowired
	private AssinaturaMobileDao dao;

	public void setDao(AssinaturaMobileDao dao) {
		this.dao = dao;
	}
	
	@Test
	public void testGetIdentificacaoMobile( ){
		
		Assert.assertNotNull( dao );
		
		AssinaturaMobile assMobile = new AssinaturaMobile();

		assMobile.setIdRequisicaoAssinaturaMob( "1" );
		assMobile.setIdIdentificacaoMobile( "17" );
		
		List<AssinaturaMobile> listAssMobile = dao.getAssinaturaMobile( assMobile );
		
		Assert.assertFalse( listAssMobile.isEmpty() );
	}
	
	@Test
	public void testInserirIdentificacaoMobileGeraSolicitacao( ){
		
		Assert.assertNotNull( dao );
		
		AssinaturaMobile assMobile = new AssinaturaMobile();
		
		assMobile.setIdIdentificacaoMobile( "17" );
		assMobile.setHashRecebido( RandomStringUtils.randomAlphabetic( 100 ) );
		assMobile.setDtSolicitacao( "06/12/17 16:00:00" );
		
		assMobile = dao.inserirIdentificacaoMobile( assMobile );
		
		Assert.assertNotNull( assMobile.getIdRequisicaoAssinaturaMob() );
	}
	
	@Test
	public void testAtualizarAssinaturaMobileGeraAssinatura( ){
		
		Assert.assertNotNull( dao );
		
		AssinaturaMobile assMobile = new AssinaturaMobile();
		
		assMobile.setIdRequisicaoAssinaturaMob( "7" );
		assMobile.setIdIdentificacaoMobile( "17" );
		assMobile.setHashRecebido( RandomStringUtils.randomAlphabetic( 100 ) );
		assMobile.setDtSolicitacao( "08/12/2017 16:00:00" );
		assMobile.setHashAssinado( RandomStringUtils.randomAlphabetic( 100 ) );
		assMobile.setDtAssinatura( "08/12/2017 16:00:00" );
		
		Long qtdRegsAfetados = dao.atualizarAssinaturaMobile( assMobile );
		
		Assert.assertTrue( qtdRegsAfetados > 0);
	}
}
