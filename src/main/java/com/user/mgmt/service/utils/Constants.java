package com.user.mgmt.service.utils;

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

}
