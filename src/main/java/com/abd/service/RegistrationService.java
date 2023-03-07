package com.abd.service;

import java.util.Map;

import com.abd.bindings.User;

public interface RegistrationService {
	//My own analysis 
//	public String saveUserInfo(UserEntity userEntity);
//	
//	public List<CountryEntity>loadCountry();
//	
//	public List<StateEntity>loadState();
//	
//	public List<CityEntity>loadCity();
//	
//	public boolean UniqueEmail(String userEmail);
	
	//now Sirs analysis
	public boolean uniqueEmail(String userEmail);
	
	public Map<Integer, String>getCountries();
	
	public Map<Integer, String>getState(Integer countryId);
	
	public Map<Integer, String>getCities(Integer stateId);
	
	public boolean registerUser(User user);
	
	
	
}
