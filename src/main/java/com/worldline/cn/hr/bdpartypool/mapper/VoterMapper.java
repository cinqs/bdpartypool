package com.worldline.cn.hr.bdpartypool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.worldline.cn.hr.bdpartypool.dao.Voter;

public interface VoterMapper extends Mapper {

	final String INSERT_ONE = "INSERT INTO VOTERS "
			+ " VALUES (NULL, '${ip}')";
	
	final String SELECT_ALL = "SELECT * FROM VOTERS";
	final String SELECT_BY_ID = "SELECT * FROM VOTERS "
			+ "WHERE ID='${id}'";
	final String SELECT_BY_IP = "SELECT * FROM VOTERS "
			+ "WHERE IP='${ip}'";
	
	@Insert(INSERT_ONE)
	void insertOne(Voter voter);
	
	@Select(SELECT_ALL)
	List<Voter> selectAll();
	
	@Select(SELECT_BY_ID)
	Voter selectById(Voter voter);
	
	@Select(SELECT_BY_IP)
	Voter selectByIp(Voter voter);
}
