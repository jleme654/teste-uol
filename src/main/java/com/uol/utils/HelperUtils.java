package com.uol.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author julio
 * @since 2019-04-10
 * @version 1.0.0
 * 
 */
public class HelperUtils {

	public static Date convertStringtoDate(String dataStr) {
		Date parsedDate = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy");
			parsedDate = formatter.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsedDate;
	}
	
	public static String convertDateToString(Date dataStr) {
		String todayAsString = null;
		try {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			todayAsString = df.format(dataStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todayAsString;
	}

	public static String treatmentDate(String dataitem) {
		String ano = dataitem.substring(0, 4);
		String mes = dataitem.substring(5, 7);
		String dia = dataitem.substring(8, 10);
		
		StringBuilder sb = new StringBuilder();
		sb.append(dia).append("-").append(mes).append("-").append(ano);
	    return sb.toString();
	}
	
}
