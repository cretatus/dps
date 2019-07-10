package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Area;

public interface AreaRepository extends JpaRepository<Area, Integer>, VersionedObjectRepository{
}