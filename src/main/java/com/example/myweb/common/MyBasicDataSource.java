package com.example.myweb.common;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;


@Component
public class MyBasicDataSource extends BasicDataSource {
	
	public MyBasicDataSource() {
		super.setDriverClassName("org.h2.Driver");
		super.setUrl("jdbc:h2:tcp://localhost/~/test");
		super.setUsername("sa");
		super.setPassword("");
	}
	

}
