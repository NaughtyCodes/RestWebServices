package org.rest.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import java.sql.Date;
import java.sql.SQLData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
import javax.xml.bind.DatatypeConverter;
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

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/Application")
public class Application {
	
    private String data = "Some Data";
    private Date lastModified = new Date();
	private InputStream uploadFileStream;
	
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
	public String sel(@PathParam("id") Integer id,
						@HeaderParam("authorization") String authKey,
						@Context Request req) throws IOException{
		
		System.out.println(authKey);
        String[] authParts = authKey.split("\\s+");
        //String authMsg = authParts[1];
        System.out.println(authParts);
        System.out.println(new BASE64Decoder().decodeBuffer(authParts[0]));
		CustomerCURD c = new CustomerCURD();
		Customer customer = new Customer();
		customer.setId(id);
		String result = c.getById(customer).toString();
		if (result.length()==0)
			return "NO RECORD FOUND....!!";
		else 
			return result;
	}
	
	//------------------------------------
	
	@GET
	@Produces("text/plain")
	public Response get() {
	    CacheControl cc = new CacheControl();
	    cc.setMaxAge(10);
	    return Response.ok("Some Data").cacheControl(cc).build();
	}
	
	 

	 
	    @GET
	    @Path("/last-modified")
	    @Produces("text/plain")
	    public Response get(@Context Request request) {

	        ResponseBuilder builder = request.evaluatePreconditions(lastModified);
	        if (builder != null) {
	        	System.out.println("from client");
	            return builder.build();
	        }
	        System.out.println(lastModified);
	        System.out.println("from server");
	        return Response.ok(data).lastModified(lastModified).build();
	    }
	 
	    @POST
	    @Consumes("text/plain")
	    public Response post(String data) {
	        this.data = data;
	        lastModified = new Date();
	        return Response.noContent().build();
	    }

	    @GET
	    @Path("/Auth")
	    @Produces("text/plain")
	    public String auth(){
	    	return "hiiiiiii auth is working";
	    }
	    //-----------------------
	
	@GET
	@Path("/Select/Customer/Etag/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selEtag(@PathParam("id") Integer id,
						    @Context Request req) throws IOException, NoSuchAlgorithmException{
		CacheControl cc = new CacheControl();
		cc.setMaxAge(180);
		
	    
		CustomerCURD c = new CustomerCURD();
		Customer customer = new Customer();
		customer.setId(id);
		customer = c.getById(customer).get(0);
		String fname = customer.getFirstName();
		MessageDigest digest = MessageDigest.getInstance("MD5");
	    byte[] hash = digest.digest(fname.getBytes(StandardCharsets.UTF_8));
	    String hex = DatatypeConverter.printHexBinary(hash);
		//Calculate the ETag on last modified date of user resource  
		EntityTag etag = new EntityTag(hex);		
		System.out.println("Etag_:"+etag);
		Response.ResponseBuilder rb = null;
		rb = req.evaluatePreconditions(etag);
		//If ETag matches the rb will be non-null; 
        //Use the rb to return the response without any further processing
		if(rb != null){
			System.out.println("from cache -------->>");
			return Response.notModified().entity(customer).type(MediaType.APPLICATION_JSON).build();
		}

		rb = Response.ok((customer)).cacheControl(cc).tag(etag);
		System.out.println("from server -------->>");
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
	
	@POST
	@Path("/Auth/Forms/Data")
	@Produces(MediaType.TEXT_PLAIN)
	public String form01(
			@FormParam("name") String name,
			@FormParam("age") String age,
			@FormParam("city") String city
			){
		
		String inData = "Name_:"+name+"\n"
				+"Age_:"+age+"\n"
				+"City_:"+city+"\n";
		System.out.println(inData);
		return inData;
	}

	@POST
	@Path("/Auth/Forms/Upload")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA) 
	public String formUpload01(
			@FormDataParam("file") InputStream uploadFileStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail 
			) throws IOException{
		OutputStream out = new FileOutputStream(new File("e:\\upload.txt"));
		int read=0;
		byte[] b = new byte[1024];
		java.nio.file.Path path = Paths.get("e:\\upload.txt");
		
		System.out.println(path.toString());
		while((read = uploadFileStream.read(b))!=-1){
			out.write(b, 0, read);
		}
		out.flush();
		out.close();
		URL url = Application.class.getProtectionDomain().getCodeSource().getLocation();
		System.out.println(url);
		java.nio.file.Path source = Paths.get("E:\\upload.txt");
		java.nio.file.Path target = Paths.get("D:\\GitHub\\fileOut.txt");
		java.nio.file.Path f = Files.move(source,target,StandardCopyOption.REPLACE_EXISTING);
		System.out.println(fileDetail.toString());
		return fileDetail.toString();
	}

}

