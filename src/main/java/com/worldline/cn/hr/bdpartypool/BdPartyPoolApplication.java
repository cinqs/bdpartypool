package com.worldline.cn.hr.bdpartypool;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BdPartyPoolApplication {
	
	private static int round;
	private static AtomicBoolean isRunning = new AtomicBoolean(true);

	public static int getRound() {
		return round;
	}
	
	public static void setRound(int round) {
		BdPartyPoolApplication.round = round;
	}

	public static void startRound() {
		isRunning.compareAndSet(false, true);
	}
	
	public static void closeRound() {
		isRunning.compareAndSet(true, false);
	}
	
	public static boolean voteRunning() {
		return isRunning.get();
	}

	public static void main(String[] args) {
		SpringApplication.run(BdPartyPoolApplication.class, args);
	}
}
