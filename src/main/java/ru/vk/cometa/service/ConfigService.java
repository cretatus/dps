package ru.vk.cometa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/co-meta-config.properties")
public class ConfigService {
	@Autowired
	Environment env;
	
	public String getProperty(String key) {
		return env.getProperty(key);
	}
}
