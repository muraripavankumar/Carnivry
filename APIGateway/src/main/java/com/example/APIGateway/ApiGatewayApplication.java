package com.example.APIGateway;

//import com.example.APIGateway.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
		log.info("ApiGatewayApplication is RUNNING!");
	}
//	@Bean
//	public FilterRegistrationBean jwtFilter(){
//		//mention the urls to be intercepted.filtered
//		FilterRegistrationBean frb=new FilterRegistrationBean();
//		frb.setFilter(new JwtFilter());
//		frb.addUrlPatterns("/api/v1/movie","/api/v1/movies","/api/v1/movie/*","/api/v1/movies/*");
//		//frb.addUrlPatterns("/api/v1/*");//all urls will be intercepted
//		return frb;
//	}

}
