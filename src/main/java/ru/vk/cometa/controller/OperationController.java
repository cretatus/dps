package ru.vk.cometa.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.ApplicationStereotypicalObject;
import ru.vk.cometa.model.Assembly;
import ru.vk.cometa.model.Component;
import ru.vk.cometa.model.User;

@RestController
@RequestMapping("/operation")
public class OperationController extends BaseService {

	@RequestMapping(value = "select_application", method = RequestMethod.POST)
	public void selectApplication(@RequestBody Application application, Principal principal) {
		User user = userRepository.findByLogin(principal.getName());
		user.setCurrentApplication(applicationRepository.findOne(application.getId()));
		userRepository.save(user);
	}

	@RequestMapping(value = "exit_application", method = RequestMethod.GET)
	public void exitApplication(Principal principal) {
		User user = userRepository.findByLogin(principal.getName());
		user.setCurrentApplication(null);
		userRepository.save(user);
	}

	@RequestMapping(value = "build_assembly", method = RequestMethod.POST)
	public void buildAssembly(@RequestBody Assembly assembly, Principal principal) throws ManagedException {
		buildService.buildAssembly(assembly);
	}

	@RequestMapping(value = "generate_template", method = RequestMethod.POST)
	public Map<String, Object> generateTemplate(@RequestBody Map<String, Object> params, Principal principal)
			throws ManagedException {
		Map<String, Object> result = new HashMap<String, Object>();
		Component component = componentRepository.findOne(readId(params, "componentId"));
		ApplicationStereotypicalObject object = buildService.selectObjectsByComponentAndId(component,
				readId(params, "objectId"));
		try {
			result.put("resultText", buildService.processTemplate("debug", readString(params, "templateText"), object));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ManagedException(e.getMessage());
		}
	}
}
