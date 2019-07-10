package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Assembly;

public interface AssemblyRepository extends JpaRepository<Assembly, Integer>{
	List<Assembly> findByApplication(Application application);
}