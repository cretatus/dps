package ru.vk.cometa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import groovy.util.Eval;

public class GroovyTests {

	@Test
	public void testGroovy() throws Exception {
		/*
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("groovy");
		Integer sum = (Integer) engine.eval("(1..10).sum()");
		assertEquals(Integer.valueOf(55), sum);
		*/
		Integer r = (Integer)Eval.me("t", 5, "t * 10");
		assertEquals(r, Integer.valueOf(50));
	}

	@Test
	public void testGroovyHack() throws Exception {
		/*
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("groovy");
		Integer sum = (Integer) engine.eval("(1..10).sum()");
		assertEquals(Integer.valueOf(55), sum);
		*/
		Integer r = (Integer)Eval.me("t", 5, "import java.lang.Integer; Integer i; i = t * 10; return i;");
		assertEquals(r, Integer.valueOf(50));
	}

}
