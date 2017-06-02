package com.worldline.cn.hr.bdpartypool.httpFilter;

import java.io.IOException; 

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

import com.worldline.cn.hr.bdpartypool.BdPartyPoolApplication;

@WebFilter(value="/api/*")
public class VoterFilter implements Filter{
	
	private Logger logger = Logger.getLogger(VoterFilter.class);
	
	
	@Override
	public void destroy() {
		logger.info("destroying Voter Filter");
	}

	@Override
	public void doFilter(ServletRequest request, 
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		int round = BdPartyPoolApplication.getRound();
		
		if(round == 0 || !BdPartyPoolApplication.voteRunning()) {
			res.setStatus(503);
			res.getWriter().write("vote pool is closed..."
					+ "wait patiently...");
		} else {
			chain.doFilter(req, res);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("initializing Voter filter...");
	}

}
