package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Platform;

public interface PlatformRepository extends JpaRepository<Platform, Integer>, VersionedObjectRepository{

}