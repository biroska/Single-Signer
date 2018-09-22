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
import br.com.experian.single.signer.core.model.IdentificacaoMobile;
import br.com.experian.single.signer.rest.dao.IdentificacaoMobileDao;
import br.com.experian.single.signer.rest.util.DbUtil;

@Repository
public class IdentificacaoMobileDaoImpl extends JdbcDaoSupport implements IdentificacaoMobileDao {
	
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
	public List<IdentificacaoMobile> getIdentificacaoMobile( IdentificacaoMobile idMobile ){
		
		logger.info(">getIdentificacaoMobile()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " IdentificacaoMobile: " + idMobile );
		
		if ( idMobile == null ) {
			return null;
		}
		
		if ( StringUtils.isBlank( idMobile.getIdIdentificacaoMobile() ) &&
			 StringUtils.isBlank( idMobile.getDocumento() )             &&
			 StringUtils.isBlank( idMobile.getHashPush() ) ) {
			return null;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
									.withFunctionName( Constantes.DB.SPC_IDENTIFICACAO_MOBILE )
									.withSchemaName( Constantes.DB.SCHEMA )
									.returningResultSet("REF CURSOR",
					                                    BeanPropertyRowMapper.newInstance( IdentificacaoMobile.class ) );
		
		MapSqlParameterSource inputs = new MapSqlParameterSource()
				.addValue("P_ID_IDENTIFICACAO_MOBILE", NumberUtils.isDigits( idMobile.getIdIdentificacaoMobile() ) ? Long.valueOf( idMobile.getIdIdentificacaoMobile() ) : null )
				.addValue("P_DOCUMENTO", idMobile.getDocumento() )
				.addValue("P_HASH_PUSH", idMobile.getHashPush() );
		
		logger.info("realizando a consulta: " + DbUtil.logCallFuncion( jdbcCall, inputs) );
		
		Map<String, Object > cursor = jdbcCall.execute( inputs );
		
		List<IdentificacaoMobile> listMensagens = ( List<IdentificacaoMobile> ) cursor.get("REF CURSOR");
		
		logger.info("Quantidade de registros retornados: " + listMensagens.size() );
		
		logger.info("<getIdentificacaoMobile()");
		
		return listMensagens;
		
	}
	
	@Override
	public IdentificacaoMobile inserirIdentificacaoMobile( IdentificacaoMobile identificacao ){
		
		logger.info(">inserirIdentificacaoMobile()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " IdentificacaoMobile: " + identificacao );
		
		if ( identificacao == null ) {
			return identificacao;
		}

		/*
		 * Campos obrigatórios no DB
		 * 
		 * */
		if ( StringUtils.isBlank( identificacao.getHashPush() )   ||
			 StringUtils.isBlank( identificacao.getDocumento() )  || 
			 StringUtils.isBlank( identificacao.getNoNome() ) ) {
			return identificacao;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
										.withFunctionName( Constantes.DB.SPI_IDENTIFICACAO_MOBILE )
										.withSchemaName( Constantes.DB.SCHEMA )
										.withReturnValue();
		
		MapSqlParameterSource inputs = new MapSqlParameterSource()
									.addValue("P_HASH_PUSH", identificacao.getHashPush() )
									.addValue("P_DOCUMENTO", identificacao.getDocumento() )
									.addValue("P_NO_NOME",   identificacao.getNoNome() )
									.addValue("P_EM_EMAIL",  identificacao.getEmEmail() );

		logger.info("realizando a consulta: " + DbUtil.logCallFuncion( jdbcCall, inputs) );

		Map<String, Object > cursor = jdbcCall.execute( inputs );

		String id = ( String ) cursor.get( Constantes.DB.RETURN_ALIAS );
		
		identificacao.setIdIdentificacaoMobile( id );

		logger.info("<inserirIdentificacaoMobile()");
		
		return identificacao;
	}
	
	@Override
	public Long atualizarIdentificacaoMobile( IdentificacaoMobile identificacao ){
		
		logger.info(">atualizarIdentificacaoMobile()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " IdentificacaoMobile: " + identificacao );
		
		if ( identificacao == null ) {
			return -1L;
		}

		/*
		 * Campos obrigatórios no DB
		 * 
		 * */
		if ( StringUtils.isBlank( identificacao.getIdIdentificacaoMobile() ) ||
			 StringUtils.isBlank( identificacao.getHashPush() )     		 ||
			 StringUtils.isBlank( identificacao.getDocumento() )       		 || 
			 StringUtils.isBlank( identificacao.getNoNome() ) ) {
			return -1L;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
				.withFunctionName( Constantes.DB.SPU_IDENTIFICACAO_MOBILE )
				.withSchemaName( Constantes.DB.SCHEMA )
				.withReturnValue();

		MapSqlParameterSource inputs = new MapSqlParameterSource()
		.addValue("P_ID_MOBILE", identificacao.getIdIdentificacaoMobile() )
		.addValue("P_HASH_PUSH", identificacao.getHashPush() )
		.addValue("P_DOCUMENTO", identificacao.getDocumento() )
		.addValue("P_NO_NOME",   identificacao.getNoNome() )
		.addValue("P_EM_EMAIL",  identificacao.getEmEmail() );

		logger.info("executando a function: " + DbUtil.logCallFuncion( jdbcCall, inputs) );

		Map<String, Object > cursor = jdbcCall.execute( inputs );

		BigDecimal qtdRegsAfetados = ( BigDecimal ) cursor.get( Constantes.DB.RETURN_ALIAS );
		
		logger.info("<atualizarIdentificacaoMobile()");
		
		return qtdRegsAfetados.longValue();
	}
	
}
