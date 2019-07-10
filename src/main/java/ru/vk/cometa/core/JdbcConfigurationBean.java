package ru.vk.cometa.core;

import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ru.vk.cometa.service.ConfigService;

@Configuration
@EnableAsync
public class JdbcConfigurationBean extends WebMvcConfigurerAdapter {
	@Autowired
	ConfigService config;

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
    	Properties properties = new Properties();
    	properties.setProperty("useUnicode", "true");
    	properties.setProperty("characterEncoding", "utf8");
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl(config.getProperty("db.url"));
        driverManagerDataSource.setUsername(config.getProperty("db.login"));
        driverManagerDataSource.setPassword(config.getProperty("db.password"));

        driverManagerDataSource.setConnectionProperties(properties);
        return driverManagerDataSource;
    }
    
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }
    

}