package com.user.mgmt.service.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;

public class Constants {
	public static int okStatus = HttpStatus.OK.value();
	public static int badStatus = HttpStatus.BAD_REQUEST.value();
	public static int dupStatus = HttpStatus.CONFLICT.value();
	public static int createdStatus = HttpStatus.CREATED.value();

	public static String creation = "CREATION";
	public static String updation = "UPDATION";
	public static String initiated = "INITIATED";
	public static String approved = "APPROVED";
	public static String rejected = "REJECTED";

	public static String addMonth(int monthToAddNumber) throws ParseException {
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.MONTH, monthToAddNumber);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sf.format(calender.getTime());
		return date;
	}
}
