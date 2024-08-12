package secret_diary.secret_diary_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SecretDiarySpringApplication extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(SecretDiarySpringApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SecretDiarySpringApplication.class, args);
	}

}
