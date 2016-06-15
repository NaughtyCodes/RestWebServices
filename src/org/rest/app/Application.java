package org.rest.app;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
//import java.sql.Date;
import java.sql.SQLData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;


import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.sf.ehcache.hibernate.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.rest.services.CustomerCURD;
import org.rest.services.resource.Customer;

import sun.misc.BASE64Decoder;
import org.apache.log4j.Logger;


@Path("/Application")
public class Application {
	
	
	
	@GET
	@Path("/Select/Customer/Json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonSel(@HeaderParam("authorization") String authKey) throws IOException, ParseException{
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = format.parse("1999-05-01");
		Customer c = new Customer();
		c.setDob(date);
		c.setFirstName("firstName");
		c.setLastName("lastName");
		c.setName("dfsaaaafa");
		System.out.println(c);
		
		return Response.status(200).entity(c).build();
	}
	
	@GET
	@Path("/Select/Customer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sel(	@PathParam("id") Integer id,
						@HeaderParam("authorization") String authKey,
						@Context Request req) throws IOException{
		CacheControl cc = new CacheControl();
		cc.setMaxAge(180);
		
		System.out.println(authKey);
        String[] authParts = authKey.split("\\s+");
        //String authMsg = authParts[1];
        System.out.println(authParts);
        System.out.println(new BASE64Decoder().decodeBuffer(authParts[0]));
		CustomerCURD c = new CustomerCURD();
		Customer customer = new Customer();
		customer.setId(id);
		customer = c.getById(customer).get(0);
		String fname = customer.getFirstName();
		System.out.println(fname);
		
		//String result = c.getById(customer).toString();
		//Calculate the ETag on last modified date of user resource  
		EntityTag etag = new EntityTag(fname.hashCode()+"");		
		System.out.println("Etag_:"+etag);
		Response.ResponseBuilder rb = null;
		rb = req.evaluatePreconditions();
		//If ETag matches the rb will be non-null; 
        //Use the rb to return the response without any further processing
		if(rb != null){
			return rb.cacheControl(cc).tag(etag).build();
		}

		rb = Response.ok((customer)).cacheControl(cc).tag(etag);
		return rb.build();		
		/*if (result.length()==0)
			return "NO RECORD FOUND....!!";
		else 
			return result;*/
	}
	
	@GET
	@Path("/Search/Customer/{selChar}")
	@Produces(MediaType.TEXT_PLAIN)
	public String selId(@PathParam("selChar") String selChar){
		CustomerCURD c = new CustomerCURD();
		Customer customer = new Customer();
		customer.setName(selChar+"%");
		List<Customer> result = c.read(customer);
		return result.toString();
	}
	
	@GET
	@Path("/Update/Customer")
	@Produces(MediaType.TEXT_PLAIN)
	public void update() throws ParseException{
		CustomerCURD c = new CustomerCURD();
		Customer customer = new Customer();
		customer.setId(700);
		customer.setFirstName("MOHAN");
		customer.setLastName("KRISHNAN");
		customer.setName("MOHANAKRISHNAN K");
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = format.parse("1999-05-01");
		customer.setDob(date);
		c.update(customer);		
	}

	@GET
	@Path("/Insert/Customer/{name}/{fname}/{lname}")
	//@Produces(MediaType.TEXT_PLAIN)
	public String insert(
			@PathParam("name") String name,
			@PathParam("fname") String fname,
			@PathParam("lname") String lname
			) throws ParseException{
		CustomerCURD c = new CustomerCURD();
		Customer customer = new Customer();
		//customer.setId(4);
		customer.setFirstName(fname);
		customer.setLastName(lname);
		customer.setName(name);
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");	
		String sdate = format.format(new Date());
		Date date = format.parse(sdate);
		customer.setDob(date);
		c.insert(customer);	
		return "record inserted seccussfully record = "+customer.toString();
	}
	
	@GET
	@Path("/Delete/Customer/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(@PathParam("id") Integer id){
		CustomerCURD c = new CustomerCURD();
		Customer customer = new Customer();
		customer.setId(id);
		c.delete(customer);
		return "record deleted successfully id = "+id;
	}
}
