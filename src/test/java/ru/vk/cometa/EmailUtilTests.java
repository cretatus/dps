package ru.vk.cometa;

import org.junit.Test;

import ru.vk.cometa.service.EmailUtil;

public class EmailUtilTests {
	@Test
	public void test() {
		EmailUtil emailUtil = new EmailUtil();
		emailUtil.send("test", "test text", "vasiliy.kuzmin@gmail.com");
	}

}
