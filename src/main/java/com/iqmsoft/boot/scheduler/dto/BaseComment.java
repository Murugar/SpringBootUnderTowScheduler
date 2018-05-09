package com.iqmsoft.boot.scheduler.dto;


import com.fasterxml.jackson.databind.ObjectMapper;



public class BaseComment {

	@Override
	public String toString() {
		try {
			return toJsonString(this);
		} catch (Exception e) {
			return super.toString();
		}
	}
	
	protected static String toJsonString(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
	//	map.registerModule(new Module());
	
		return map.writeValueAsString(r);
	}
}
