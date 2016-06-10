package org.rest.services;

import java.util.List;

public interface Operation {
	
	public void call();
	public <T> void create(T collection);
	public <T> void update(T collection);
	public <T> List<T> read(T collection);
	public <T> List<T> getById(T collection);
	public <T> void delete(T collection);
	public <T> void insert(T collection);
	
}
