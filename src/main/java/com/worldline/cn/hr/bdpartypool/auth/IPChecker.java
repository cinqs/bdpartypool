package com.worldline.cn.hr.bdpartypool.auth;

import org.apache.log4j.Logger;

public class IPChecker {
	private  Logger logger = Logger.getLogger(IPChecker.class);

	private String ipBind;
	
	public IPChecker(String ipBind) {
		this.ipBind = ipBind;
	}
	
	public boolean checkIP(String ip) {
		logger.debug("going to check ip matching with: " + ipBind + " vs " + ip);
		return ip.startsWith(ipBind);
	}
}
