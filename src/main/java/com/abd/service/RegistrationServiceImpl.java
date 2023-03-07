package com.abd.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abd.bindings.User;
import com.abd.constants.AppConstants;
import com.abd.entities.CityEntity;
import com.abd.entities.CountryEntity;
import com.abd.entities.StateEntity;
import com.abd.entities.UserEntity;
import com.abd.exception.RegAppException;
import com.abd.props.AppProperties;
import com.abd.repositories.CityRepository;
import com.abd.repositories.CountryRepository;
import com.abd.repositories.StateRepository;
import com.abd.repositories.UserRepository;
import com.abd.utils.EmailUtils;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	CountryRepository countryRepo;

	@Autowired
	StateRepository stateRepo;

	@Autowired
	CityRepository cityRepo;

	@Autowired
	EmailUtils emailUtils;

	@Autowired
	AppProperties appProperties;

	@Override
	public boolean uniqueEmail(String userEmail) {
		UserEntity userEntity = userRepo.findByUserEmail(userEmail);
		if (userEntity != null) {
			return false;
		} else {
			return true;
		}
		// return userEntity==null;
	}

	// IQ: where you have used collections in your projects
	@Override
	public Map<Integer, String> getCountries() {
		List<CountryEntity> findAll = countryRepo.findAll();

		HashMap<Integer, String> countryMap = new HashMap<>();

		for (CountryEntity entity : findAll) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}
		return countryMap;
	}

	@Override
	public Map<Integer, String> getState(Integer countryId) {
		List<StateEntity> stateList = stateRepo.findByCountryId(countryId);

		HashMap<Integer, String> statesMap = new HashMap<Integer, String>();

		for (StateEntity state : stateList) {
			statesMap.put(state.getStateId(), state.getStateName());
		}
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityEntity> citiesList = cityRepo.findByStateId(stateId);

		HashMap<Integer, String> cityMap = new HashMap<Integer, String>();
		for (CityEntity city : citiesList) {
			cityMap.put(city.getCityId(), city.getCityName());
		}
		return cityMap;
	}

	@Override
	public boolean registerUser(User user) {
		user.setUserPwd(generateTempPwd());
		user.setUserAccStatus(AppConstants.LOCKED);

		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(user, entity);

		UserEntity save = userRepo.save(entity);

		if (save.getUserId() != null) {
			return sendRegEmail(user);
		}
		return false;
	}

	private boolean sendRegEmail(User user) {
		boolean emailSent = false;

		try {
			Map<String, String> messages = appProperties.getMessages();
			// String subject = "User Registration Success";
			String subject = messages.get(AppConstants.REG_MAIL_SUBJECT);
			String bodyFileName = messages.get(AppConstants.REG_MAIL_BODY_TEMPLATE_FILE);
			String body = readMailBody(bodyFileName, user);
			emailUtils.sendEmail(subject, body, user.getUserEmail());
			emailSent = true;
		} catch (Exception e) {
			throw new RegAppException(e.getMessage());
		}

		return emailSent;
		// TODO Auto-generated method stub

	}

	private String readMailBody(String filename, User user) {
		String mailBody = null;
		// IQ: WHERE YOU HAVE USED STRINGBUFFER IN UR PROJECT
		StringBuffer buffer = new StringBuffer();

		Path path = Paths.get(filename);

		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(line -> {
				buffer.append(line);
			});
			mailBody = buffer.toString();
			mailBody = mailBody.replace(AppConstants.FNAME, user.getUserFname());
			mailBody = mailBody.replace(AppConstants.EMAIL, user.getUserEmail());
			mailBody = mailBody.replace(AppConstants.TEMP_PWD, user.getUserPwd());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return mailBody;
	}

	private String generateTempPwd() {
		String tempPwd = null;
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		tempPwd = random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return tempPwd;
	}

}
