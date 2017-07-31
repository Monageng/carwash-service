package kbm.co.za.mobile.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import kbm.co.za.mobile.contants.ClientConstants;
import kbm.co.za.mobile.utils.DateUtils;
import kbm.co.za.mobile.vo.Client;
import kbm.co.za.mobile.vo.WashTransaction;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

public class WashDAO {

	private MongoClient getMongoClient() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(
				ClientConstants.MONGO_DB_HOSTNAME,
				ClientConstants.MONGO_DB_PORT);
		return mongoClient;
	}

	public boolean insertTransaction(WashTransaction transaction) {
		try {
			MongoDatabase db = getMongoClient().getDatabase(
					ClientConstants.MONGO_DB_CLIENT);
			System.out.println("DB : " + db);
			MongoCollection<Document> col = db
					.getCollection(ClientConstants.MONGO_DB_WASH_TABLE);
			System.out.println("col : " + col);

			Document document = new Document();
			document.put("regNo", transaction.getRegNo());
			document.put("count", 1);
			document.put("modifiedDate", DateUtils.parseToDate(
					transaction.getDate(), DateUtils.PATTERN_YYYY_MM_DD));
			System.out.println("document " + document.toJson());

			// ObjectMapper mapper = new ObjectMapper();
			// userProfile.set_id(UUID.randomUUID().toString());
			// transaction.setDate(date);
			// String jsonString = mapper.writeValueAsString(transaction);
			// System.out.println("JSON for Create : " + jsonString);

			col.insertOne(new Document(document));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Client searchClientByRegNo(String regNo) {
		Client client = new Client();
		try {
			DB db = getMongoClient().getDB(ClientConstants.MONGO_DB_CLIENT);
			DBCollection table = db
					.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);
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

				// TODO SET proper date of Birth
				client.setDateOfBirth("2017-01-01");
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
			DBCollection table = db
					.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			// searchQuery.put("name", "mkyong");

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
			DBCollection table = db
					.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);

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
			DBCollection table = db
					.getCollection(ClientConstants.MONGO_DB_CLIENT_TABLE);

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();

			DBCursor cursor = table.find(searchQuery);

			while (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				table.remove(dbObject);
			}
			;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<WashTransaction> getAllTransctions() {
		List<WashTransaction> list = new ArrayList<WashTransaction>();
		try {
			MongoDatabase db = getMongoClient().getDatabase(
					ClientConstants.MONGO_DB_CLIENT);
			MongoCollection<Document> table = db
					.getCollection(ClientConstants.MONGO_DB_WASH_TABLE);
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			Bson sort = Sorts.descending("regNo", "modifiedDate");

			FindIterable<Document> cursor = table.find(searchQuery);

			cursor.sort(sort);
			Iterator<Document> iter = cursor.iterator();

			String month = null;
			String dbMonth = "";
			String regNumber = "";
			String dbRegNo = "";
			WashTransaction wash = null;
			while (iter.hasNext()) {
				Document dbObject = iter.next();

				dbRegNo = dbObject.getString("regNo");
				Date date = dbObject.getDate("modifiedDate");
				if (date != null) {
					dbMonth = DateUtils.getMonthFromDate(date);
				}
				if (!regNumber.equalsIgnoreCase(dbRegNo)) {
					regNumber = dbRegNo;
					month = dbMonth;
					wash = new WashTransaction();
					wash.setMonth(dbMonth);
					wash.setRegNo(regNumber);
					wash.setCount(1);
					list.add(wash);
				} else {
					if (month.equalsIgnoreCase(dbMonth)) {
						wash.setCount(1 + wash.getCount());
					} else {
						month = dbMonth;
						wash = new WashTransaction();
						wash.setMonth(dbMonth);
						wash.setRegNo(regNumber);
						wash.setCount(1);
						list.add(wash);
					}
				}

				// String dbMonth =
				// cursor.getString(cursor.getColumnIndexOrThrow(WashTxn.Entry.COLUMN_NAME_MONTH));
				// count =
				// cursor.getInt(cursor.getColumnIndexOrThrow(WashTxn.Entry.COLUMN_NAME_COUNT));
				//
				// if (!regNo.equalsIgnoreCase(dbReg)) {
				// regNo = dbReg;
				// month = dbMonth;
				// dto = new CarWashTxnDto();
				// dto.setMonth(month);
				// dto.setRegNo(regNo);
				// dto.setCount(count);
				// list.add(dto);
				// } else {
				// if (month.equalsIgnoreCase(dbMonth)) {
				// dto.setCount(count + dto.getCount());
				// } else {
				// month = dbMonth;
				// dto = new CarWashTxnDto();
				// dto.setRegNo(regNo);
				// dto.setMonth(month);
				// dto.setCount(count);
				// list.add(dto);
				// }
				//
				// }

				// WashTransaction client = new WashTransaction();
				// client.setRegNo(String.valueOf(dbObject.get("regNo")));
				// //client.setCellNumber(Long.parseLong(dbObject.get("cellNumber").toString()));
				// list.add(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
