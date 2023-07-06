package com.grupo4.topTrend.services;

import java.util.List;
import java.util.UUID;

import com.grupo4.topTrend.models.entities.Category;

public interface CategoryService {
	List<Category> findAll();
	Category findByCode(UUID code);
}
