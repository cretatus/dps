package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Transformation;

public interface TransformationRepository extends JpaRepository<Transformation, Integer>, VersionedObjectRepository{
}