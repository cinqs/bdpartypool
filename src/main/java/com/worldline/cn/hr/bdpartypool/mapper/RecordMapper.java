package com.worldline.cn.hr.bdpartypool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.worldline.cn.hr.bdpartypool.dao.Record;

public interface RecordMapper extends Mapper {

	final String INSERT_ONE = "INSERT INTO RECORDS "
			+ "(RECORDAT, VOTEFOR, VOTEBY, VOTEROUND) VALUES "
			+ "(${recordAt}, ${voteFor}, ${voteBy}, ${voteRound})";
	
	final String SELECT_BY_ITEM = "SELECT * FROM RECORDS WHERE"
			+ "VOTEFOR='${voteFor}' and VOTEROUND='${voteRound}'";
	
	final String SELECT_BY_VOTER = "SELECT * FROM RECORDS WHERE"
			+ " VOTEBY=${voteBy} and VOTEROUND=${voteRound}";
	
	@Insert(INSERT_ONE)
	void insertOne(Record record);
	
	@Select(SELECT_BY_ITEM)
	List<Record> getByItem(Record record);
	
	@Select(SELECT_BY_VOTER)
	List<Record> getByVoter(Record record);
}
