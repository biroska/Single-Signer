package br.com.experian.single.signer.rest.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import br.com.experian.commons.environment.configuration.Environment;
import br.com.experian.commons.security.authorization.service.AuthorizationServiceFactory;
import br.com.experian.commons.security.authorization.service.IAuthorizationService;
import br.com.experian.commons.security.cryptography.business.CryptographyFactory;
import br.com.experian.commons.security.cryptography.business.ICryptography;
import br.com.experian.commons.security.cryptography.datatype.Cryptography;
import br.com.experian.commons.security.user.User;
import br.com.experian.commons.web.log.LoggerUtils;
import br.com.experian.srv.usuario.client.facade.ws.AutenticarFacadeWS;
import br.com.experian.srv.usuario.client.model.AutenticaVO;
import br.com.experian.srv.usuario.client.model.ParametrosVO;
import br.com.experian.srv.usuario.core.model.AutenticarResponse;

public class MainframeAuthenticationProvider implements AuthenticationProvider {
	
	private String authServiceUrl;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private RoleMapper roleMapper;
	
	private static Pattern USERNAME_PATTERN = Pattern.compile("[a-z0-9]{7,8}", Pattern.CASE_INSENSITIVE);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		String remoteAddress = null;
		Object details = authentication.getDetails();
		
		LOGGER.debug("Iniciando autenticacao para {}", username);
		
		if(!USERNAME_PATTERN.matcher(username).matches()){
			LOGGER.info("{} nao possui formato de usuario de mainframe", username);
			return null;
		}

		if (details instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails authDetails = (WebAuthenticationDetails) details;
			remoteAddress = authDetails.getRemoteAddress();
		}
			
		try {
			AutenticarFacadeWS ws = new AutenticarFacadeWS();
			AutenticaVO vo = new AutenticaVO();
			vo.setLogon(username);
			vo.setSenha(password);
			vo.setIp(remoteAddress);
			vo.setIdTransacao(LoggerUtils.getMDCRequestId());
			
			ParametrosVO parametros = new ParametrosVO();
			parametros.setEndpoint(authServiceUrl);
			
			AutenticarResponse res = ws.processa(vo, parametros);
			password = res.getUsuario().getToken();
			
			String cicsName = Environment.getInstance().getEnvironmentTO().getCics().getCicsDefaultName();
			ICryptography cryptographer = CryptographyFactory.getInstance().createCryptography(61);

			Cryptography cryptography = new Cryptography();
			cryptography.setCicsName(cicsName);
			cryptography.setLoginName(username);
			cryptography.setPassword(password);

			User user = new User();
			user.setUserSessionLastAccessTime(Calendar.getInstance());
			user.setUserLogin(username);
			user.setUserSessionParam(cryptographer.doCryption(cryptography));
			user.setUserAddress(remoteAddress);
			user.setUserSessionToken(password);

			AuthorizationServiceFactory authorizationServiceFactory = AuthorizationServiceFactory.getInstance();
			IAuthorizationService authorizationService = authorizationServiceFactory.createAuthorizationService();
			authorizationService.retrieveUserProfile(user, roleMapper.getUniqueTransactions());

			Collection<SimpleGrantedAuthority> grantedRoles = new ArrayList<SimpleGrantedAuthority>();
			
			for (Entry<String, List<String>> roleMapping : roleMapper.getRoleMapping().entrySet()) {
				String role = roleMapping.getKey();
				String[] transactions = roleMapping.getValue().toArray(new String[0]);
				
				if(user.getUserProfile().hasAccess(transactions)){
					grantedRoles.add(new SimpleGrantedAuthority(roleMapper.getRolePrefix()+role));
				}
			}

			return new UsernamePasswordAuthenticationToken(user, password, grantedRoles);
		} catch (Exception e) {
			LOGGER.error("Erro ao autenticar usuario", e);
			throw new BadCredentialsException("Invalid access", e); 
		}
		
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public void setAuthServiceUrl(String authServiceUrl) {
		this.authServiceUrl = authServiceUrl;
	}

}
