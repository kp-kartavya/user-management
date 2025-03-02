package com.user.mgmt.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.mgmt.controller.UserController;
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
	
	private int salt = 16;
	private int hashLength = 32;
	private int parallelism = 4;
	private int memory = 65536;
	private int iterations = 10;

	private final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(salt, hashLength, parallelism, memory,
			iterations);

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
				user.setUsername(row.getColumnName());
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
		log.info("Start method getPassword");
		int max = 16, min = 8, pwdLength = 0;
		int range = max - min + 1;
		int c = 'A';
		int rl = 0;
		char[] numChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		char[] lwrChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		char[] uprChar = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		char[] splChar = { '@', '$', '#' };
		for (int i = 0; i < min; i++) {
			pwdLength = (int) (Math.random() * 100 % range + min);
		}
		char[] pw = new char[pwdLength];
		for (int i = 0; i < pwdLength; i++) {
			rl = (int) (Math.random() * 4);
			switch (rl) {
			case 0:
				c = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				c = 'a' + (int) (Math.random() * 26);
				break;
			case 2:
				c = 'A' + (int) (Math.random() * 26);
				break;
			case 3:
				c = '@' + (int) (Math.random() * 3);
				break;
			}
			pw[i] = (char) c;
		}

		int f2 = 0, f3 = 0, f4 = 0, f5 = 0;
		for (int i = 0; i < pw.length; i++) {
			for (int j = 0; j < lwrChar.length; j++) {
				if (pw[i] == lwrChar[j]) {
					f2 = 1;
				}
			}
			for (int j = 0; j < uprChar.length; j++) {
				if (pw[i] == uprChar[j]) {
					f3 = 1;
				}
			}
			for (int j = 0; j < numChar.length; j++) {
				if (pw[i] == numChar[j]) {
					f4 = 1;
				}
			}
			for (int j = 0; j < splChar.length; j++) {
				if (pw[i] == splChar[j]) {
					f5 = 1;
				}
			}
		}
		log.info("End method getPassword method Password is: " + new String(pw));
		if (f2 == 1 && f3 == 1 && f4 == 1 && f5 == 1) {
			return new String(pw);
		} else {
			return getPassword();
		}
	}
}