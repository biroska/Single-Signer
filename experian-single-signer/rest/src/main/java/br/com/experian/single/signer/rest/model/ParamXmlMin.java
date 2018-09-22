package br.com.experian.single.signer.rest.model;

import org.springframework.stereotype.Component;

import com.google.common.primitives.Bytes;

@Component
public class ParamXmlMin {
	
	public ParamXmlMin() {}
	
	private String vlParam;
	
	private String nmParam;
	
	private String nmContexto;
	
	private String idParam;
	
	private Bytes blParam;

	public String getVlParam() {
		return vlParam;
	}

	public void setVlParam(String vlParam) {
		this.vlParam = vlParam;
	}

	public String getNmParam() {
		return nmParam;
	}

	public void setNmParam(String nmParam) {
		this.nmParam = nmParam;
	}

	public String getNmContexto() {
		return nmContexto;
	}

	public void setNmContexto(String nmContexto) {
		this.nmContexto = nmContexto;
	}

	public String getIdParam() {
		return idParam;
	}

	public void setIdParam(String idParam) {
		this.idParam = idParam;
	}

	public Bytes getBlParam() {
		return blParam;
	}

	public void setBlParam(Bytes blParam) {
		this.blParam = blParam;
	}

	@Override
	public String toString() {
		return "ParamXmlMin [vlParam=" + vlParam + ", nmParam=" + nmParam + ", nmContexto=" + nmContexto + ", idParam="
				+ idParam + ", blParam=" + blParam + "]";
	}
	
}
