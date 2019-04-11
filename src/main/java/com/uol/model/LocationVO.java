package com.uol.model;

public class LocationVO {
	private String distance;// ": 18593,
	private String title;// ": "São Paulo",
	private String location_type;// ": "City",
	private Long woeid;// ": 455827,
	private String latt_long;// ": "-23.562880,-46.654659"

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation_type() {
		return location_type;
	}

	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}

	public Long getWoeid() {
		return woeid;
	}

	public void setWoeid(Long woeid) {
		this.woeid = woeid;
	}

	public String getLatt_long() {
		return latt_long;
	}

	public void setLatt_long(String latt_long) {
		this.latt_long = latt_long;
	}

}
