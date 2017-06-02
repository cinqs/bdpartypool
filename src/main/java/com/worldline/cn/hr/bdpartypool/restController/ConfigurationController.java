package com.worldline.cn.hr.bdpartypool.restController;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.worldline.cn.hr.bdpartypool.dao.Configuration;
import com.worldline.cn.hr.bdpartypool.services.ConfigurationService;

@RestController
@RequestMapping(value="/api/configuration/")
public class ConfigurationController {

	private Logger logger = Logger.getLogger(ConfigurationController.class);
	
	private @Autowired ConfigurationService cs;
	
	@RequestMapping(value="round", method=RequestMethod.GET)
	public Map<String, String> getRound() {
		Map<String, String> map = new HashMap<>();
		
		Configuration c = cs.selectByName("round");
		
		if(null == c) {
			map.put("value", "unknown");
		} else {
			map.put("value", c.getProperty());
		}
		
		return map;
	}
}
