package com.worldline.cn.hr.bdpartypool.services;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

	private Logger logger = Logger.getLogger(RecordService.class);
	private @Autowired SqlSessionFactory ssf;
	
}
