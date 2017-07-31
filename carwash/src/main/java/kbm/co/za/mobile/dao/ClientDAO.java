package kbm.co.za.mobile.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kbm.co.za.mobile.contants.ClientConstants;
import kbm.co.za.mobile.utils.DateUtils;
import kbm.co.za.mobile.vo.Client;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ClientDAO {
	
	private MongoClient getMongoClient() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(ClientConstants.MONGO_DB_HOSTNAME, ClientConstants.MONGO_DB_PORT);
		return mongoClient;
	}
	
	public List<Client> searchClientByBirthMonth(String month) {
		List<Client> list = new ArrayList<Client>();
		try {
			List<Client> dbClients = getAllClients();
			System.out.println(dbClients);
			if(month != null &&  !month.isEmpty()) {
				
			} else {
				month = DateUtils.getMonthFromDate(new Date());
			}
			System.out.println("month : " + month);
			for (Client c : dbClients) {
				System.out.println("date " + c.getDateOfBirth());
				if (c.getDateOfBirth() != null && !c.getDateOfBirth().equalsIgnoreCase("null")) {
					Date date = DateUtils.parseToDate(c.getDateOfBirth(), DateUtils.PATTERN_YYYY_MM_DD);
					String dMonth = DateUtils.getMonthFromDate(date);
					System.out.println( " dMonth " + dMonth );
					if (month.equalsIgnoreCase(dMonth)) {
						list.add(c);
					}
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean insertClient(Client client ) {
		try {
			MongoDatabase db = getMongoClient().getDatabase(ClientConstants.MONGO_DB_CLIENT);
			MongoCollection<Document> col =  db.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);
			Client dbClient = searchClientByRegNo(client.getRegNo());
			if (dbClient != null) {
				throw new Exception("Client Already exists");
			}
						
			BasicDBObject document = new BasicDBObject();
			
//			document.append(key, value)
					
			document.put("name", client.getName());
			document.put("surname", client.getSurname());
			document.put("regNo", client.getRegNo());
			document.put("cellNumber", client.getCellNumber());
			document.put("createdDate", new Date());
			document.put("dateOfBirth", client.getDateOfBirth());
			System.out.println("document " + document.toJson());
			
			col.insertOne(new Document(document));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Client searchClientByRegNo(String regNo){
		Client client = new Client();
		try {
			DB db = getMongoClient().getDB(ClientConstants.MONGO_DB_CLIENT);
			DBCollection table = db.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("regNo", regNo);

			DBCursor cursor = table.find(searchQuery);
			System.out.println("Count : " + cursor.count());
			if (cursor.count() == 0) {
				return null;
			}
			while (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				System.out.println(dbObject.get("name"));
				System.out.println(dbObject.get("surname"));
				client.setName(dbObject.get("name").toString());
				client.setSurname(dbObject.get("surname").toString());
				client.setRegNo(String.valueOf(dbObject.get("regNo")));
				client.setCellNumber(String.valueOf(dbObject.get("cellNumber")));
				client.setDateOfBirth(String.valueOf(dbObject.get("dateOfBirth")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return client;
	}

	public List<Client> getAllClients() {
		List<Client> list = new ArrayList<Client>();
		try {
			DB db = getMongoClient().getDB(ClientConstants.MONGO_DB_CLIENT);
			DBCollection table = db.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			//searchQuery.put("name", "mkyong");

			DBCursor cursor = table.find(searchQuery);

			while (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				System.out.println(dbObject.get("name"));
				System.out.println(dbObject.get("surname"));
				Client client = new Client();
				client.setName(dbObject.get("name").toString());
				client.setSurname(dbObject.get("surname").toString());
				client.setRegNo(String.valueOf(dbObject.get("regNo")));
				client.setCellNumber(String.valueOf(dbObject.get("cellNumber")));
				client.setDateOfBirth(String.valueOf(dbObject.get("dateOfBirth")));
				list.add(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void deleteClient(String regNo) {
		try {
			DB db = getMongoClient().getDB(ClientConstants.MONGO_DB_CLIENT);
			DBCollection table = db.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);
			
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("regNo", regNo);

			table.findAndRemove(searchQuery);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void deleteAll() {
		try {
			DB db = getMongoClient().getDB(ClientConstants.MONGO_DB_CLIENT);
			DBCollection table = db.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);
			
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			
			DBCursor cursor = table.find(searchQuery);

			while (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				table.remove(dbObject);
			};
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
