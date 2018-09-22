package br.com.experian.single.signer.core.model;

import org.springframework.stereotype.Component;

@Component
public class MensagemLog {
	
	private String idMensagem; 
	private String dtMensagem; 
	private String tipo;
	private String funcao;   
	private String cliente;   
	private String mensagem;
	public String getIdMensagem() {
		return idMensagem;
	}
	public void setIdMensagem(String idMensagem) {
		this.idMensagem = idMensagem;
	}
	public String getDtMensagem() {
		return dtMensagem;
	}
	public void setDtMensagem(String dtMensagem) {
		this.dtMensagem = dtMensagem;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFuncao() {
		return funcao;
	}
	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMensagem == null) ? 0 : idMensagem.hashCode());
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
		MensagemLog other = (MensagemLog) obj;
		if (idMensagem == null) {
			if (other.idMensagem != null)
				return false;
		} else if (!idMensagem.equals(other.idMensagem))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "MensagemLog [idMensagem=" + idMensagem + ", dtMensagem=" + dtMensagem + ", tipo=" + tipo + ", funcao="
				+ funcao + ", cliente=" + cliente + ", mensagem=" + mensagem + "]";
	}

}