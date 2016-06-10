package org.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rest.services.resource.Track;

@Path("/hello")
public class Hello {

	/**
	 * @param args
	 */
	
	@GET
	@Produces("application/json")
	public String sayPlainTextHellow(){
		

		return "HELLO jerser Plain Output";
		
		}
	
		@GET
		@Path("/get")
		@Produces(MediaType.APPLICATION_JSON)
		public Track getTrackInJSON() {

			Track track = new Track();
			track.setTitle("Enter Sandman");
			track.setSinger("Metallica");

			return track;

		}

}
