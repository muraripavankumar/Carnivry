package com.stackroute;

import com.stackroute.config.TwilioConfig;
import com.twilio.Twilio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;

@SpringBootApplication
//@EnableEurekaClient
@Slf4j
public class RegistrationService {

//    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    public RegistrationService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }


    @PostConstruct
    public void initTwilio(){
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
    }


    public static void main(String[] args) {

        SpringApplication.run(RegistrationService.class,args);
        log.info("REGISTRATION SERVICE RUNNING!");

    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(){
//        final CorsConfiguration config= new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:4200");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }
}
