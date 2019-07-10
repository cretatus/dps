package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Dependency;
import ru.vk.cometa.model.Version;

public interface DependencyRepository extends JpaRepository<Dependency, Integer>{
	List<Dependency> findByInfluencerVersion(Version influencerVersion);
	List<Dependency> findByDependentVersion(Version dependentVersion);
}