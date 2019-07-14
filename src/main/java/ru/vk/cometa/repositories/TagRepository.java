package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> , VersionedObjectRepository{
}