package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Assembly;
import ru.vk.cometa.model.Build;

public interface BuildRepository extends JpaRepository<Build, Integer>{
	List<Build> findByAssembly(Assembly assembly);
	List<Build> findByApplication(Application application);
}