package br.com.experian.single.signer.rest.dao;

import java.util.List;

import br.com.experian.single.signer.core.model.AssinaturaMobile;

public interface AssinaturaMobileDao {

	public List<AssinaturaMobile> getAssinaturaMobile(AssinaturaMobile idMobile);

	public AssinaturaMobile inserirIdentificacaoMobile(AssinaturaMobile assMobile);

	public Long atualizarAssinaturaMobile(AssinaturaMobile assMobile);

}
