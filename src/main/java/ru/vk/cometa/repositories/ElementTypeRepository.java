package ru.vk.cometa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.ElementType;

public interface ElementTypeRepository extends JpaRepository<ElementType, Integer>{
}