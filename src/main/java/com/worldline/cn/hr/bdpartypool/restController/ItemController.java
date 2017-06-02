package com.worldline.cn.hr.bdpartypool.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldline.cn.hr.bdpartypool.dao.Item;
import com.worldline.cn.hr.bdpartypool.services.ItemService;
import com.worldline.cn.hr.bdpartypool.utils.IPUtils;

@RequestMapping(value="/api/item/")
@RestController
public class ItemController {
	private Logger logger = Logger.getLogger(ItemController.class);
	private @Autowired ItemService is;

	@RequestMapping(value="list", method=RequestMethod.GET)
	public List<Map<String, Object>> list() {
		
		return is.listItem();
	}
	
	@RequestMapping(value="/propose", method=RequestMethod.GET)
	public Map<String, String> propose(HttpServletRequest req,
			@RequestParam(value="name") String name,
			HttpServletResponse res) {
		Map<String, String> map = new HashMap<>();
		String ip = IPUtils.getIPAddr(req);
		logger.debug("received proposal from " + ip + " for " + name);
		
		if(null != is.selectByName(name)) {
			map.put("status", "false");
			map.put("msg", "this item is already registered, and can be voted...");
			return map;
		} else {
			
			Item item = new Item();
			item.setName(name);
			is.insertOne(item);
			
			map.put("status", "true");
			map.put("msg", "item been registered, thank you for your proposal");
			return map;
		}
	}
}
