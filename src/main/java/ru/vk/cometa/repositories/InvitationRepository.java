package ru.vk.cometa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Invitation;
import ru.vk.cometa.model.User;

public interface InvitationRepository extends JpaRepository<Invitation, Integer>{
	List<Invitation> findBySenderUser(User senderUser);
	List<Invitation> findByApplication(Application application);
	@Query("select i from Invitation i where i.application.ownerUser = ?1")
	List<Invitation> findByOwnerUser(User user);
}