package ru.vk.cometa.controller;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.Generator;
import ru.vk.cometa.model.Resource;
import ru.vk.cometa.service.BaseService;

@RestController
@RequestMapping("/upload")
public class UploadController extends BaseService {

	protected Resource findOrCreateResource(Integer resourceId) {
		if(resourceId == null) {
			return new Resource();
		}
		else {
			return resourceRepository.findOne(resourceId);
		}
	}
	
	@RequestMapping(value = "file_upload", method = RequestMethod.POST)
	public Integer addParameterStructure (
			@RequestPart("file") MultipartFile file, 
			@RequestPart("parameters") String parameters, 
			Principal principal) throws ManagedException {
		try {
			Map<String, Object> params = readParams(parameters);
			ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
			Resource resource = findOrCreateResource(readId(params, "resourceId"));
			resource.setApplication(getApplication(principal));
			resource.setFormat(readString(params, "templateFormat"));
			resource.setEncoding(readString(params, "templateEncoding"));
			resource.setOwner(Generator.class.getSimpleName());;
			resource.setText(IOUtils.toString(stream, readString(params, "templateEncoding")));
			resource = resourceRepository.save(resource);
			return resource.getId();
		}
		catch(Exception e){
			throw new ManagedException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "text_upload", method = RequestMethod.POST)
	public Integer saveModule(
			@RequestPart("text") String text, 
			@RequestPart("parameters") String parameters, 
			Principal principal) throws ManagedException {
		try {
			Map<String, Object> params = readParams(parameters);
			Resource resource = findOrCreateResource(readId(params, "resourceId"));
			resource.setApplication(getApplication(principal));
			resource.setFormat(readString(params, "templateFormat"));
			resource.setEncoding("UTF-8");
			resource.setOwner(Generator.class.getSimpleName());;
			resource.setText(text);
			resource = resourceRepository.save(resource);
			return resource.getId();
		}
		catch(Exception e){
			throw new ManagedException(e.getMessage());
		}
	}
}
