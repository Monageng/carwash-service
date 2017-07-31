package kbm.co.za.mobile.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import kbm.co.za.mobile.dao.ClientDAO;
import kbm.co.za.mobile.dao.WashDAO;
import kbm.co.za.mobile.vo.Client;
import kbm.co.za.mobile.vo.WashTransaction;

import com.sun.jersey.api.client.ClientResponse.Status;

@Path("/ClientService")
public class ClientService {

	ClientDAO dao = new ClientDAO();
	WashDAO washDAO = new WashDAO();

	@POST
	@Path("/client/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerClient(Client client) {
		boolean sucess = dao.insertClient(client);
		if (sucess) {
			return Response.ok("Client registered successfully").build();
		} else {
			return Response.status(500)
					.entity("Failed to register clients user exists").build();
		}
	}
	
	@POST
	@Path("/client/logTransaction")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logTransaction(WashTransaction washTransaction ) {
		boolean sucess = washDAO.insertTransaction(washTransaction);
		if (sucess) {
			return Response.ok("Recorded transaction successfully").build();
		} else {
			return Response.status(500)
					.entity("Failed to record transaction ").build();
		}
	}

	@GET
	@Path("/client")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClient(@QueryParam("name") String name) {
		return Response.ok(new Client()).build();
	}

	@GET
	@Path("/searchClientByRegNo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClientByRegNo(@QueryParam("regNo") String regNo) {
		if (regNo == null || regNo.isEmpty()) {
			throw new WebApplicationException(
					Status.BAD_REQUEST.getStatusCode());
		}

		Client client = dao.searchClientByRegNo(regNo);
		if (client == null) {
			throw new WebApplicationException(Status.NO_CONTENT.getStatusCode());
		} else {
			return Response.ok(client).build();
		}
	}

	@GET
	@Path("/clients")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClients() {
		System.getProperty("hostname");
		List<Client> list = dao.getAllClients();
		return Response.ok(list).build();
	}
	
	@GET
	@Path("/transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllTransactions() {
		System.out.println(System.getProperty("hostname"));
		List<WashTransaction> list = washDAO.getAllTransctions();
		return Response.ok(list).build();
	}
	
	@DELETE
	@Path("/deleteClient/{regNo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteClient(@PathParam("regNo") String regNo) {
		dao.deleteClient(regNo);
		return Response.ok("Deleted successfully").build();
	}
	
	@DELETE
	@Path("/deleteAllClient")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAllClient() {
		dao.deleteAll();
		return Response.ok("Deleted successfully").build();
	}
	
	@GET
	@Path("searchClientByBirthMonth")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Client> searchClientByBirthMonth(@QueryParam("month") String month) {
		List<Client>  list = dao.searchClientByBirthMonth(month);
		return list;
		
	}

}
