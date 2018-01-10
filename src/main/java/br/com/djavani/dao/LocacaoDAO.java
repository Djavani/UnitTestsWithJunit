package br.com.djavani.dao;

import java.util.List;

import br.com.djavani.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
