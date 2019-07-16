package ru.vk.cometa.service;

import org.springframework.stereotype.Service;

import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.User;

@Service
public class UserService extends BaseService{
	public String getPermission(User user, Application app) {
		Application application = applicationRepository.findOne(app.getId());
		if(application.getOwnerUser().getId().equals(user.getId())) {
			return "owner";
		}
		return invitationRepository.findByApplicationAndEmail(application, user.getEmail()).getPermission();
	}
	
}
