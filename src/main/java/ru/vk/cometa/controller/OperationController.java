package ru.vk.cometa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.ApplicationStereotypicalObject;
import ru.vk.cometa.model.Assembly;
import ru.vk.cometa.model.Build;
import ru.vk.cometa.model.BuildLog;
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

	@RequestMapping(value = "download_build_files", method = RequestMethod.POST)
	public void downloadBuildFiles(@RequestBody Build build, HttpServletResponse response, Principal principal) throws ManagedException {
		try {
			Build b = buildRepository.findOne(build.getId());
			response.setContentType("application/zip");
			zipUtil.zipDirectory(response.getOutputStream(), new File(b.getPath()), b.getLabel() + ".zip");
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ManagedException(e.getMessage());
		}
	}

	@RequestMapping(value = "download_build_log_file", method = RequestMethod.POST)
	public void downloadBuildLogFile(@RequestBody BuildLog buildLog, HttpServletResponse response, Principal principal) throws ManagedException {
		try {
			if(buildLog.getIsDirectory()) {
				Build b = buildRepository.findOne(buildLog.getBuild().getId());
				response.setContentType("application/zip");
				zipUtil.zipDirectory(response.getOutputStream(), new File(b.getPath()), b.getLabel() + ".zip");
				response.flushBuffer();
			}
			else {
				response.setContentType("application/zip");
				IOUtils.copy(new FileInputStream(new File(buildLog.getPath())), response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ManagedException(e.getMessage());
		}
	}
}
