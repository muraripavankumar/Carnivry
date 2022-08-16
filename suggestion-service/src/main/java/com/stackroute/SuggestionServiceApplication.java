package com.stackroute;

//import com.stackroute.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class SuggestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuggestionServiceApplication.class, args);
		log.info("SuggestionService Application RUNNING!");
	}
//	@Bean
//	public FilterRegistrationBean jwtFilter(){
//		//mention the urls to be intercepted.filtered
//		FilterRegistrationBean frb=new FilterRegistrationBean();
//		frb.setFilter(new JwtFilter());
//		frb.addUrlPatterns("/api/*");//list of urls that are to be intercepted
//		return frb;
//	}

}
