package com.user.mgmt.service.impl;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.mgmt.dto.InitiatorTempDto;
import com.user.mgmt.dto.UserDto;
import com.user.mgmt.entity.InitiatorDetails;
import com.user.mgmt.entity.InitiatorTemp;
import com.user.mgmt.entity.User;
import com.user.mgmt.entity.UserAuthentication;
import com.user.mgmt.exception.ResourceNotFoundException;
import com.user.mgmt.repo.AuthRepo;
import com.user.mgmt.repo.InitiatorDetailsRepo;
import com.user.mgmt.repo.InitiatorTempRepo;
import com.user.mgmt.repo.UserRepo;
import com.user.mgmt.service.MasterService;
import com.user.mgmt.service.UserService;
import com.user.mgmt.service.utils.Constants;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private InitiatorTempRepo initiatorTempRepo;
	@Autowired
	private InitiatorDetailsRepo initiatorDetailsRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private MasterService masterService;
	@Autowired
	private AuthRepo authRepo;

	private final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(Constants.SALT, Constants.HASH_LENGTH,
			Constants.PARALLELISM, Constants.MEMORY, Constants.ITERATIONS);

	@Override
	public InitiatorTempDto saveRequest(UserDto userDto) {
		if (initiatorTempRepo.existsByCreatedUserAndRequestTypeAndStatus(userDto.getUsername(), Constants.creation,
				Constants.initiated)) {
			return null;
		} else if (!userRepo.existsByUsername(userDto.getUsername())) {
			InitiatorTemp temp = new InitiatorTemp();
			temp.setCreatedUser(userDto.getUsername());
			temp.setCreatedBy("DUMMY");
			temp.setRequestType(Constants.creation);
			temp.setStatus(Constants.initiated);
			InitiatorTemp savedTemp = initiatorTempRepo.save(temp);
			InitiatorDetails details;
			HashMap<String, Object> pMap = new HashMap<>();

			details = new InitiatorDetails(userDto.getUsername(), userDto.getUsername(), "", "");
			pMap.put("Username", details);
			details = new InitiatorDetails(userDto.getFirstName(), userDto.getFirstName(), "", "");
			pMap.put("First Name", details);
			details = new InitiatorDetails(userDto.getLastName(), userDto.getLastName(), "", "");
			pMap.put("Last Name", details);
			details = new InitiatorDetails(userDto.getPhoneNumber().toString(), userDto.getPhoneNumber().toString(), "",
					"");
			pMap.put("Phone Number", details);
			details = new InitiatorDetails(userDto.getCountryFk().toString(),
					masterService.getCountryById(userDto.getCountryFk()), "", "");
			pMap.put("Country", details);
			details = new InitiatorDetails(userDto.getStateFk().toString(),
					masterService.getStateById(userDto.getStateFk()), "", "");
			pMap.put("State", details);
			details = new InitiatorDetails(userDto.getAddress(), userDto.getAddress(), "", "");
			pMap.put("Address", details);
			details = new InitiatorDetails(userDto.getStatus(), userDto.getStatus(), "", "");
			pMap.put("Status", details);
			details = new InitiatorDetails(userDto.getEmail(), userDto.getEmail(), "", "");
			pMap.put("Email", details);
			details = new InitiatorDetails(userDto.getRole(), userDto.getRole(), "", "");
			pMap.put("Role", details);

			Iterator<Entry<String, Object>> iterator = pMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				InitiatorDetails dtls = new InitiatorDetails();
				dtls.setColumnName(key);
				dtls.setCreatedBy("DUMMY");
				dtls.setModifiedBy("DUMMY");
				dtls.setTempFk(savedTemp.getTempPk());

				Object obj = pMap.get(key);
				if (obj instanceof InitiatorDetails) {
					InitiatorDetails init = (InitiatorDetails) obj;
					if ("Username".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("First Name".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("Last Name".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("Phone Number".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("Country".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("State".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("Address".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("Status".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("Email".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					} else if ("Role".equals(key)) {
						dtls.setNewValue(init.getNewValue());
						dtls.setNewValueDesc(init.getNewValueDesc());
						dtls.setOldValue(init.getOldValue());
						dtls.setOldValueDesc(init.getOldValueDesc());
					}
				}

				initiatorDetailsRepo.save(dtls);
			}
			InitiatorTempDto tempDto = modelMapper.map(savedTemp, InitiatorTempDto.class);
			tempDto.setUserDto(userDto);
			return tempDto;
		}
		return null;
	}

	@Override
	@Transactional
	public InitiatorTempDto approveRequest(Long tempPk) throws ParseException {
		InitiatorTemp temp = initiatorTempRepo.findByTempPk(tempPk)
				.orElseThrow(() -> new ResourceNotFoundException("Request", "Id", tempPk.toString()));
		List<InitiatorDetails> map = initiatorDetailsRepo.findSelectedColumnsByTempFk(temp.getTempPk());
		User user = new User();

		for (InitiatorDetails row : map) {
			if ("First Name".equals(row.getColumnName())) {
				user.setFirstName(row.getNewValue());
			} else if ("Last Name".equals(row.getColumnName())) {
				user.setLastName(row.getNewValue());
			} else if ("Username".equals(row.getColumnName())) {
				user.setUsername(row.getNewValue());
			} else if ("Status".equals(row.getColumnName())) {
				user.setStatus(row.getNewValue());
			} else if ("Phone Number".equals(row.getColumnName())) {
				user.setPhoneNumber(Long.parseLong(row.getNewValue()));
			} else if ("Role".equals(row.getColumnName())) {
				user.setRole(row.getNewValue());
			} else if ("Country".equals(row.getColumnName())) {
				user.setCountryFk(Long.parseLong(row.getNewValue()));
			} else if ("State".equals(row.getColumnName())) {
				user.setStateFk(Long.parseLong(row.getNewValue()));
			} else if ("Email".equals(row.getColumnName())) {
				user.setEmail(row.getNewValue());
			} else if ("Address".equals(row.getColumnName())) {
				user.setAddress(row.getNewValue());
			}
		}

		user.setCreatedBy(temp.getCreatedBy());
		user.setModifiedBy("Dummy");

		UserAuthentication auth = new UserAuthentication();
		auth.setCreatedBy("Dummy");
		auth.setModifiedBy("Dummy");

		String password = getPassword();
		String encoded = encoder.encode(password);
		auth.setPassword(encoded);
		auth.setUsername(user.getUsername());
		auth.setPasswordExpiryDate(Constants.addMonth(3));
		authRepo.save(auth);
		userRepo.saveAndFlush(user);
		temp.setStatus(Constants.approved);
		InitiatorTemp savedTemp = initiatorTempRepo.save(temp);
		return modelMapper.map(savedTemp, InitiatorTempDto.class);

	}

	public static String getPassword() {
		SecureRandom random = new SecureRandom();
		int min = 8, max = 16;
		int length = random.nextInt((max - min) + 1) + min;

		List<Character> pwd = new ArrayList<>();
		pwd.add(Constants.LOWER.charAt(random.nextInt(Constants.LOWER.length())));
		pwd.add(Constants.UPPER.charAt(random.nextInt(Constants.UPPER.length())));
		pwd.add(Constants.DIGITS.charAt(random.nextInt(Constants.DIGITS.length())));
		pwd.add(Constants.SPECIAL.charAt(random.nextInt(Constants.SPECIAL.length())));

		while (pwd.size() < length) {
			pwd.add(Constants.ALL.charAt(random.nextInt(Constants.ALL.length())));
		}

		Collections.shuffle(pwd, random);
		StringBuilder password = new StringBuilder();
		pwd.forEach(password::append);

		log.warn("PASSWORD IS " + password.toString());

		return password.toString();
	}

	// FOR NEW USER THAT DOESN'T HAVE ADMIN ACCOUNT
	@Override
	@Transactional
	public UserDto createNewUser(UserDto userDto) throws ParseException {
		if (!userRepo.existsByUsername(userDto.getUsername())) {
			User user = modelMapper.map(userDto, User.class);
			userRepo.save(user);
			String password = getPassword();
			String encoded = encoder.encode(password);

			UserAuthentication auth = new UserAuthentication();
			auth.setCreatedBy("NEW_ADMIN_USER");
			auth.setModifiedBy("NEW_ADMIN_USER");
			auth.setPassword(encoded);
			auth.setUsername(user.getUsername());
			auth.setPasswordExpiryDate(Constants.addMonth(3));
			authRepo.save(auth);

			return userDto;
		}

		return null;
	}

	@Override
	public boolean existsByUsername(String username) {
		boolean exists = userRepo.existsByUsername(username);

		if (!exists)
			return false;

		return true;
	}

}