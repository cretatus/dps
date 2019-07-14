package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.vk.cometa.model.AppEntity;
import ru.vk.cometa.model.Attribute;
import ru.vk.cometa.model.Key;

public interface KeyRepository extends JpaRepository<Key, Integer>, VersionedObjectRepository{
	List<Key> findByEntity(AppEntity entity);

	@Query("SELECT a FROM Key ek, Attribute a WHERE ek.entity = ?1 and a.structure = ek.structure")
	List<Attribute> findAttributesByEntity(AppEntity entity);
}