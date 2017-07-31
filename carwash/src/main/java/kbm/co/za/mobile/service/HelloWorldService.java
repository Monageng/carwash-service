package kbm.co.za.mobile.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/HelloWorldService")  
public class HelloWorldService {

	@GET
	public Response getMsg() {

		String output = "Jersey say : Hello uptated";

		return Response.status(200).entity(output).build();

	}

}
