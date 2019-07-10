package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Attribute;
import ru.vk.cometa.model.Structure;

public interface AttributeRepository extends JpaRepository<Attribute, Integer>, VersionedObjectRepository{
	List<Attribute> findByStructure(Structure structure);
	Attribute findByStructureAndSysname(Structure structure, String sysname);
}