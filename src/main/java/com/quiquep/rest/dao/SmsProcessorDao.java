package com.quiquep.rest.dao;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class SmsProcessorDao {

	private JdbcTemplate jdbcTemplate;	
	private SimpleJdbcInsert insertProcess;
	
	private static final String CREATED = "CREATED";
	private static final String FINISHED = "FINISHED";
	
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertProcess = new SimpleJdbcInsert(dataSource)
	        .withTableName("process")
	        .usingColumns("flow_id", "status", "text")
	        .usingGeneratedKeyColumns("id");
    }
    
    public void createProcess(String flowId, String text) {
        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("flow_id", flowId);
        parameters.put("status", CREATED);
        parameters.put("text", text);
        insertProcess.execute(parameters);
    }
    
    public void trackProcess(String flowId, String status) {
        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("flow_id", flowId);
        parameters.put("status", status);
        insertProcess.execute(parameters);
    }

	public void trackFinishedProcess(String messageId) {
		trackProcess(messageId, FINISHED);		
	}    
    
}


