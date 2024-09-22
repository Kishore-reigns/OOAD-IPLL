package main;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class Mongo {
	
	
	public static void main(String[] args) {
		String url = "mongodb://localhost:27017";
		
		try(MongoClient mongoclient =  MongoClients.create(url)) {
			MongoDatabase db = mongoclient.getDatabase("ipllDb");
			
			MongoCollection<Document> collections = db.getCollection("lesson1");
			
			Document document = collections.find().first();
			
			int count = 0 ; 
			for(String collection : db.listCollectionNames()) {
				if(collection.contains("C")) {
					System.out.println(collection);
					count++ ;	
				}
				 
			}
		}
	}
}
