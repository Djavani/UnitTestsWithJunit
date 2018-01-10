package br.com.djavani.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


import br.com.djavani.servicos.CalculoValorLocacaoTest;
import br.com.djavani.servicos.LocacaoServiceTest;

@RunWith(Suite.class)
@SuiteClasses({	
//	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})

public class SuiteExecucao {
	
}
