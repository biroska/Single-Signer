package br.com.experian.single.signer.core.model;

import org.springframework.stereotype.Component;

@Component
public class AssinaturaMobile {
	
	private String idRequisicaoAssinaturaMob;
	private String idIdentificacaoMobile;
	private String hashRecebido;
	private String dtSolicitacao;
	private String hashAssinado;
	private String dtAssinatura;

	public String getIdRequisicaoAssinaturaMob() {
		return idRequisicaoAssinaturaMob;
	}
	public void setIdRequisicaoAssinaturaMob(String idRequisicaoAssinaturaMob) {
		this.idRequisicaoAssinaturaMob = idRequisicaoAssinaturaMob;
	}
	public String getIdIdentificacaoMobile() {
		return idIdentificacaoMobile;
	}
	public void setIdIdentificacaoMobile(String idIdentificacaoMobile) {
		this.idIdentificacaoMobile = idIdentificacaoMobile;
	}
	public String getHashRecebido() {
		return hashRecebido;
	}
	public void setHashRecebido(String hashRecebido) {
		this.hashRecebido = hashRecebido;
	}
	public String getDtSolicitacao() {
		return dtSolicitacao;
	}
	public void setDtSolicitacao(String dtSolicitacao) {
		this.dtSolicitacao = dtSolicitacao;
	}
	public String getHashAssinado() {
		return hashAssinado;
	}
	public void setHashAssinado(String hashAssinado) {
		this.hashAssinado = hashAssinado;
	}
	public String getDtAssinatura() {
		return dtAssinatura;
	}
	public void setDtAssinatura(String dtAssinatura) {
		this.dtAssinatura = dtAssinatura;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idRequisicaoAssinaturaMob == null) ? 0 : idRequisicaoAssinaturaMob.hashCode());
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
		AssinaturaMobile other = (AssinaturaMobile) obj;
		if (idRequisicaoAssinaturaMob == null) {
			if (other.idRequisicaoAssinaturaMob != null)
				return false;
		} else if (!idRequisicaoAssinaturaMob.equals(other.idRequisicaoAssinaturaMob))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AssinaturaMobile [idRequisicaoAssinaturaMob=" + idRequisicaoAssinaturaMob + ", idIdentificacaoMobile="
				+ idIdentificacaoMobile + ", hashRecebido=" + hashRecebido + ", dtSolicitacao=" + dtSolicitacao
				+ ", hashAssinado=" + hashAssinado + ", dtAssinatura=" + dtAssinatura + "]";
	}

}
