package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Generator;
import ru.vk.cometa.model.Platform;
import ru.vk.cometa.model.Stereotype;

public interface GeneratorRepository extends JpaRepository<Generator, Integer>, VersionedObjectRepository{
	List<Generator> findByPlatform(Platform platform);
	List<Generator> findByStereotype(Stereotype stereotype);
}