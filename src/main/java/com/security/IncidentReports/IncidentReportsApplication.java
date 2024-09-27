package com.security.IncidentReports;

import com.security.IncidentReports.config.RequestLoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IncidentReportsApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncidentReportsApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<RequestLoggingFilter> loggingFilter(){
		FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new RequestLoggingFilter());
		registrationBean.addUrlPatterns("/api/*"); // Adjust as necessary
		return registrationBean;
	}
}
