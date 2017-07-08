package com.xebia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xebia.beans.Herd;
import com.xebia.dao.HerdDAO;

@Service("service")
public class RESTServiceImpl implements RESTService {

	@Autowired
	private HerdDAO herdDao;

	public HerdDAO getHerdDao() {
		return herdDao;
	}
	
	public void saveHerd(Herd herd) {
		getHerdDao().saveHerd(herd);
	}

	public Herd getHerd() {
		return getHerdDao().getHerd();
	}
}
