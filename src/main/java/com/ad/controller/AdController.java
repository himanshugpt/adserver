package com.ad.controller;

public interface AdController {
	
	String add(String content);
	
	String get(String id);
	
	boolean delete(String id);
	
	boolean registerImpression(String adId);
	
	boolean registerClick(String adId);
	
	String smartGet();
}
