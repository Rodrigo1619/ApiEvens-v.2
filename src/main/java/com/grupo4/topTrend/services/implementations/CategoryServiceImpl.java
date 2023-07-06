package com.grupo4.topTrend.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.entities.Category;
import com.grupo4.topTrend.repositories.CategoryRepository;
import com.grupo4.topTrend.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category findByCode(UUID code) {
		return categoryRepository.findOneByCode(code);
	}
}
