package br.com.experian.single.signer.core.model;

import org.springframework.stereotype.Component;

@Component
public class IdentificacaoMobile {
	
	private String idIdentificacaoMobile;
	private String hashPush;
	private String documento;
	private String noNome;
	private String emEmail;
	
	public String getIdIdentificacaoMobile() {
		return idIdentificacaoMobile;
	}
	public void setIdIdentificacaoMobile(String idIdentificacaoMobile) {
		this.idIdentificacaoMobile = idIdentificacaoMobile;
	}
	public String getHashPush() {
		return hashPush;
	}
	public void setHashPush(String hashPush) {
		this.hashPush = hashPush;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNoNome() {
		return noNome;
	}
	public void setNoNome(String noNome) {
		this.noNome = noNome;
	}
	public String getEmEmail() {
		return emEmail;
	}
	public void setEmEmail(String emEmail) {
		this.emEmail = emEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idIdentificacaoMobile == null) ? 0 : idIdentificacaoMobile.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentificacaoMobile other = (IdentificacaoMobile) obj;
		if (idIdentificacaoMobile == null) {
			if (other.idIdentificacaoMobile != null)
				return false;
		} else if (!idIdentificacaoMobile.equals(other.idIdentificacaoMobile))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "IdentificacaoMobile [idIdentificacaoMobile=" + idIdentificacaoMobile + ", hashPush=" + hashPush
				+ ", documento=" + documento + ", noNome=" + noNome + ", emEmail=" + emEmail + "]";
	}

}