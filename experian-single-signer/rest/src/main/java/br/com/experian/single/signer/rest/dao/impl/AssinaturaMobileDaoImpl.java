package br.com.experian.single.signer.rest.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.owasp.esapi.CustomLogger;
import org.owasp.esapi.CustomLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import br.com.experian.single.signer.core.constants.Constantes;
import br.com.experian.single.signer.core.model.AssinaturaMobile;
import br.com.experian.single.signer.rest.dao.AssinaturaMobileDao;
import br.com.experian.single.signer.rest.util.DbUtil;

@Repository
public class AssinaturaMobileDaoImpl  extends JdbcDaoSupport implements AssinaturaMobileDao {
	
	private CustomLogger logger = CustomLoggerFactory.getLogger( this.getClass().getName() );
	
	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AssinaturaMobile> getAssinaturaMobile( AssinaturaMobile assMobile ){
		
		logger.info(">getAssinaturaMobile()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " AssinaturaMobile: " + assMobile );
		
		if ( assMobile == null ) {
			return null;
		}
		
		if ( StringUtils.isBlank( assMobile.getIdRequisicaoAssinaturaMob() ) &&
			 StringUtils.isBlank( assMobile.getIdIdentificacaoMobile() ) ) {
			return null;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
									.withFunctionName( Constantes.DB.SPC_REQUISICAO_ASSINAT_MOBILE )
									.withSchemaName( Constantes.DB.SCHEMA )
									.returningResultSet("REF CURSOR",
					                                    BeanPropertyRowMapper.newInstance( AssinaturaMobile.class ) );

		MapSqlParameterSource inputs = new MapSqlParameterSource()
				.addValue("P_ID_REQUISICAO_ASSINATURA", NumberUtils.isDigits( assMobile.getIdRequisicaoAssinaturaMob() ) ? Long.valueOf( assMobile.getIdRequisicaoAssinaturaMob() ) : null )
				.addValue("P_ID_IDENTIFICACAO_MOBILE", NumberUtils.isDigits( assMobile.getIdIdentificacaoMobile() ) ? Long.valueOf( assMobile.getIdIdentificacaoMobile() ) : null );
		
		logger.info("realizando a consulta: " + DbUtil.logCallFuncion( jdbcCall, inputs) );
		
		Map<String, Object > cursor = jdbcCall.execute( inputs );
		
		List<AssinaturaMobile> listMensagens = ( List<AssinaturaMobile> ) cursor.get("REF CURSOR");
		
		logger.info("Quantidade de registros retornados: " + listMensagens.size() );
		
		logger.info("<getIdentificacaoMobile()");
		
		return listMensagens;
	}
	
	@Override
	public AssinaturaMobile inserirIdentificacaoMobile( AssinaturaMobile assMobile ){
		
		logger.info(">atualizarIdentificacaoMobile()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " AssinaturaMobile: " + assMobile );
		
		if ( assMobile == null ) {
			return assMobile;
		}

		/*
		 * Campos obrigatórios no DB
		 * 
		 * */
		if ( StringUtils.isBlank( assMobile.getIdIdentificacaoMobile() )  || 
			 StringUtils.isBlank( assMobile.getHashRecebido() )           ||
			 StringUtils.isBlank( assMobile.getDtSolicitacao() ) ) {
			return assMobile;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
										.withFunctionName( Constantes.DB.SPI_REQUISICAO_ASSINAT_MOBILE )
										.withSchemaName( Constantes.DB.SCHEMA )
										.withReturnValue();
		
		MapSqlParameterSource inputs = new MapSqlParameterSource()
									.addValue("P_IDENTIFICACAO_MOBILE", assMobile.getIdIdentificacaoMobile() )
									.addValue("P_HASH_RECEBIDO",        assMobile.getHashRecebido() )
									.addValue("P_DT_SOLICITACAO",       assMobile.getDtSolicitacao() )
									.addValue("P_DT_ASSINATURA",        assMobile.getDtAssinatura() )
									.addValue("P_HASH_ASSINADO",        assMobile.getHashAssinado() );

		logger.info("realizando a consulta: " + DbUtil.logCallFuncion( jdbcCall, inputs) );

		Map<String, Object > cursor = jdbcCall.execute( inputs );

		String id = ( String ) cursor.get( Constantes.DB.RETURN_ALIAS );
		
		assMobile.setIdRequisicaoAssinaturaMob( id );

		logger.info("<atualizarIdentificacaoMobile()");
		
		return assMobile;
	}
	
	@Override
	public Long atualizarAssinaturaMobile( AssinaturaMobile assMobile ){
		
		logger.info(">atualizarAssinaturaMobile()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " IdentificacaoMobile: " + assMobile );
		
		if ( assMobile == null ) {
			return -1L;
		}

		/*
		 * Campos obrigatórios no DB
		 * 
		 * */
		if ( StringUtils.isBlank( assMobile.getIdIdentificacaoMobile() ) ||
			 StringUtils.isBlank( assMobile.getIdIdentificacaoMobile() ) ||
			 StringUtils.isBlank( assMobile.getHashRecebido() )     	 ||
			 StringUtils.isBlank( assMobile.getDtSolicitacao() ) ) {
			return -1L;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
				.withFunctionName( Constantes.DB.SPU_REQUISICAO_ASSINAT_MOBILE )
				.withSchemaName( Constantes.DB.SCHEMA )
				.withReturnValue();

		MapSqlParameterSource inputs = new MapSqlParameterSource()
										.addValue("P_IDENTIFICADOR", 	    assMobile.getIdRequisicaoAssinaturaMob() )
										.addValue("P_IDENTIFICACAO_MOBILE", assMobile.getIdIdentificacaoMobile() )
										.addValue("P_HASH_RECEBIDO",        assMobile.getHashRecebido() )
										.addValue("P_DT_SOLICITACAO",       assMobile.getDtSolicitacao() )
										.addValue("P_DT_ASSINATURA",        assMobile.getDtAssinatura() )
										.addValue("P_HASH_ASSINADO",        assMobile.getHashAssinado() );

		logger.info("executando a function: " + DbUtil.logCallFuncion( jdbcCall, inputs) );

		Map<String, Object > cursor = jdbcCall.execute( inputs );

		BigDecimal qtdRegsAfetados = ( BigDecimal ) cursor.get( Constantes.DB.RETURN_ALIAS );
		
		logger.info("<atualizarAssinaturaMobile()");
		
		return qtdRegsAfetados.longValue();
	}
	
}
