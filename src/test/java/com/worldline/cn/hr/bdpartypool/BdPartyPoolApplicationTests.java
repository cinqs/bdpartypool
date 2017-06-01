package com.worldline.cn.hr.bdpartypool;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.worldline.cn.hr.bdpartypool.dao.Item;
import com.worldline.cn.hr.bdpartypool.mapper.ItemMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BdPartyPoolApplicationTests {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private @Autowired SqlSessionFactory ssf;

	@Test
	public void contextLoads() {
		SqlSession ss = ssf.openSession(true);
		
		ItemMapper im = ss.getMapper(ItemMapper.class);
		
		Item item = new Item();
		
		item.setName("orange");
		
		im.insertOne(item);


		List<Item> items = im.getAll();
		
		logger.info(items.size());
		
		item.setId(4);
		Item i = im.getByID(item);
		
		logger.info(i.getName());
	}

}
