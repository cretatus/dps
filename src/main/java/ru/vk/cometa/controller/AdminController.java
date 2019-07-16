package ru.vk.cometa.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Identified;
import ru.vk.cometa.model.Invitation;
import ru.vk.cometa.model.User;
import ru.vk.cometa.service.BaseService;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseService{
	@RequestMapping(value = "current_application", method = RequestMethod.GET)
	public Application getCurrentApplication(Principal principal) throws ManagedException {
		return userRepository.findByLogin(principal.getName()).getCurrentApplication();
	}


	@RequestMapping(value = "save_application", method = RequestMethod.POST)
	public void saveApplication(@RequestBody Application application, Principal principal) throws ManagedException {
		application.setOwnerUser(userRepository.findByLogin(principal.getName()));
		assertNotNull(application.getName(), "Name");
		validationService.unique(application).addParameter("name", application.getName())
				.addParameter("ownerUser", application.getOwnerUser()).check();
		applicationRepository.save(application);
	}

	@RequestMapping(value = "remove_application", method = RequestMethod.POST)
	public void removeApplication(@RequestBody Application application, Principal principal) throws ManagedException {
		applicationRepository.delete(application);
	}

	@RequestMapping(value = "save_invitation", method = RequestMethod.POST)
	public void saveInvitation(@RequestBody Invitation invitation, Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		assertNotNull(invitation.getApplication(), "Application");
		assertNotNull(invitation.getEmail(), "Email");
		assertNotNull(invitation.getPermission(), "Permission");
		invitation.setSenderUser(user);
		invitation.setStatus("SENT");
		validationService.unique(invitation).addParameter("email", invitation.getEmail())
				.addParameter("application", invitation.getApplication()).check();
		invitationRepository.save(invitation);
		emailUtil.send("Co-Meta service invitation",
				"Lets join to co-Meta! That's cool! But I do not know where it is now. You have to ask this guy about URL - "
						+ user.getEmail() + ". He (or she) wrote this text: " + invitation.getDescription(),
				invitation.getEmail(), user.getEmail());
	}

	@RequestMapping(value = "remove_invitation", method = RequestMethod.POST)
	public void removeInvitation(@RequestBody Invitation invitation, Principal principal) throws ManagedException {
		invitationRepository.delete(invitation);
	}
	
	@RequestMapping(value = "read_applications", method = RequestMethod.GET)
	public List<Application> getApplicationsByOwner(Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		List<Application> list = applicationRepository.findByOwnerUser(userRepository.findByLogin(principal.getName()));
		List<Identified> listIds = new ArrayList<Identified>(list);
		for(Invitation invitation : invitationRepository.findAcceptedByEmail(user.getEmail())) {
			if(!contains(listIds, invitation.getApplication())) {
				listIds.add(invitation.getApplication());
				list.add(invitation.getApplication());
			}
		}
		return list;
	}

	@RequestMapping(value = "read_permissions", method = RequestMethod.GET)
	public Map<Integer, String> getPermissions(Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		Map<Integer, String> result = new HashMap<Integer, String>();
		for(Application application: getApplicationsByOwner(principal)) {
			if(application.getOwnerUser().getId().equals(user.getId())) {
				result.put(application.getId(), "owner");
			}
			else  {
				Invitation invitation = invitationRepository.findByApplicationAndEmail(application, user.getEmail());
				if(invitation != null) {
					result.put(application.getId(), invitation.getPermission());
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "read_all_invitatoins", method = RequestMethod.GET)
	public List<Invitation> getAllInvitations(Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		List<Invitation> result = invitationRepository.findBySenderUser(user);
		List<Identified> listIds = new ArrayList<Identified>(result);
		for(Invitation invitation : invitationRepository.findByOwnerUser(user)) {
			if(!contains(listIds, invitation)) {
				result.add(invitation);
				listIds.add(invitation);
			}
		}
		return result;
	}
	@RequestMapping(value = "read_my_invitatoins", method = RequestMethod.GET)
	public List<Invitation> getMyInvitations(Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		return invitationRepository.findActiveByEmail(user.getEmail());
	}

	@RequestMapping(value = "select_application", method = RequestMethod.POST)
	public void selectApplication(@RequestBody Application application, Principal principal) {
		User user = userRepository.findByLogin(principal.getName());
		user.setCurrentApplication(applicationRepository.findOne(application.getId()));
		user.setCurrentPermission(userService.getPermission(user, application));
		userRepository.save(user);
	}

	@RequestMapping(value = "accept_invitation", method = RequestMethod.POST)
	public void acceptInvitation(@RequestBody Invitation invitation, Principal principal) {
		invitation = invitationRepository.findOne(invitation.getId());
		invitation.setStatus("accepted");
		invitationRepository.save(invitation);
	}

	@RequestMapping(value = "cancel_invitation", method = RequestMethod.POST)
	public void cancelInvitation(@RequestBody Invitation invitation, Principal principal) {
		invitation = invitationRepository.findOne(invitation.getId());
		invitation.setStatus("canceled");
		invitationRepository.save(invitation);
	}
	
}
