package org.rest.services.resource;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;

@Entity
@Table(name="rest_customer")
//@SQLInsert( sql="INSERT INTO Customer(id, name, first_name, last_name, dob) VALUES(?,upper(?),?,?,?)")
//@SQLUpdate( sql="UPDATE Customer SET id = ?, name = upper(?), first_name = ?, last_name = ?, dob = ? WHERE id = ?")
//@SQLDelete( sql="DELETE Customer WHERE id = ?")
//@SQLDeleteAll( sql="DELETE Customer")
@Loader(namedQuery = "getCustomerByName")
@NamedNativeQueries({
	@NamedNativeQuery(name="getCustomerByName", query="select * from rest_customer where display_name like ? ", resultClass = Customer.class),
	@NamedNativeQuery(name="getCustomerById", query="select * from rest_customer where customer_id = :id", resultClass = Customer.class),
	@NamedNativeQuery(name="delCustomerById", query="delete rest_customer where customer_id = :id", resultClass = Customer.class)
})
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="customer_id")
	private Integer id;
	@Column(name="display_name")
	private String name;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="date_of_birth")
	private Date dob;
	
/*	public Customer(Integer id, String name, String firstName, String lastName,String dob) {
		super();
		this.id = id;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
	}
*/	
	
	public Integer getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public Date getDob() {
		return this.dob;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + this.id + ", name=" + this.name
				+ ", firstName=" + this.firstName + ", lastName="
				+ this.lastName + ", dob=" + this.dob + "]";
	}
	
}
