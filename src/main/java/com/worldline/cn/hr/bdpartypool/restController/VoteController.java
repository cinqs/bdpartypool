package com.worldline.cn.hr.bdpartypool.restController;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldline.cn.hr.bdpartypool.BdPartyPoolApplication;
import com.worldline.cn.hr.bdpartypool.dao.Record;
import com.worldline.cn.hr.bdpartypool.dao.Voter;
import com.worldline.cn.hr.bdpartypool.mapper.RecordMapper;
import com.worldline.cn.hr.bdpartypool.mapper.VoterMapper;
import com.worldline.cn.hr.bdpartypool.utils.IPUtils;

@RestController
@RequestMapping(value="/api/vote/")
public class VoteController {
	
	private Logger logger = Logger.getLogger(VoteController.class);
	private @Autowired SqlSessionFactory ssf;
	private @Value("${pool.ticket.max}") int ticketMax;

	@RequestMapping(value="/")
	public boolean vote(HttpServletRequest req,
			@RequestParam(value="id") int id,
			HttpServletResponse res) throws IOException {
		String ip = IPUtils.getIPAddr(req);
		int voterID = checkIP(ip);
		int round = BdPartyPoolApplication.getRound();
		logger.debug("received voting from " + ip + " for " + id);

		//these ip and id mapper can be buffered or cached...
		
		SqlSession ss = ssf.openSession(true);
		
		RecordMapper rm = ss.getMapper(RecordMapper.class);
		VoterMapper vm = ss.getMapper(VoterMapper.class);
		
		Record record = new Record();
		record.setVoteRound(round);
		record.setVoteBy(voterID);
		List<Record> records = rm.getByVoter(record);
		
		if(records.size() >= ticketMax) {
			res.getWriter().write("no more tickets available...");
			return false;
		} else {
			record.setRecordAt(new Timestamp(new Date().getTime()));
			record.setVoteBy(voterID);
			record.setVoteFor(id);
			record.setVoteRound(round);
			
			try {
				rm.insertOne(record);
			} catch (Exception e) {
				logger.warn(e);
				return false;
			}
			
			return true;
		}
	}
	
	@RequestMapping(value="/propose", method=RequestMethod.GET)
	public boolean propose(HttpServletRequest req,
			@RequestParam(value="name") String name) {
		String ip = IPUtils.getIPAddr(req);
		logger.debug("received proposal from " + ip + " for " + name);
		
		return true;
	}
	
	private int checkIP(String ip) {
		SqlSession ss = ssf.openSession(true);
		VoterMapper vm = ss.getMapper(VoterMapper.class);
		Voter voter = new Voter();
		voter.setIp(ip);
		
		if(null == vm.selectByIp(voter)) {
			vm.insertOne(voter);
		}
		
		voter = vm.selectByIp(voter);
		return voter.getId();
	}
}
