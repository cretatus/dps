package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Structure;

public interface StructureRepository extends JpaRepository<Structure, Integer>, VersionedObjectRepository{
}