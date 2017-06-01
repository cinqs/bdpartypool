package com.worldline.cn.hr.bdpartypool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.worldline.cn.hr.bdpartypool.auth.IPChecker;

@Configuration
public class Config {
	
	private @Value("${IP.bind}") String IPPrefix;

	@Bean
	public IPChecker ipChecker() {
		return new IPChecker(IPPrefix);
	}
}
