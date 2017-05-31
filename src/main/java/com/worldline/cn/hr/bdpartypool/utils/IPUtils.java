package com.worldline.cn.hr.bdpartypool.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class IPUtils {
	
	private static Logger logger = Logger.getLogger(IPUtils.class);

	private IPUtils() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static String getIPAddr(HttpServletRequest request) {     
		String ip = request.getHeader("x-forwarded-for");
		logger.debug("1: " + ip);
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip))    {     
			ip = request.getHeader("Proxy-Client-IP");  
			logger.debug("2: " + ip);
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)   || "null".equalsIgnoreCase(ip)) {    
			ip = request.getHeader("WL-Proxy-Client-IP");  
			logger.debug("3: " + ip);
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)    || "null".equalsIgnoreCase(ip)) {  
			ip = request.getRemoteAddr();
			if("0:0:0:0:0:0:0:1".equals(ip))
				ip = "127.0.0.1";
			logger.debug("4: " + ip);
		}  
		return ip;
	} 
}
