package ru.vk.cometa.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.ApplicationNamedObject;
import ru.vk.cometa.model.ApplicationStereotypicalObject;
import ru.vk.cometa.model.Dependency;
import ru.vk.cometa.model.Version;
import ru.vk.cometa.repositories.ModuleRepository;
import ru.vk.cometa.repositories.ApplicationRepository;
import ru.vk.cometa.repositories.AreaRepository;
import ru.vk.cometa.repositories.AssemblyRepository;
import ru.vk.cometa.repositories.AttributeRepository;
import ru.vk.cometa.repositories.BuildLogRepository;
import ru.vk.cometa.repositories.BuildRepository;
import ru.vk.cometa.repositories.ComponentRepository;
import ru.vk.cometa.repositories.DependencyRepository;
import ru.vk.cometa.repositories.ElementRepository;
import ru.vk.cometa.repositories.ElementTypeRepository;
import ru.vk.cometa.repositories.KeyRepository;
import ru.vk.cometa.repositories.EntityRepository;
import ru.vk.cometa.repositories.GeneratorRepository;
import ru.vk.cometa.repositories.InvitationRepository;
import ru.vk.cometa.repositories.MajorVersionRepository;
import ru.vk.cometa.repositories.PackageRepository;
import ru.vk.cometa.repositories.PlatformRepository;
import ru.vk.cometa.repositories.ResourceRepository;
import ru.vk.cometa.repositories.StereotypeRepository;
import ru.vk.cometa.repositories.StructureRepository;
import ru.vk.cometa.repositories.MetatypeRepository;
import ru.vk.cometa.repositories.UserRepository;
import ru.vk.cometa.repositories.VersionRepository;
import ru.vk.cometa.repositories.VersionedObjectRepository;
import ru.vk.cometa.service.BuildService;
import ru.vk.cometa.service.ConfigService;
import ru.vk.cometa.service.EmailUtil;
import ru.vk.cometa.service.ModelService;
import ru.vk.cometa.service.ModuleService;
import ru.vk.cometa.service.PackageService;
import ru.vk.cometa.service.ValidationService;
import ru.vk.cometa.service.ZipUtil;

public class BaseService {
	@Autowired
	protected ModuleService moduleService;
	@Autowired
	protected PackageService packageService;
	@Autowired
	protected ModelService modelService;
	@Autowired
	protected ValidationService validationService;
	@Autowired
	protected BuildService buildService;
	@Autowired
	protected ConfigService config;
	@Autowired
	protected ZipUtil zipUtil;
	@Autowired
	protected EmailUtil emailUtil;

	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected ApplicationRepository applicationRepository;
	@Autowired
	protected ModuleRepository moduleRepository;
	@Autowired
	protected MajorVersionRepository majorVersionRepository;
	@Autowired
	protected VersionRepository versionRepository;
	@Autowired
	protected DependencyRepository dependencyRepository;
	@Autowired
	protected StereotypeRepository stereotypeRepository;
	@Autowired
	protected PlatformRepository platformRepository;
	@Autowired
	protected GeneratorRepository generatorRepository;
	@Autowired
	protected ComponentRepository componentRepository;
	@Autowired
	protected PackageRepository packageRepository;
	@Autowired
	protected ElementTypeRepository elementTypeRepository;
	@Autowired
	protected AreaRepository areaRepository;
	@Autowired
	protected StructureRepository structureRepository;
	@Autowired
	protected EntityRepository entityRepository;
	@Autowired
	protected AttributeRepository attributeRepository;
	@Autowired
	protected ElementRepository elementRepository;
	@Autowired
	protected KeyRepository entityKeyRepository;
	@Autowired
	protected MetatypeRepository metatypeRepository;
	@Autowired
	protected AssemblyRepository assemblyRepository;
	@Autowired
	protected BuildRepository buildRepository;
	@Autowired
	protected ResourceRepository resourceRepository;
	@Autowired
	protected BuildLogRepository buildLogRepository;
	@Autowired
	protected InvitationRepository invitationRepository;

	public void checkCurrentApplicationIsNotNull(Principal principal) throws ManagedException {
		if (userRepository.findByLogin(principal.getName()).getCurrentApplication() == null) {
			throw new ManagedException("The current application is not selected");
		}
	}

	public void assertNotNull(Object object, String objectName) throws ManagedException {
		if (object == null) {
			throw new ManagedException("The " + objectName + " cannot be null");
		}
	}

	public Application getApplication(Principal principal) {
		return userRepository.findByLogin(principal.getName()).getCurrentApplication();
	}

	public Version getCurrentVersion(Principal principal) {
		return userRepository.findByLogin(principal.getName()).getCurrentVersion();
	}

	public List<ApplicationNamedObject> selectValidObjects(VersionedObjectRepository repository, Principal principal){
		Version currentVersion = getCurrentVersion(principal);
		if(currentVersion == null) {
			return new ArrayList<ApplicationNamedObject>();
		}
		List<ApplicationNamedObject> result = repository.findByVersion(currentVersion);
		for(Dependency dependency : dependencyRepository.findByDependentVersion(currentVersion)) {
			List<ApplicationNamedObject> list = repository.findByVersion(dependency.getInfluencerVersion());
			result.addAll(list);
		}
		return result;
	}
	protected void checkApplicationNamedObject(ApplicationNamedObject object) throws ManagedException {
		assertNotNull(object.getName(), "Name");
		assertNotNull(object.getSysname(), "Sysname");
		validationService.unique(object).addParameter("name", object.getName()).check();
		validationService.unique(object).addParameter("sysname", object.getSysname()).check();
		if (!object.getSysname().matches("[a-zA-Z0-9\\_]+")) {
			throw new ManagedException("The sysname is not correct");
		}

	}

	protected void checkApplicationStereotypicalObject(ApplicationStereotypicalObject object) throws ManagedException {
		checkApplicationNamedObject(object);
		assertNotNull(object.getStereotype(), "Stereotype");
	}
	protected Map<String, Object> readParams(String parameters) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().readValue(parameters, new TypeReference<HashMap<String, String>>(){});
	}
	
	protected Integer readId(Map<String, Object> params, String key) {
		if(params.containsKey(key)) {
			return Integer.parseInt(params.get(key).toString());
		}
		return null;
	}
	
	protected String readString(Map<String, Object> params, String key) {
		return readString(params, key, "");
	}
	protected String readString(Map<String, Object> params, String key, String def) {
		if(params.containsKey(key)) {
			return params.get(key).toString();
		}
		return def;
	}
	

}
