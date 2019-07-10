package ru.vk.cometa.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import ru.vk.cometa.controller.BaseService;
import ru.vk.cometa.model.Identified;

@Service
public class ValidationService extends BaseService{
	@PersistenceContext
	EntityManager entityManager;
	
	public CheckUnique unique(Identified object) {
		return new CheckUnique(entityManager, object);
	}
}

