package br.com.experian.single.signer.core.constants;

public interface Constantes {

	interface Formatting {
		
		public static String TAB_CONFIG_3 = "\n\t\t\t";
		public static String TAB_CONFIG_4 = "\n\t\t\t\t";
	}
	
	interface Certificate {
		
		public static String HEADER_PKCS7 = "-----BEGIN PKCS7-----"; 
		public static String FOOTER_PKCS7 = "-----END PKCS7-----";
		public static String HARD_AUTH = "796fa2b9f60d3e5ee058bf45a2bea58a4a2efbc1a11c93811ddfc8c90cfdce48"; //Experian
		public static String PATH_CMS = "C:\\Desenv\\Projetos\\workspace_sdk_serasa\\SerasaCryptoFoundation\\cmssigner\\src\\test\\resources\\";
		public static String PKCS11_CONFIG_ATR_CODE = "3BF71800008031FE4573667465";
		
		public static String KEY_STORE_PFX = "C:\\\\Desenv\\\\Certificados\\\\e-IDACP\\\\marcel_ghisi_cpf.pfx";
		public static String KEY_STORE_ALIAS = "marcel_ghisi_cpf";
		public static String KEY_STORE_PASS = "123456";
		
		public static String KEY_STORE_P12 = "C:\\\\Desenv\\\\\\\\Certificados\\\\\\\\e-IDACP\\\\\\\\osmar_tonon_cpf.p12";
		public static String KEY_STORE_P12_ALIAS = "osmar_tonon_cpf";
		public static String KEY_STORE_P12_PASS = "123456";
	}
	
	interface DB {
		public static String SCHEMA = "PROD";
		
		public static String RETURN_ALIAS = "return";
		
		public static String SPC_PARAM_XML_MIN = "SPC_PARAM_XML_MIN";
		
		public static String SPC_LOG_MENSAGEM = "SPC_LOG_MENSAGEM";
		public static String SPI_LOG_MENSAGEM = "SPI_LOG_MENSAGEM";
		
		public static String SPC_IDENTIFICACAO_MOBILE = "SPC_IDENTIFICACAO_MOBILE";
		public static String SPI_IDENTIFICACAO_MOBILE = "SPI_IDENTIFICACAO_MOBILE";
		public static String SPU_IDENTIFICACAO_MOBILE = "SPU_IDENTIFICACAO_MOBILE";
		
		public static String SPC_REQUISICAO_ASSINAT_MOBILE = "SPC_REQUISICAO_ASSINAT_MOBILE";
		public static String SPI_REQUISICAO_ASSINAT_MOBILE = "SPI_REQUISICAO_ASSINAT_MOBILE";
		public static String SPU_REQUISICAO_ASSINAT_MOBILE = "SPU_REQUISICAO_ASSINAT_MOBILE";
	}
	
}
