package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Package;
import ru.vk.cometa.model.Version;

public interface PackageRepository extends JpaRepository<Package, Integer>, VersionedObjectRepository{
	//@Query("SELECT p FROM AppPackage p WHERE p.version = ?1 AND p.parentPackage = ?2 ORDER BY p.number")
	List<Package> findByVersionAndParent(Version version, Package parent);
	List<Package> findByParent(Package parent);
	List<Package> findByApplicationAndParent(Application application, Package parent);
}