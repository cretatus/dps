package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.AppEntity;

public interface EntityRepository extends JpaRepository<AppEntity, Integer>, VersionedObjectRepository{
}