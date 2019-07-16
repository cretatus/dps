package ru.vk.cometa;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class WebClientApplication {

	public static void main(String[] args) {
		System.out.println(new File(".").getAbsolutePath());
		SpringApplication.run(WebClientApplication.class, args);
	}
}
