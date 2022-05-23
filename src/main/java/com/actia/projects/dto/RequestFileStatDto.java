package com.actia.projects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RequestFileStatDto {

	String getFirstname();
	int getValue();
}
