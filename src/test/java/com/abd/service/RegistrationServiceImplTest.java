package com.abd.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.abd.entities.CountryEntity;
import com.abd.entities.UserEntity;
import com.abd.props.AppProperties;
import com.abd.repositories.CityRepository;
import com.abd.repositories.CountryRepository;
import com.abd.repositories.StateRepository;
import com.abd.repositories.UserRepository;
import com.abd.utils.EmailUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {
	@MockBean
	UserRepository userRepo;

	@MockBean
	CountryRepository countryRepo;

	@MockBean
	StateRepository stateRepo;

	@MockBean
	CityRepository cityRepo;

	@MockBean
	EmailUtils emailUtils;

	@MockBean //used to create a mock object 
	AppProperties appProperties;
	
	@InjectMocks //its used to inject above mock object into target class
	private RegistrationServiceImpl service;
	
	@Test
	public void uniquEmailTest() {
		when(userRepo.findByUserEmail("abd@gmail.com")).thenReturn(new UserEntity());
		boolean uniqueEmail = service.uniqueEmail("abd@gmail.com");
		assertFalse(uniqueEmail);
	}
	
//	@Test
//	public void getCountriesTest() {
//		HashMap<Integer, String> mp = new HashMap<>();
//		mp.put(1, "India");
//		mp.put(2, "USA");
//		when(countryRepo.findAll()).thenReturn(mp);
//	}
}
