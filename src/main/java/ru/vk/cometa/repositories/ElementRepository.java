package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Area;
import ru.vk.cometa.model.Element;
import ru.vk.cometa.model.Version;

public interface ElementRepository extends JpaRepository<Element, Integer>, VersionedObjectRepository{
	Element findByVersionAndAreaAndSysname(Version version, Area area, String sysname);
}