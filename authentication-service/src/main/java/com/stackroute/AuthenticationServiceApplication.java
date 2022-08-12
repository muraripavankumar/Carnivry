package com.stackroute;

import com.stackroute.filter.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableEurekaClient
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}


//	@Bean
//	public FilterRegistrationBean jwtFilter() {
//		// which urls to be intercepted / filtered		//
//		FilterRegistrationBean frb = new FilterRegistrationBean();
//		frb.setFilter(new JwtFilter());
//		frb.addUrlPatterns("/api/v1/forgotPassword");
//		return frb;
//	}
}
