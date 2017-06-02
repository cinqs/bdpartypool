package com.worldline.cn.hr.bdpartypool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.worldline.cn.hr.bdpartypool.dao.Configuration;

public interface ConfigurationMapper extends Mapper {

	final String INSERT_ONE = "INSERT INTO CONFIGURATIONS "
			+ "(ID, NAME, PROPERTY) "
			+ "VALUES(NULL, '${name}', '${property}')";
	final String SELECT_ALL = "SELECT * FROM CONFIGURATIONS";
	final String SELECT_BY_NAME = "SELECT * FROM CONFIGURATIONS "
			+ "WHERE NAME='${name}'";
	
	@Insert(INSERT_ONE)
	void insertOne(Configuration c);
	
	@Select(SELECT_ALL)
	List<Configuration> selectAll();
	
	@Select(SELECT_BY_NAME)
	Configuration selectByName(Configuration c);
}
