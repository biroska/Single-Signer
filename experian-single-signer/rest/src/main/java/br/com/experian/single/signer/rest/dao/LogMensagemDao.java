package br.com.experian.single.signer.rest.dao;

import java.util.List;

import br.com.experian.single.signer.core.model.MensagemLog;

public interface LogMensagemDao {

	public List<MensagemLog> getMensagemLog(MensagemLog msg, String dtInicio, String dtFinal);

	public MensagemLog inserirMensagemLog(MensagemLog msg);

}
