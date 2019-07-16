package ru.vk.cometa.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import ru.vk.cometa.repositories.UserRepository;

@Configuration
@PropertySource("classpath:/co-meta-config.properties")
public class ConfigService {
	@Autowired
	Environment env;
	@Autowired
	UserRepository userRepository;
	
	public String getProperty(String key) {
		return env.getProperty(key);
	}
	
	@Bean
	public FilterRegistrationBean readApplicationFilterRegistration() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new ReadApplicationFilter(userRepository));
	    registration.addUrlPatterns("/read/*");
	    registration.addUrlPatterns("/operation/*");
	    registration.setName("readApplicationFilter");
	    registration.setOrder(1);
	    return registration;
	} 
	@Bean
	public FilterRegistrationBean writeApplicationFilterRegistration() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new WriteApplicationFilter(userRepository));
	    registration.addUrlPatterns("/save/*");
	    registration.addUrlPatterns("/remove/*");
	    registration.setName("writeApplicationFilter");
	    registration.setOrder(2);
	    return registration;
	} 
	@Bean
	public FilterRegistrationBean adminApplicationFilterRegistration() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(new AdminApplicationFilter(userRepository));
	    registration.addUrlPatterns("/admin/*");
	    registration.setName("adminApplicationFilter");
	    registration.setOrder(3);
	    return registration;
	} 
}
