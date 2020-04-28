package ru.vk.cometa.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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
	private String getPermission(User user, Application app) throws ManagedException {
		Application application = applicationRepository.findOne(app.getId());
		if(application.getOwnerUser().getId().equals(user.getId())) {
			return "owner";
		}
		Invitation invitation = invitationRepository.findByApplicationAndEmail(application, user.getEmail());
		if(invitation == null) {
			throw new ManagedException("The user " + user.getName() + " does not have rights on the application " + application.getName());
		}
		return invitation.getPermission();
	}

	@RequestMapping(value = "current_application", method = RequestMethod.GET)
	public Application getCurrentApplication(Principal principal) throws ManagedException {
		return userRepository.findByLogin(principal.getName()).getCurrentApplication();
	}


	@RequestMapping(value = "save_application", method = RequestMethod.POST)
	public void saveApplication(@RequestBody Application application, Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		if(application.getId() != null) {
			if(!application.getOwnerUser().getId().equals(user.getId())) {
				throw new ManagedException("Access denied! Only the owner can change the application!");
			}
		}
		application.setOwnerUser(user);
		assertNotNull(application.getName(), "Name");
		validationService.unique(application).addParameter("name", application.getName())
				.addParameter("ownerUser", application.getOwnerUser()).check();
		applicationRepository.save(application);
	}

	@RequestMapping(value = "remove_application", method = RequestMethod.POST)
	public void removeApplication(@RequestBody Application application, Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		if(application.getId() != null) {
			if(!application.getOwnerUser().getId().equals(user.getId())) {
				throw new ManagedException("Access denied! Only the owner can remove the application!");
			}
		}
		applicationRepository.delete(application);
	}

	@RequestMapping(value = "save_invitation", method = RequestMethod.POST)
	public void saveInvitation(@RequestBody Invitation invitation, Principal principal) throws ManagedException {
		assertNotNull(invitation.getApplication(), "Application");
		assertNotNull(invitation.getEmail(), "Email");
		assertNotNull(invitation.getPermission(), "Permission");
		User user = userRepository.findByLogin(principal.getName());
		Application application = applicationRepository.findOne(invitation.getApplication().getId());
		String permission = getPermission(user, application);
		if(!(permission.equals("admin") || permission.equals("owner"))) {
			throw new ManagedException("Access denied! Only the owner or the admin can send the invitation!");
		}

		invitation.setApplication(application);
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
		User user = userRepository.findByLogin(principal.getName());
		Application application = applicationRepository.findOne(invitation.getApplication().getId());
		String permission = getPermission(user, application);
		if(!permission.equals("owner") || !invitation.getSenderUser().getId().equals(user.getId())) {
			throw new ManagedException("Access denied! Only the owner or the invite's sender can remove the invitation!");
		}
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
	public void selectApplication(@RequestBody Application application, Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		user.setCurrentApplication(applicationRepository.findOne(application.getId()));
		user.setCurrentPermission(getPermission(user, application));
		userRepository.save(user);
	}

	@RequestMapping(value = "accept_invitation", method = RequestMethod.POST)
	public void acceptInvitation(@RequestBody Invitation invitation, Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		if(!user.getEmail().equals(invitation.getEmail())) {
			throw new ManagedException("Access denied!");
		}
		invitation = invitationRepository.findOne(invitation.getId());
		invitation.setStatus("accepted");
		invitationRepository.save(invitation);
	}

	@RequestMapping(value = "cancel_invitation", method = RequestMethod.POST)
	public void cancelInvitation(@RequestBody Invitation invitation, Principal principal) throws ManagedException {
		User user = userRepository.findByLogin(principal.getName());
		if(!user.getEmail().equals(invitation.getEmail())) {
			throw new ManagedException("Access denied!");
		}
		invitation = invitationRepository.findOne(invitation.getId());
		invitation.setStatus("canceled");
		invitationRepository.save(invitation);
	}
	
	@RequestMapping(value = "transformation_config", method = RequestMethod.GET)
	public Map<String, Object>  getTransformationConfig(Principal principal) throws ManagedException {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, List<String>> adapters = new HashMap<String, List<String>>();
		List<String> protocols = Arrays.asList(new String[] {"http", "jdbc", "co-meta"});
		Map<String, List<Map<String, String>>> parameters = new HashMap<String, List<Map<String, String>>>();
		adapters.put("http", Arrays.asList(new String[] {
				ru.vk.cometa.service.transformation.XmlAdapter.class.getSimpleName()}));
		adapters.put("jdbc", Arrays.asList(new String[] {
				ru.vk.cometa.service.transformation.MysqlAdapter.class.getSimpleName()}));
		adapters.put("co-meta", Arrays.asList(new String[] {
				ru.vk.cometa.service.transformation.CometaAdapter.class.getSimpleName()}));
		
		result.put("protocols", protocols);
		result.put("adapters", adapters);
		result.put("parameters", parameters);
		for(String protocol : adapters.keySet()) {
			for(String adapter : adapters.get(protocol)) {
				parameters.put(adapter, adapterService.getAdapterByClassName(adapter).createParamsList());
			}
		}
		return result;
	}
}
