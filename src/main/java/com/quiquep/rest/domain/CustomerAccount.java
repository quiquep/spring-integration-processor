package com.quiquep.rest.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "name"
})
@XmlRootElement(name = "CustomerAccount")
public class CustomerAccount {

	private String id;
	private String name;
	
	public CustomerAccount() {}
	
	public CustomerAccount(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CustomerAccount [id=" + id + ", name=" + name + "]";
	}
	
}


