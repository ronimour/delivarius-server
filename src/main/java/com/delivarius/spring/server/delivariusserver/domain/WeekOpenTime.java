package com.delivarius.spring.server.delivariusserver.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.utils.PersistableUtils;

@Entity
public class WeekOpenTime implements Persistable<Long> {

	public static final String FORMAT = "HH:mm";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String monOpenTime;
	
	private String monCloseTime;
	
	private String tueOpenTime;
	
	private String tueCloseTime;
	
	private String wedOpenTime;
	
	private String wedCloseTime;
	
	private String thuOpenTime;
	
	private String thuCloseTime;
	
	private String friOpenTime;
	
	private String friCloseTime;
	
	private String satOpenTime;
	
	private String satCloseTime;
	
	private String sunOpenTime;
	
	private String sunCloseTime;
	
	public WeekOpenTime() {}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getMonOpenTime() {
		return monOpenTime;
	}

	public void setMonOpenTime(String monOpenTime) {
		this.monOpenTime = monOpenTime;
	}

	public String getMonCloseTime() {
		return monCloseTime;
	}

	public void setMonCloseTime(String monCloseTime) {
		this.monCloseTime = monCloseTime;
	}

	public String getTueOpenTime() {
		return tueOpenTime;
	}

	public void setTueOpenTime(String tueOpenTime) {
		this.tueOpenTime = tueOpenTime;
	}

	public String getTueCloseTime() {
		return tueCloseTime;
	}

	public void setTueCloseTime(String tueCloseTime) {
		this.tueCloseTime = tueCloseTime;
	}

	public String getWedOpenTime() {
		return wedOpenTime;
	}

	public void setWedOpenTime(String wedOpenTime) {
		this.wedOpenTime = wedOpenTime;
	}

	public String getWedCloseTime() {
		return wedCloseTime;
	}

	public void setWedCloseTime(String wedCloseTime) {
		this.wedCloseTime = wedCloseTime;
	}

	public String getThuOpenTime() {
		return thuOpenTime;
	}

	public void setThuOpenTime(String thuOpenTime) {
		this.thuOpenTime = thuOpenTime;
	}

	public String getThuCloseTime() {
		return thuCloseTime;
	}

	public void setThuCloseTime(String thuCloseTime) {
		this.thuCloseTime = thuCloseTime;
	}

	public String getFriOpenTime() {
		return friOpenTime;
	}

	public void setFriOpenTime(String friOpenTime) {
		this.friOpenTime = friOpenTime;
	}

	public String getFriCloseTime() {
		return friCloseTime;
	}

	public void setFriCloseTime(String friCloseTime) {
		this.friCloseTime = friCloseTime;
	}

	public String getSatOpenTime() {
		return satOpenTime;
	}

	public void setSatOpenTime(String satOpenTime) {
		this.satOpenTime = satOpenTime;
	}

	public String getSatCloseTime() {
		return satCloseTime;
	}

	public void setSatCloseTime(String satCloseTime) {
		this.satCloseTime = satCloseTime;
	}

	public String getSunOpenTime() {
		return sunOpenTime;
	}

	public void setSunOpenTime(String sunOpenTime) {
		this.sunOpenTime = sunOpenTime;
	}

	public String getSunCloseTime() {
		return sunCloseTime;
	}

	public void setSunCloseTime(String sunCloseTime) {
		this.sunCloseTime = sunCloseTime;
	}

	@Override
	public boolean isNew() {
		return PersistableUtils.isNew(this);
	}

	@Override
	public boolean equals(Object obj) {
		return PersistableUtils.equalsPersistable(this, obj);
	}

}
