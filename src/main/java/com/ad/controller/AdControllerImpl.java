package com.ad.controller;

import com.ad.dao.AdControllerDataAccess;
import com.ad.dao.AdControllerDataAsccessImpl;

public class AdControllerImpl implements AdController{
	
	AdControllerDataAccess dataAccess = new AdControllerDataAsccessImpl();

	@Override
	public String add(String content) {
		return dataAccess.add(content);
	}

	@Override
	public String get(String id) {
		return dataAccess.get(id);
	}

	@Override
	public boolean delete(String id) {
		return dataAccess.delete(id);
	}

	@Override
	public boolean registerImpression(String adId) {
		return dataAccess.registerClick(adId);
	}

	@Override
	public boolean registerClick(String adId) {
		return dataAccess.registerClick(adId);
	}

	@Override
	public String smartGet() {
		return dataAccess.smartGet();
	}

}
