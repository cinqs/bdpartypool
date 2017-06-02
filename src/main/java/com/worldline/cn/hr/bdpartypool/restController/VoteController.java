package com.worldline.cn.hr.bdpartypool.restController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.worldline.cn.hr.bdpartypool.services.ItemService;
import com.worldline.cn.hr.bdpartypool.utils.IPUtils;

@RestController
@RequestMapping(value="/api/vote/")
public class VoteController {
	
	private Logger logger = Logger.getLogger(VoteController.class);
	private @Autowired SqlSessionFactory ssf;
	private @Value("${pool.ticket.max}") int ticketMax;
	
	private @Autowired ItemService is;

	@RequestMapping(value="/")
	public Map<String, String> vote(HttpServletRequest req,
			@RequestParam(value="id") int id,
			HttpServletResponse res) throws IOException {
		Map<String, String> map = new HashMap<>();
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
			map.put("status", "warning");
			map.put("msg", "you have reached the maximum tickets limit...");
		} else {
			record.setVoteBy(voterID);
			record.setVoteFor(id);
			record.setVoteRound(round);
			
			try {
				rm.insertOne(record);
			} catch (Exception e) {
				logger.warn(e);
				map.put("status", "danger");
				map.put("msg", "There is an error while recording your voting...try later please");
				return map;
			}
			
			map.put("status", "success");
			map.put("msg", "Thanks for your voting...");
		}
		
		return map;
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
