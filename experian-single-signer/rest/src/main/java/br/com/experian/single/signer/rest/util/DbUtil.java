package br.com.experian.single.signer.rest.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import br.com.experian.single.signer.core.constants.Constantes;

public class DbUtil {
	
	public static String logCallFuncion( SimpleJdbcCall jdbcCall, MapSqlParameterSource sqlParam ) {
		
		if ( jdbcCall == null ) {
			return "";
		}
		
		StringBuffer buffer = new StringBuffer( Constantes.Formatting.TAB_CONFIG_4 );
		
		buffer.append( "begin ? := " );
		buffer.append( jdbcCall.getSchemaName() );
		buffer.append( "." );
		buffer.append( jdbcCall.getProcedureName() );
		
		if ( sqlParam != null ) {
			
			Map<String, Object> sqlParamMap = sqlParam.getValues();
			
			Collection<Object> values = sqlParamMap.values();
			List<Object> listValues = new ArrayList<Object>( 0 );
			
			if ( values != null && !values.isEmpty() ) {
				
				listValues.addAll( values );
				
				buffer.append( "(" );
			}
			
			for (int i = 0; i < listValues.size(); i++) {
				
				if ( i > 0 ) {
					buffer.append(',');
				}
				buffer.append( "\'" );
				
				
				Object aux = listValues.get(i);
                String parametro = aux == null ? null : aux.toString();
				
				buffer.append( parametro );
				
				 buffer.append('\'');
			}
			
			buffer.append(')');
			
			buffer.append("; end;");
		}
		
		return buffer.toString();
	}
}
