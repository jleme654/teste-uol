package br.com.b2w.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author julio
 * @since 2019-03-26
 * @version 1.0.0
 * 
 */
@JsonIgnoreProperties
public class ItemVO {
	private String name;// ": "Impressora",
	private int code;// ": "5",
	private String date;// ": "2016-10-05T14:30:37.040Z",
	private DimensionVO dimension;// ": {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DimensionVO getDimension() {
		return dimension;
	}

	public void setDimension(DimensionVO dimension) {
		this.dimension = dimension;
	}

}
