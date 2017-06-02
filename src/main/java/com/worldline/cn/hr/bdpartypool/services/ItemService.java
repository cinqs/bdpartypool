package com.worldline.cn.hr.bdpartypool.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldline.cn.hr.bdpartypool.BdPartyPoolApplication;
import com.worldline.cn.hr.bdpartypool.dao.Item;
import com.worldline.cn.hr.bdpartypool.dao.Record;
import com.worldline.cn.hr.bdpartypool.mapper.ItemMapper;
import com.worldline.cn.hr.bdpartypool.mapper.RecordMapper;

@Service
public class ItemService {

	private Logger logger = Logger.getLogger(ItemService.class);
	private @Autowired SqlSessionFactory ssf;
	
	public boolean insertOne(Item item) {
		SqlSession ss = ssf.openSession(true);
		ItemMapper im = ss.getMapper(ItemMapper.class);
		
		try {
			im.insertOne(item);
		} catch(Exception e) {
			logger.warn(e);
			ss.close();
			return false;
		}
		
		ss.close();
		return true;
	}
	
	public Item selectByName(String name){
		SqlSession ss = ssf.openSession(true);
		ItemMapper im = ss.getMapper(ItemMapper.class);
		
		Item item = new Item();
		item.setName(name);
		
		return im.getByName(item);
	}
	
	public List<Map<String, Object>> listItem() {
		List<Map<String, Object>> itemList = new ArrayList<>();
		int round = BdPartyPoolApplication.getRound();
		
		SqlSession ss = ssf.openSession(true);
		ItemMapper im = ss.getMapper(ItemMapper.class);
		RecordMapper rm = ss.getMapper(RecordMapper.class);
		
		List<Item> items = im.getAll();
		
		if(items != null) {
			for(Item item : items) {
				Map<String, Object> iMap = new HashMap<>();
				int id = item.getId();
				iMap.put("id", id);
				String name = item.getName();
				iMap.put("name", name);
				
				Record record = new Record();
				record.setVoteFor(id);
				record.setVoteRound(round);
				List<Record> records = rm.getByItem(record);
				if(null != records) {
					iMap.put("count", records.size());
				} else {
					iMap.put("count", 0);
				}
				
				itemList.add(iMap);
			}
		}
		
		ss.close();
		return itemList;
	}
}
