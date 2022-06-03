package com.actia.projects.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface CalendarEvents {

	@JsonProperty("Id")
	String getId();
	@JsonProperty("Subject")
	String getSubject();
	@JsonProperty("StartTime")
	Date getStartTime();
	@JsonProperty("EndTime")
	Date getEndTime();
}
