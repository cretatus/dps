package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.MajorVersion;

public interface MajorVersionRepository extends JpaRepository<MajorVersion, Integer>{
	List<MajorVersion> findByModule(AppModule module);
	List<MajorVersion> findByApplication(Application application);
	MajorVersion findByModuleAndNumber(AppModule module, Integer number);
}