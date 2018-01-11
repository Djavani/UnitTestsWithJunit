package br.com.djavani.asserts;
import org.junit.Assert;
import org.junit.Test;

import br.com.djavani.entidades.Usuario;

public class AssertTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		// atentar para as casas decimais
		Assert.assertEquals("Erro de comparacao", 1, 1);
		Assert.assertEquals(0.51234, 0.512, 0.001); 
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());	
	}
	
	@Test
	public void testeStrings() {
		Assert.assertEquals("bola", "bola");
		Assert.assertNotEquals("bola", "casa");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
	}
	
	@Test
	public void testeObjeto() {
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = u2;
		Usuario u4 = null;
		
		Assert.assertEquals(u1, u2);
		
		Assert.assertSame(u2, u3);
		Assert.assertNotSame(u1, u2);
		
		Assert.assertNull(u4);
		Assert.assertNotNull(u2);
	}
}
