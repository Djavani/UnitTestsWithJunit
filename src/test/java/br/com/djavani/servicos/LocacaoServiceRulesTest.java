package br.com.djavani.servicos;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.djavani.builders.FilmeBuilder;
import br.com.djavani.builders.UsuarioBuilder;
import br.com.djavani.dao.LocacaoDAO;
import br.com.djavani.entidades.Filme;
import br.com.djavani.entidades.Locacao;
import br.com.djavani.entidades.Usuario;
import br.com.djavani.runners.ParallelRunner;
import br.com.djavani.utils.DataUtils;

@RunWith(ParallelRunner.class)
public class LocacaoServiceRulesTest {

	@InjectMocks
	@Spy
	private LocacaoService service;

	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAO dao;
	@Mock
	private EmailService email;

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	// teste normal, ao ocorrer exceção o teste é parado e não prossegue para o
	// proximo teste
	@Test
	public void deveAlugarFilmeOld() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());

		Mockito.doReturn(DataUtils.obterData(12, 1, 2018)).when(service).obterData();

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(12, 1, 2018)),CoreMatchers.is(true));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(13, 1, 2018)),CoreMatchers.is(true)); 
	}
	

	// TESTES COM RULES
	@Test
	public void deveAlugarFilme() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());

		Mockito.doReturn(DataUtils.obterData(12, 1, 2018)).when(service).obterData();

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
		errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(12, 1, 2018)),CoreMatchers.is(true));
		errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(13, 1, 2018)),CoreMatchers.is(true));
	}

}
