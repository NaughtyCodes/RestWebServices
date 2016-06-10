package org.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.rest.services.resource.Account;
import org.rest.services.resource.Customer;

public class CustomerCURD implements Operation {
	
	AnnotationConfiguration cfg = new AnnotationConfiguration();
	Session session = cfg.configure("hibernate.cfg.xml").buildSessionFactory().openSession();		

	@Override
	public void call() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void create(T c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void update(T c) {
		// TODO Auto-generated method stub
		Transaction txn = session.beginTransaction();
		if(c instanceof Customer){
			System.out.println(c.toString());
			session.saveOrUpdate(c);
			session.flush();
		}
		txn.commit();
		session.close();

		
	}

	@Override
	public <T> List<T> read(T c) {
		// TODO Auto-generated method stub
		List<T> list=new ArrayList<T>();
		if(c instanceof Customer){
			
			Query q = session.getNamedQuery("getCustomerByName");
			q.setParameter(0, ((Customer) c).getName());
			List result = q.list();
			list = result;
			System.out.println(result.toString());
			StringBuilder sb = new StringBuilder();
			
			for(Object o : result){
				Customer as = (Customer)o;
				System.out.println(as.getName());
				sb.append(as.getName()+"__\n");
				}
			session.close();
			} else if(c instanceof Account){}
		return list;
	}

	@Override
	public <T> List<T> getById(T c) {
		// TODO Auto-generated method stub
		List<T> list=new ArrayList<T>();
		if(c instanceof Customer){
			
			Query q = session.getNamedQuery("getCustomerById");
			q.setParameter("id", ((Customer) c).getId());
			List result = q.list();
			list = result;
			System.out.println(result.toString());
			StringBuilder sb = new StringBuilder();
			
			for(Object o : result){
				Customer as = (Customer)o;
				System.out.println(as.getName());
				sb.append(as.getName()+"__\n");
				}
			session.close();
			} else if(c instanceof Account){}
		return list;
	}
	
	@Override
	public <T> void delete(T c) {
		// TODO Auto-generated method stub
		Transaction txn = session.beginTransaction();
		if(c instanceof Customer){
			System.out.println(((Customer) c).getId());			
			session.delete(((Customer) c));
			session.flush();
		}
		txn.commit();
		session.close();
	}

	@Override
	public <T> void insert(T c) {
		// TODO Auto-generated method stub
		Transaction txn = session.beginTransaction();
		if(c instanceof Customer){
			System.out.println(c.toString());
			session.save(c);
			session.flush();
		}
		txn.commit();
		session.close();
	}
}
