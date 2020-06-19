package net.mjr.commandservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import net.mjr.commandservice.config.CommandsConfig;

@SpringBootApplication
@EnableConfigurationProperties(CommandsConfig.class)
public class CommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandServiceApplication.class, args);
	}

}
