package org.rest.services;

public class RestOperationFactory {

	public CustomerCURD getOperation(String entityName){
		
		switch(entityName){
			case "Customer":
				return new CustomerCURD();
			default:
				return null;
		}
		
	}
	
}
