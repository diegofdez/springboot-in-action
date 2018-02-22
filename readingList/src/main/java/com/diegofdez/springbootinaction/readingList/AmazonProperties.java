package com.diegofdez.springbootinaction.readingList;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="amazon") // All amazon.* properties from application.properties will be sent to setters in this class
public class AmazonProperties {
	private String associateId;

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
}
