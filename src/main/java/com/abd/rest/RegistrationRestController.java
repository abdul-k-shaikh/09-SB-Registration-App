package com.abd.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abd.bindings.User;
import com.abd.constants.AppConstants;
import com.abd.service.RegistrationService;

@RestController
public class RegistrationRestController {
	
	@Autowired
	private RegistrationService regService;
	
	@GetMapping("/emailcheck/{email}")
	public String checkEmail(@PathVariable String email) {
		boolean uniqueEmail = regService.uniqueEmail(email);
		if(uniqueEmail) {
			return AppConstants.UNIQUE;
		}
		else {
			return AppConstants.DUPLICATE;
		}
	}
	
	@GetMapping("/countries")
	public Map<Integer, String> getCountries(){
		return regService.getCountries();
	}
	
	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable Integer countryId ){
		Map<Integer,String>states =regService.getState(countryId);
		return states;
	}
	
	@GetMapping("/city/{stateId}")
	public Map<Integer, String>getCities(@PathVariable Integer stateId){
		Map<Integer,String> cities=regService.getState(stateId);
		return cities;
	}
	
	@PostMapping("/saveuser")
	public String saveUser(@RequestBody User user) {
		boolean registerUser = regService.registerUser(user);
		if(registerUser) {
			return AppConstants.SUCCESS;
		}
		else {
			return AppConstants.FAIL;
		}
			
	}
	
	
}
