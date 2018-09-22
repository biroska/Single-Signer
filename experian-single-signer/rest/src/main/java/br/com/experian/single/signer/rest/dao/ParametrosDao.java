package br.com.experian.single.signer.rest.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import br.com.experian.single.signer.core.constants.Constantes;
import br.com.experian.single.signer.rest.model.ParamXmlMin;

@Repository
public class ParametrosDao extends JdbcDaoSupport {

	/*Com JDBC DAO Support o datasource fica encapsulado no metodo getJdbcTemplate() */
	
	@Autowired
	@Qualifier("dbDataSource")
	private DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getParameterResultSet(String context, String param ){
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
									.withFunctionName( Constantes.DB.SPC_PARAM_XML_MIN )
									.withSchemaName( Constantes.DB.SCHEMA );
		
		SqlParameterSource inputs = new MapSqlParameterSource()
				.addValue("p_nm_contexto", context )
				.addValue("p_nm_param", param );
		
		List< LinkedCaseInsensitiveMap > funcReturn = jdbcCall.executeFunction( List.class, inputs);

		System.out.println("ParametrosDao.getParameter(): " + funcReturn );
		
		for (LinkedCaseInsensitiveMap f : funcReturn) {
			System.out.println( "retornos: " + f );
			
			System.out.println( "VL_PARAM: " + f.get( "VL_PARAM" ) );
			System.out.println( "NM_PARAM: " + f.get( "NM_PARAM" ) );
			System.out.println( "NM_CONTEXTO: " + f.get( "NM_CONTEXTO" ) );
			System.out.println( "ID_PARAM: " + f.get( "ID_PARAM" ) );
			System.out.println( "BL_PARAM: " + f.get( "BL_PARAM" ) );
		}
		
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public ParamXmlMin getParameter(String context, String param ){
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall( getJdbcTemplate() )
									.withFunctionName( Constantes.DB.SPC_PARAM_XML_MIN )
									.withSchemaName( Constantes.DB.SCHEMA )
									.returningResultSet("REF CURSOR",
					                           BeanPropertyRowMapper.newInstance( ParamXmlMin.class ));
		
		SqlParameterSource inputs = new MapSqlParameterSource()
				.addValue("P_NM_CONTEXTO", context )
				.addValue("P_NM_PARAM", param );
		
		Map<String, Object> cursor = jdbcCall.execute( inputs );
		
		List<ParamXmlMin> paramXml = ( List<ParamXmlMin> ) cursor.get("REF CURSOR");
		
		System.out.println( "paramXml: " + paramXml );
		
		return paramXml.get( 0 );
	}
	
}
