package com.worldline.cn.hr.bdpartypool.httpFilter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.worldline.cn.hr.bdpartypool.auth.IPChecker;
import com.worldline.cn.hr.bdpartypool.utils.IPUtils;

@WebFilter("/*")
public class IPFilter implements Filter {
	private Logger logger = Logger.getLogger(IPFilter.class);
	
	private @Autowired IPChecker ipChecker;

	@Override
	public void destroy() {
		logger.info("Function filter is being destroyed");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String ip = IPUtils.getIPAddr(req);
		boolean result = ipChecker.checkIP(ip);
		
		logger.info("Got request from: " + ip);
		logger.info("match result: " + result);
		
		if(!result) {
			PrintWriter pw = res.getWriter();
			pw.write("you are not authorized to vote!");
			pw.flush();
			pw.close();
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("Function filter is being initialized");
	}
		
}
