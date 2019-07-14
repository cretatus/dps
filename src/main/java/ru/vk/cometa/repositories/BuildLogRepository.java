package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Build;
import ru.vk.cometa.model.BuildLog;

public interface BuildLogRepository extends JpaRepository<BuildLog, Integer>{
	List<BuildLog> findByBuild(Build build);
}