package com.worldline.cn.hr.bdpartypool.services;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldline.cn.hr.bdpartypool.dao.Configuration;
import com.worldline.cn.hr.bdpartypool.mapper.ConfigurationMapper;

@Service
public class ConfigurationService {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ConfigurationService.class);
	private @Autowired SqlSessionFactory ssf;
		
	public boolean insertOne(Configuration c) {
		SqlSession ss = ssf.openSession(true);
		ConfigurationMapper cm = ss.getMapper(ConfigurationMapper.class);
		
		try {
			cm.insertOne(c);
			ss.close();
			return true;
		} catch(Exception e) {
			ss.close();
			return false;
		}
	}
	
	public Configuration selectByName(String name) {
		SqlSession ss = ssf.openSession(true);
		ConfigurationMapper cm = ss.getMapper(ConfigurationMapper.class);
		
		Configuration c = new Configuration();
		c.setName(name);
		
		c =  cm.selectByName(c);
		ss.close();
		return c;
	}
	
	public boolean updateByName(Configuration c) {
		SqlSession ss = ssf.openSession(true);
		ConfigurationMapper cm = ss.getMapper(ConfigurationMapper.class);
		
		int i = cm.updateByName(c);
		
		return i == 1;
	}
}
