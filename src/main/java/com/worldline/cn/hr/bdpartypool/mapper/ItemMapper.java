package com.worldline.cn.hr.bdpartypool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.worldline.cn.hr.bdpartypool.dao.Item;

public interface ItemMapper extends Mapper {

	final String INSERT_ONE = "INSERT INTO ITEMS VALUES"
			+ "(NULL, '${name}')";
	
	final String GET_ALL = "SELECT * FROM ITEMS";
	
	final String GET_BY_ID = "SELECT * FROM ITEMS WHERE ID='${id}'";
	
	final String GET_BY_NAME = "SELECT * FROM ITEMS WHERE "
			+ "NAME='${name}'";
	
	@Insert(INSERT_ONE)
	void insertOne(Item item);
	
	@Select(GET_ALL)
	List<Item> getAll();
	
	@Select(GET_BY_ID)
	Item getByID(Item item);
	
	@Select(GET_BY_NAME)
	Item getByName(Item item);
}
