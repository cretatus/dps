package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.User;

public interface ApplicationRepository extends JpaRepository<Application, Integer>{
	List<Application> findByOwnerUser(User ownerUser);
}