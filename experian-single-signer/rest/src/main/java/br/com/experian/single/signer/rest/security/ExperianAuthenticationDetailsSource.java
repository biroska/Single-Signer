package br.com.experian.single.signer.rest.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Recupera o endereço IP do header X-Forwarded-For
 * 
 * @author Daniel Miyamoto
 *
 */
public class ExperianAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
	
	private static String X_FORWARDED_FOR = "X-Forwarded-For";
	private static Pattern IP_PATTERN = Pattern.compile("\\b(\\d+\\.\\d+\\.\\d+\\.\\d+)\\b");

	@Override
	public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
	
		final String requestIp = getRequesterIp(request);
		
		return (new WebAuthenticationDetails(request){
			private static final long serialVersionUID = 1L;

			@Override
			public String getRemoteAddress() {
				return requestIp;
			}
		});
	}
	
	private String getRequesterIp(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		
		String forwardedForIp = getRequesterIpFromHeader(request.getHeader(X_FORWARDED_FOR));
		
		if(StringUtils.isNotBlank(forwardedForIp)){
			ip = forwardedForIp;
		}
		return ip;
	}

	private String getRequesterIpFromHeader(String forwardedFor){
		if(StringUtils.isNotBlank(forwardedFor)){
			Matcher ipMatcher = IP_PATTERN.matcher(forwardedFor);
			
			if(ipMatcher.find()){
				return ipMatcher.group();
			}
		}
		
		return StringUtils.EMPTY;
	}

}
