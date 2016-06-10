package org.rest.services.resource;

public class Account {
	
	private Integer accId;
	private Integer globalAccNo;
	private Integer customerId;
	private String createdData;
	
	public Account(Integer accId, Integer globalAccNo, Integer customerId,String createdData) {
		super();
		this.accId = accId;
		this.globalAccNo = globalAccNo;
		this.customerId = customerId;
		this.createdData = createdData;
	}
	
	public Integer getAccId() {
		return this.accId;
	}

	public Integer getGlobalAccNo() {
		return this.globalAccNo;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public String getCreatedData() {
		return this.createdData;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public void setGlobalAccNo(Integer globalAccNo) {
		this.globalAccNo = globalAccNo;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setCreatedData(String createdData) {
		this.createdData = createdData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [accId=");
		builder.append(this.accId);
		builder.append(", globalAccNo=");
		builder.append(this.globalAccNo);
		builder.append(", customerId=");
		builder.append(this.customerId);
		builder.append(", createdData=");
		builder.append(this.createdData);
		builder.append("]");
		return builder.toString();
	}
	
}
