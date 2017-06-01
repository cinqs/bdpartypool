package com.worldline.cn.hr.bdpartypool;

import org.apache.log4j.Logger;
import org.junit.Test;

public class MainTest {
	private Logger logger = Logger.getLogger(MainTest.class);

	@Test
	public void test() {
		
		double init = 2.5d;
		
		int year = 8;
		
		double amount = init;
		
		double current = init;
		
		for(int i = 2; i <= year; i++) {
			current = current * (1 + 0.1);
			amount = amount + current;
		}
		
		logger.info(amount);
	}
}
