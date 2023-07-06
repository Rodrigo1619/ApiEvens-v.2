package com.grupo4.topTrend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.Category;

public interface CategoryRepository
	extends JpaRepository<Category, UUID> {
	List<Category> findAll();
	Category findOneByCode(UUID code);
}
