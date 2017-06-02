package com.worldline.cn.hr.bdpartypool;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BdPartyPoolApplication {
	
	private static int round;
	private static AtomicBoolean isClosed = new AtomicBoolean(true);

	public static int getRound() {
		return round;
	}

	public static void startRound() {
		isClosed.compareAndSet(true, false);
		BdPartyPoolApplication.round++;
	}
	
	public static void closeRound() {
		isClosed.compareAndSet(false, true);
	}
	
	public static boolean voteRunning() {
		return !isClosed.get();
	}

	public static void main(String[] args) {
		SpringApplication.run(BdPartyPoolApplication.class, args);
	}
}
