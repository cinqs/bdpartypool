package com.worldline.cn.hr.bdpartypool.restController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldline.cn.hr.bdpartypool.BdPartyPoolApplication;

@RestController
@RequestMapping(value="/admin/")
public class AdminController {

	@RequestMapping("start")
	public boolean startRound() {
		BdPartyPoolApplication.startRound();
		return true;
	}
}
