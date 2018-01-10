package br.com.djavani.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.com.djavani.builders.FilmeBuilder;
import br.com.djavani.builders.UsuarioBuilder;
import br.com.djavani.dao.LocacaoDAO;
import br.com.djavani.entidades.Filme;
import br.com.djavani.entidades.Locacao;
import br.com.djavani.entidades.Usuario;
import br.com.djavani.matchers.MatchersProprios;
import br.com.djavani.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LocacaoService.class })
public class LocacaoServiceTest_PowerMock {

	@InjectMocks
	private LocacaoService service;

	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDAO dao;
	@Mock
	private EmailService email;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = PowerMockito.spy(service);
		System.out.println("Iniciando 4...");
		CalculadoraTest.ordem.append(4);
	}

	@After
	public void tearDown() {
		System.out.println("finalizando 4...");
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println(CalculadoraTest.ordem.toString());
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(27, 1, 2018));

		// acao
		Locacao retorno = service.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}

	@Test
	public void deveAlugarFilme_SemCalcularValor() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes);

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertThat(locacao.getValor(), is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
	}

	@Test
	public void deveCalcularValorLocacao() throws Exception {
		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		// acao
		Double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);

		// verificacao
		Assert.assertThat(valor, is(4.0));
	}
}
