package com.user.mgmt.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.mgmt.controller.UserController;
import com.user.mgmt.dto.InitiatorTempDto;
import com.user.mgmt.dto.UserDto;
import com.user.mgmt.entity.InitiatorDetails;
import com.user.mgmt.entity.InitiatorTemp;
import com.user.mgmt.repo.InitiatorDetailsRepo;
import com.user.mgmt.repo.InitiatorTempRepo;
import com.user.mgmt.repo.UserRepo;
import com.user.mgmt.service.MasterService;
import com.user.mgmt.service.UserService;
import com.user.mgmt.service.utils.Constants;

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
			pMap.put("User ID", details);
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
					if ("User ID".equals(key)) {
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

}
