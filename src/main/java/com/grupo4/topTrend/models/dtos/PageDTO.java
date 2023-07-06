package com.grupo4.topTrend.models.dtos;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDTO<T> {
	private List<T> content;
	private Integer page;
	private Integer size;
	@JsonProperty("total_elements")
	private Long totalElements;
	@JsonProperty("total_pages")
	private Integer totalPages;
}
