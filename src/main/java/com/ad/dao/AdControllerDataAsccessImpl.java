package com.ad.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.util.*;

import com.mongodb.*;
import java.util.UUID;
import java.util.HashMap;
import java.util.Random;


public class AdControllerDataAsccessImpl implements AdControllerDataAccess {

	MongoClient mongoClient = new MongoClient();
	DB db = mongoClient.getDB( "ebay" );
	DBCollection coll = db.getCollection("advertisements");
	
	@Override
	public String add(String content) {
		try{
			String _id = UUID.randomUUID().toString();
			BasicDBObject doc = new BasicDBObject("content", content);
			doc.append("_id", _id);
			doc.append("impression", 0);
			doc.append("click", 0);
			coll.insert(doc);
			return _id;
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return "";
	}

	@Override
	public String get(String id) {
		try{
			BasicDBObject query = new BasicDBObject();
			query.put("_id", id);
			DBObject result = null;
			if(id == null || id.length() == 0){
				result = coll.findOne();
			}else {
				result = coll.findOne(query);
			}
			return result.toString();
		}catch(Exception e){
			// handle it
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return "";
	}

	@Override
	public boolean delete(String id) {
		try{
			BasicDBObject query = new BasicDBObject();
			query.put("_id", id);
			WriteResult result = coll.remove(query);
			return true;
		}catch(Exception e){
			// handle it
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return false;
	}

	@Override
	public boolean registerImpression(String adId) {
		try{
			BasicDBObject query = new BasicDBObject();
			query.put("_id", adId);
			DBObject result = coll.findOne(query);
			double impression = Double.valueOf( result.get("impression").toString());
			result.put("impression", impression+1);
			coll.update(query, result);
			return true;
		}catch(Exception e){
			// handle it
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return false;
	}

	@Override
	public boolean registerClick(String adId) {
		try{
			BasicDBObject query = new BasicDBObject();
			query.put("_id", adId);
			DBObject result = coll.findOne(query);
			double impression = Double.valueOf(result.get("click").toString());
			result.put("click", impression+1);
			coll.update(query, result);
			return true;
		}catch(Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return false;
	}

	@Override
	public String smartGet() {
		String str = "";
		HashMap<Integer, List<String>> ads = new HashMap<>();
		BasicDBObject query = new BasicDBObject();
		DBCursor cursor = coll.find(query);
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			double click = Double.valueOf(obj.get("click").toString());
			double impression = Double.valueOf( obj.get("impression").toString());
			int ctr = (int) Math.round((click/impression) * 10);
			//if(ctr == 0) ctr =1;
			if(ads.containsKey(ctr)){
				List<String> l = ads.get(ctr);
				l.add(obj.toString());
				ads.put(ctr,l );
			}else {
				List<String> l =new ArrayList<String>();
				l.add(obj.toString());
				ads.put(ctr,l);
			}
		}
		HashMap<Integer, Integer> rangeToKeyMap = new HashMap<>();
		int i =0;
		
		for(Integer ctr : ads.keySet()){
			int range = ctr+i;
			System.out.println("Range: " + range);
			rangeToKeyMap.put(range, ctr);
			i = range;
		}
		
		Random rand = new Random();

		int  n = rand.nextInt(i+1) ;
		System.out.println("Random interger: " + n);
		
		int[] sortedKeys = new int[rangeToKeyMap.keySet().size()];
		int count = 0;
		for(Integer ii : rangeToKeyMap.keySet()){
			sortedKeys[count++] =ii;
		}
		
		Arrays.sort(sortedKeys);
		
		for(Integer range : sortedKeys){
			if(n <= range){
				int ctrToBeselected = rangeToKeyMap.get(range);
				List<String> l = ads.get(ctrToBeselected);
				int selectedIndex = rand.nextInt(l.size());
				return l.get(selectedIndex);
			}
		}
		
		return str;
	}

}
