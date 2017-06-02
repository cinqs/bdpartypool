package com.worldline.cn.hr.bdpartypool.services;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldline.cn.hr.bdpartypool.BdPartyPoolApplication;
import com.worldline.cn.hr.bdpartypool.dao.Configuration;

@Service
public class Initiator {

	private Logger logger = Logger.getLogger(Initiator.class);
	
	private @Autowired ConfigurationService cs;
	
	@PostConstruct
	public void init() {
		loadRoundInfo();
	}
	
	private void loadRoundInfo() {
		Configuration c = cs.selectByName("round");
		
		if(null == c) {
			c = new Configuration();
			c.setName("round");
			c.setProperty("0");
			cs.insertOne(c);
			BdPartyPoolApplication.setRound(0);
		} else {
			String property = c.getProperty();
			int round = 0;
			try {
				round = Integer.parseInt(property);
			} catch(Exception e) {
				logger.info(e);
			}
			
			BdPartyPoolApplication.setRound(round);
		}
	}
}
