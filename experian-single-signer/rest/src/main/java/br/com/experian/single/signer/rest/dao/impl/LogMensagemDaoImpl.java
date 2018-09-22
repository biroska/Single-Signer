package br.com.experian.single.signer.rest.dao.impl;

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
import br.com.experian.single.signer.core.model.MensagemLog;
import br.com.experian.single.signer.rest.dao.LogMensagemDao;
import br.com.experian.single.signer.rest.util.DbUtil;

@Repository
public class LogMensagemDaoImpl extends JdbcDaoSupport implements LogMensagemDao {
	
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
	public List<MensagemLog> getMensagemLog( MensagemLog msg, String dtInicio, String dtFinal ){
		
		logger.info(">getMensagemLog()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " MensagemLog: " + msg + 
					 Constantes.Formatting.TAB_CONFIG_4 + " data inicio: " + dtInicio +
				     Constantes.Formatting.TAB_CONFIG_4 + " data final: " + dtFinal );
		
		if ( msg == null ) {
			return null;
		}
		
		if ( StringUtils.isBlank( msg.getIdMensagem() ) &&
			 StringUtils.isBlank( msg.getCliente() )    &&
			 StringUtils.isBlank( msg.getFuncao() )     &&
			 StringUtils.isBlank( dtInicio )            &&
			 StringUtils.isBlank( dtFinal )             &&
			 StringUtils.isBlank( msg.getTipo() ) ) {
			return null;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
									.withFunctionName( Constantes.DB.SPC_LOG_MENSAGEM )
									.withSchemaName( Constantes.DB.SCHEMA )
									.returningResultSet("REF CURSOR",
					                                    BeanPropertyRowMapper.newInstance( MensagemLog.class ) );
		
		MapSqlParameterSource inputs = new MapSqlParameterSource()
			.addValue("P_IDENTIFICADOR", NumberUtils.isDigits( msg.getIdMensagem() ) ? Long.valueOf( msg.getIdMensagem() ) : null )
			.addValue("P_TIPO", msg.getTipo() )
			.addValue("P_FUNCAO", msg.getFuncao() )
			.addValue("P_CLIENTE", msg.getCliente() )
			.addValue("P_DT_MSG_INI", dtInicio )
			.addValue("P_DT_MSG_FINAL", dtFinal );
		
		logger.info("realizando a consulta: " + DbUtil.logCallFuncion( jdbcCall, inputs) );
		
		Map<String, Object > cursor = jdbcCall.execute( inputs );
		
		List<MensagemLog> listMensagens = ( List<MensagemLog> ) cursor.get("REF CURSOR");
		
		logger.info("Quantidade de registros retornados: " + listMensagens.size() );
		
		logger.info("<getMensagemLog()");
		
		return listMensagens;
	}
	
	@Override
	public MensagemLog inserirMensagemLog( MensagemLog msg ){
		
		logger.info(">inserirMensagemLog()");
		
		logger.info( Constantes.Formatting.TAB_CONFIG_3 + " Parametros: " + 
				     Constantes.Formatting.TAB_CONFIG_4 + " MensagemLog: " + msg );
		
		if ( msg == null ) {
			return null;
		}
		
		if ( StringUtils.isBlank( msg.getCliente() )    ||
			 StringUtils.isBlank( msg.getFuncao() )     ||
			 StringUtils.isBlank( msg.getTipo() )       || 
			 StringUtils.isBlank( msg.getMensagem() ) ) {
			return msg;
		}
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
				.withFunctionName( Constantes.DB.SPI_LOG_MENSAGEM )
				.withSchemaName( Constantes.DB.SCHEMA )
				.withReturnValue();

		MapSqlParameterSource inputs = new MapSqlParameterSource()
		.addValue("P_TIPO", msg.getTipo() )
		.addValue("P_FUNCAO", msg.getFuncao() )
		.addValue("P_CLIENTE", msg.getCliente() )
		.addValue("P_MENSAGEM", msg.getMensagem() );

		logger.info("realizando a consulta: " + DbUtil.logCallFuncion( jdbcCall, inputs) );

		Map<String, Object > cursor = jdbcCall.execute( inputs );

		String idMensagem = ( String ) cursor.get( Constantes.DB.RETURN_ALIAS );
		
		msg.setIdMensagem( idMensagem );

		logger.info("<inserirMensagemLog()");
		
		return msg;
	}
}
