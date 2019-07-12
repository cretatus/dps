package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Metatype;

public interface MetatypeRepository extends JpaRepository<Metatype, String>{
	Metatype findByCode(String code);
	List<Metatype> findByMetaobject(String metaobject);
}