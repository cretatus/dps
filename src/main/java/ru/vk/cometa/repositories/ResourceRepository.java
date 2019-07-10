package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer>{
	List<Resource> findByApplication(Application application);
}