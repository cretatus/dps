package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Component;
import ru.vk.cometa.model.Package;
import ru.vk.cometa.model.Platform;

public interface ComponentRepository extends JpaRepository<Component, Integer>, VersionedObjectRepository{
	List<Component> findByPlatform(Platform platform);
	//@Query("select с from Component c where c.package = ?1")
	List<Component> findByPack(Package pack);
}