package com.worldline.cn.hr.bdpartypool.restController;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldline.cn.hr.bdpartypool.BdPartyPoolApplication;

@RestController
@RequestMapping(value="/admin/")
public class AdminController {
	private Logger logger = Logger.getLogger(AdminController.class);

	@RequestMapping("vote/start")
	public boolean startRound() {
		BdPartyPoolApplication.startRound();
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
