package com.worldline.cn.hr.bdpartypool.restController;

import java.io.IOException;
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
import com.worldline.cn.hr.bdpartypool.dao.Item;
import com.worldline.cn.hr.bdpartypool.dao.Record;
import com.worldline.cn.hr.bdpartypool.dao.Voter;
import com.worldline.cn.hr.bdpartypool.mapper.ItemMapper;
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
	public String vote(HttpServletRequest req,
			@RequestParam(value="id") int id,
			HttpServletResponse res) throws IOException {
		String ip = IPUtils.getIPAddr(req);
		int voterID = checkIP(ip);
		int round = BdPartyPoolApplication.getRound();
		logger.debug("received voting from " + ip + " for " + id);

		//these ip and id mapper can be buffered or cached...
		
		SqlSession ss = ssf.openSession(true);
		
		RecordMapper rm = ss.getMapper(RecordMapper.class);
		
		Record record = new Record();
		record.setVoteRound(round);
		record.setVoteBy(voterID);
		List<Record> records = rm.getByVoter(record);
		
		if(records.size() >= ticketMax) {
			//res.getWriter().write("no more tickets available...");
			return "you have reached the maximum tickets limit...";
		} else {
			record.setVoteBy(voterID);
			record.setVoteFor(id);
			record.setVoteRound(round);
			
			try {
				rm.insertOne(record);
			} catch (Exception e) {
				logger.warn(e);
				return "There is an error while recording your voting...try later please";
			}
			
			return "Thanks for your voting...";
		}
	}
	
	@RequestMapping(value="/propose", method=RequestMethod.GET)
	public String propose(HttpServletRequest req,
			@RequestParam(value="name") String name) {
		String ip = IPUtils.getIPAddr(req);
		logger.debug("received proposal from " + ip + " for " + name);
		
		if(checkItemExists(name)) {
			return "this item is already registered, and can be voted...";
		} else {
			return "item been registered, thank you for your proposal";
		}
	}
	
	private boolean checkItemExists(String name) {
		if(null == name || name.isEmpty()) {
			throw new IllegalArgumentException("item name can't be null");
		} else {
			SqlSession ss = ssf.openSession(true);
			ItemMapper im = ss.getMapper(ItemMapper.class);
			
			Item item = new Item();
			item.setName(name);
			
			if(null == im.getByName(item)) {
				im.insertOne(item);
				ss.close();
				return false;
			} else {
				ss.close();
				return true;
			}
		}
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
		ss.close();
		return voter.getId();
	}
}
