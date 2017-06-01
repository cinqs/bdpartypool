package com.worldline.cn.hr.bdpartypool.dao;
 
import java.sql.Timestamp;

public class Record {

	private Timestamp recordAt;
	private int voteFor;
	private int voteBy;
	private int voteRound;
	
	public Timestamp getRecordAt() {
		return recordAt;
	}
	public void setRecordAt(Timestamp recordAt) {
		this.recordAt = recordAt;
	}
	
	public int getVoteFor() {
		return voteFor;
	}
	public void setVoteFor(int voteFor) {
		this.voteFor = voteFor;
	}
	public int getVoteBy() {
		return voteBy;
	}
	public void setVoteBy(int voteBy) {
		this.voteBy = voteBy;
	}
	public int getVoteRound() {
		return voteRound;
	}
	public void setVoteRound(int voteRound) {
		this.voteRound = voteRound;
	}

}
