package dev.alexprezioso.gpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class GptApplication extends SpringBootServletInitializer { // AWS

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GptApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GptApplication.class, args);
	}

}
