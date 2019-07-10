package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Subtype;

public interface SubtypeRepository extends JpaRepository<Subtype, String>{
	Subtype findByCode(String code);
	List<Subtype> findByMetaobject(String metaobject);
}