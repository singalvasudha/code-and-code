package com.xebia.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.xebia.beans.Herd;
import com.xebia.beans.LabYak;

@Service("herdDao")
public class HerdDAOImpl implements HerdDAO{
	
	private static Logger log = Logger.getLogger(HerdDAOImpl.class.getName());
	
	@Autowired
	private NamedParameterJdbcTemplate jdbctemplate;

	public NamedParameterJdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}

	@Override
	public void saveHerd(Herd herd) {
		String sql = "insert into herd (name, age, sex) VALUES (:name, :age, :sex)";
		
	      Map<String, Object> m = new HashMap<String, Object>();
	      if(herd != null && herd.getLabYaks().size()>0){
		      for(LabYak yak:herd.getLabYaks())
		      {
		    	  m.put("name", yak.getName());
		    	  m.put("age", yak.getAge());
		    	  m.put("sex", yak.getSex());
		    	  getJdbctemplate().update(sql, m);
		      }
		      log.info("saveHerd() successful");
	      }
	      else
		      log.info("saveHerd() not successful");
	      
	}
	
	public Herd getHerd() {
		String sql = "select * from herd";
		Herd herd = new Herd();
		
		List<LabYak> yaks = getJdbctemplate().query(sql, new BeanPropertyRowMapper<LabYak>(LabYak.class));
				
		herd.setLabYaks(new ArrayList<LabYak>(yaks));
		log.info("getHerd() successful");
		log.info("Herd size: "+yaks.size());
		return herd;
	}

}
