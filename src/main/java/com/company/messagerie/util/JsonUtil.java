package com.company.messagerie.util;

import java.io.IOException;

import com.company.messagerie.model.MessageRequest;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static String fromObjectToJson(MessageRequest messageRequest) {
    	if(messageRequest == null) {
    		return null;
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.writeValueAsString(messageRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public static MessageRequest fromJsonToObject(String jsonObject) {
    	if(jsonObject == null) {
    		return null;
    	}
    	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.readValue(jsonObject, MessageRequest.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public static MessageRequest fromRowToObject(Row row) {
    	return JsonUtil.fromJsonToObject(row.getString("content"));
    }
}
