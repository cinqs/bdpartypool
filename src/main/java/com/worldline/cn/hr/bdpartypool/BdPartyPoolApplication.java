package com.worldline.cn.hr.bdpartypool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BdPartyPoolApplication {
	
	private static int round;

	public static int getRound() {
		return round;
	}

	public static void startRound() {
		BdPartyPoolApplication.round++;
	}

	public static void main(String[] args) {
		SpringApplication.run(BdPartyPoolApplication.class, args);
	}
}
