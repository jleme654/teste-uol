package br.com.b2w.utils;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 
 * @author julio
 * @since 2019-03-26
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

	public static String treatmentDate(String dataitem) {
		String ano = dataitem.substring(0, 4);
		String mes = dataitem.substring(5, 7);
		String dia = dataitem.substring(8, 10);
		
		StringBuilder sb = new StringBuilder();
		sb.append(dia).append("-").append(mes).append("-").append(ano);
	    return sb.toString();
	}
	
}
