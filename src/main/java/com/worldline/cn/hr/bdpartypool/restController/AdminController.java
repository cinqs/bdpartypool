package com.worldline.cn.hr.bdpartypool.restController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldline.cn.hr.bdpartypool.BdPartyPoolApplication;
import com.worldline.cn.hr.bdpartypool.dao.Configuration;
import com.worldline.cn.hr.bdpartypool.services.ConfigurationService;

@RestController
@RequestMapping(value="/admin/")
public class AdminController {
	private Logger logger = Logger.getLogger(AdminController.class);
	
	private @Autowired ConfigurationService cs;

	@RequestMapping("vote/start")
	public boolean startRound() {
		BdPartyPoolApplication.startRound();
		
		int round1 = BdPartyPoolApplication.getRound();
		
		Configuration c = cs.selectByName("round");
		int round2 = Integer.parseInt(c.getProperty());
		
		if(round1 != round2) {
			round1 = Math.max(round1, round2);
		}
		
		round1++;
		
		BdPartyPoolApplication.setRound(round1);
		c = new Configuration();
		c.setName("round");
		c.setProperty(round1 + "");
		cs.updateByName(c);
		
		logger.info("someone opened a new round of voting...");
		return true;
	}
	
	@RequestMapping("vote/stop")
	public boolean stopRound() {
		BdPartyPoolApplication.closeRound();
		logger.info("someone closed this round of voting...");
		return true;
	}
}
