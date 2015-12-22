package com.quiquep.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quiquep.rest.dao.SmsProcessorDao;
import com.quiquep.rest.domain.Command;
import com.quiquep.rest.domain.CustomerAccount;
import com.quiquep.rest.domain.CustomerAccountRequest;

@Service("smsProcessorService")
public class SmsProcessorService {

	private static final String STATUSCODE_HEADER = "http_statusCode";
	private static Logger logger = Logger.getLogger(SmsProcessorService.class);
	
	//@Autowired
	//private SmsProcessorDao dao;

    public Message<?> get(Message<String> msg) {
    	String smstext = msg.getPayload().toString();
        
        if (smstext == null) {
            return MessageBuilder.fromMessage(msg)
                .copyHeadersIfAbsent(msg.getHeaders())
                .setHeader(STATUSCODE_HEADER, HttpStatus.NOT_FOUND)
                .build(); 
        }
        
        String passportId = smstext.split(" ")[2];
        
        return MessageBuilder.withPayload(passportId)
            .copyHeadersIfAbsent(msg.getHeaders())
            .setHeader(STATUSCODE_HEADER, HttpStatus.OK)
            .build();
    }
    
    public CustomerAccount getCustomer(Message<String> msg) {
    	String smstext = msg.getPayload().toString();
    	String passportId = smstext.split(" ")[2];
        return new CustomerAccount(passportId, "Test");
    }
    
    public CustomerAccountRequest getCustomerRequest(Message<?> msg) {
    	String smstext = msg.getPayload().toString();
    	String passportId = smstext.split(" ")[2];
        return new CustomerAccountRequest(passportId);
    }

    public CustomerAccountRequest getCustomerRequestCommand(Message<?> msg) {
    	Command command = (Command) msg.getPayload();
    	String passportId = command.getParams().get(1);
        return new CustomerAccountRequest(passportId);
    }
    
    public Command getCommand(Message<?> msg) {
    	String[] smstext = msg.getPayload().toString().split(" ");
    	String action = smstext[0];
    	String param1 = smstext[1];
    	String param2 = smstext[2];
    	List<String> params = new ArrayList<String>();
    	params.add(param1);
    	params.add(param2);
        return new Command(action, params);
    }

    @Transactional
    public Message<Command> getCommandMsg(Message<?> msg) {
    	String inputMessage = msg.getPayload().toString();
    	String flowId = getFlowId(msg);
    	//getDao().createProcess(flowId, inputMessage);
    	//getDao().trackProcess(flowId, "getCommandMsg");
    	String[] smstext = inputMessage.split(" ");
    	String action = smstext[0];
    	String param1 = smstext[1];
    	String param2 = smstext[2];
    	List<String> params = new ArrayList<String>();
    	params.add(param1);
    	params.add(param2);
        Command command = new Command(action, params);
        return MessageBuilder.withPayload(command)
                .copyHeadersIfAbsent(msg.getHeaders())
                .setHeader(STATUSCODE_HEADER, HttpStatus.OK)
                .build();        
    }
    
    @Transactional
    public Message<CustomerAccountRequest> getCustomerRequestCommandMsg(Message<?> msg) {
    	String flowId = getFlowId(msg);
    	//getDao().trackProcess(flowId, "getCustomerRequestCommandMsg");
    	Command command = (Command) msg.getPayload();
    	String passportId = command.getParams().get(1);
        CustomerAccountRequest req = new CustomerAccountRequest(passportId);
        return MessageBuilder.withPayload(req)
                .copyHeadersIfAbsent(msg.getHeaders())
                .setHeader(STATUSCODE_HEADER, HttpStatus.OK)
                .build();          
    }
    
    @Transactional
    public void trackFinishedProcess(Message<?> msg) {
    	//getDao().trackFinishedProcess(getFlowId(msg));
    }

	private String getFlowId(Message<?> msg) {
		return msg.getHeaders().get("MSGID").toString();
	}
    
	public String getHeaderMessageId(Message<?> msg) {
		return msg.getHeaders().getId().toString();
	}
    
    public String getFirstParam(Message<Command> msg) {
    	Command command = (Command) msg.getPayload();
    	return command.getParams().get(0);
    }
    
    public String getSecondParam(Message<Command> msg) {
    	Command command = (Command) msg.getPayload();
    	return command.getParams().get(1);
    }

}


