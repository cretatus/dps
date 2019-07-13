package ru.vk.cometa;

import static org.junit.Assert.*;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTests {

	@Test
	public void test() throws Exception{
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		Map<String, Object> root = new HashMap<>();
		root.put("tableName", "Freemarker");
		cfg.setDirectoryForTemplateLoading(new File("C:/Projects/workspace/co-meta/src/main/resources/"));
		Template temp = cfg.getTemplate("test.ftl");
		// Writer out = new OutputStreamWriter(System.out);
		StringWriter out = new StringWriter();
		temp.process(root, out);
		assertEquals(out.toString(), "select * from Freemarker!");
	}

	@Test
	public void testFromString() throws Exception{
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		String firstTemplate = "firstTemplate";
		stringLoader.putTemplate(firstTemplate, "select * from ${tableName}!");
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setTemplateLoader(stringLoader);
		
		Map<String, Object> root = new HashMap<>();
		root.put("tableName", "Freemarker");
		Template temp = cfg.getTemplate(firstTemplate);

		StringWriter out = new StringWriter();
		temp.process(root, out);
		assertEquals(out.toString(), "select * from Freemarker!");
	}

}
