package com.quiquep.rest.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "passportId"
})
@XmlRootElement(name = "CustomerAccountRequest")
public class CustomerAccountRequest {

	private String passportId;

	public CustomerAccountRequest() {}
	
	public CustomerAccountRequest(String passportId) {
		super();
		this.passportId = passportId;
	}
	
	public String getPassportId() {
		return passportId;
	}
	
	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	@Override
	public String toString() {
		return "CustomerAccountRequest [passportId=" + passportId + "]";
	}
}


