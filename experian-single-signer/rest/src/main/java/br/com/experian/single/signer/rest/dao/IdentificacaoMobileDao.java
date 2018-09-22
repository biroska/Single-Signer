package br.com.experian.single.signer.rest.dao;

import java.util.List;

import br.com.experian.single.signer.core.model.IdentificacaoMobile;

public interface IdentificacaoMobileDao {

	public Long atualizarIdentificacaoMobile(IdentificacaoMobile identificacao);

	public IdentificacaoMobile inserirIdentificacaoMobile(IdentificacaoMobile identificacao);

	public List<IdentificacaoMobile> getIdentificacaoMobile(IdentificacaoMobile idMobile);

}
