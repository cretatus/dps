package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.vk.cometa.model.Stereotype;
import ru.vk.cometa.model.Metatype;
import ru.vk.cometa.model.Version;

public interface StereotypeRepository extends JpaRepository<Stereotype, Integer>, VersionedObjectRepository{
	List<Stereotype> findByVersionAndMetatype(Version version, Metatype metatype);
	@Query("select s from Stereotype s where s.version = ?1 and s.metatype.metaobject = ?2")
	List<Stereotype> findByVersionAndMetaobject(Version version, String metaobject);
	@Query("select s from Stereotype s where s.version = ?1 and s.metatype.code = ?2 and isDefault = true")
	Stereotype findDefault(Version version, String metatypeCode);
}