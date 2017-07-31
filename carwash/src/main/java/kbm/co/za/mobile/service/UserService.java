package kbm.co.za.mobile.service;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@Path("/UserService")
public class UserService {

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {

		String output = "Jersey say : Hello for user4s";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "Monageng");
		jsonObject.put("surname", "Motsabi");

		MongoClient mongo = new MongoClient("localhost", 27017);
		System.out.println("Mongo " + mongo.getAddress());
		List<String> dbs = mongo.getDatabaseNames();
		for (String db : dbs) {
			System.out.println(db);
		}

		DB db = mongo.getDB("userdb");

		DBCollection table = db.getCollection("user");
		BasicDBObject document = new BasicDBObject();
		document.put("name", "mkyong");
		document.put("age", 30);
		document.put("createdDate", new Date());
		table.insert(document);

		BasicDBObject searchQuery2 = new BasicDBObject().append("name",
				"mkyong");

		DBCursor cursor2 = table.find(searchQuery2);
		System.out.println("Curser " + cursor2);

		while (cursor2.hasNext()) {
			System.out.println(cursor2.next());
		}

		return Response.status(200).entity(jsonObject.toString()).build();

	}

}
