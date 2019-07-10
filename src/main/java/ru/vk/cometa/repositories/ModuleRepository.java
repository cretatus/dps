package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.Application;

public interface ModuleRepository extends JpaRepository<AppModule, Integer>{
	List<AppModule> findByApplication(Application application);
}