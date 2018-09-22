package br.com.experian.single.signer.rest.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.experian.single.signer.rest.model.ParamXmlMin;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContextTest.xml"})
public class ParametrosDaoTest  {

	@Autowired
	private ParametrosDao dao;

	public void setDao(ParametrosDao dao) {
		this.dao = dao;
	}
	
	@Test
	public void testSayHello() {
		System.out.println("ParametrosDaoTest.testSayHello()");
		Assert.assertNotNull( dao );
		
		ParamXmlMin paramXmlMin = dao.getParameter( "REQUISICAOWS", "controleAcessoIpHabilitado");
		
		Assert.assertNotNull( paramXmlMin );
		
	}
	
}
