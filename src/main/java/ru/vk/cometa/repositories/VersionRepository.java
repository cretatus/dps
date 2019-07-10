package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.MajorVersion;
import ru.vk.cometa.model.Version;

public interface VersionRepository extends JpaRepository<Version, Integer>{
	List<Version> findByModule(AppModule module);
	List<Version> findByApplication(Application application);
	List<Version> findByMajorVersion(MajorVersion majorVersion);
	Version findByMajorVersionAndNumber(MajorVersion majorVersion, Integer number);
}