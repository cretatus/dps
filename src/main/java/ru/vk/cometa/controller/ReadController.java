package ru.vk.cometa.controller;

import java.io.File;
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
import ru.vk.cometa.model.AppEntity;
import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.ApplicationNamedObject;
import ru.vk.cometa.model.ApplicationStereotypicalObject;
import ru.vk.cometa.model.Assembly;
import ru.vk.cometa.model.Attribute;
import ru.vk.cometa.model.Build;
import ru.vk.cometa.model.BuildLog;
import ru.vk.cometa.model.Dependency;
import ru.vk.cometa.model.ElementType;
import ru.vk.cometa.model.Key;
import ru.vk.cometa.model.MajorVersion;
import ru.vk.cometa.model.Metatype;
import ru.vk.cometa.model.Resource;
import ru.vk.cometa.model.Stereotype;
import ru.vk.cometa.model.Structure;
import ru.vk.cometa.model.Version;
import ru.vk.cometa.service.BaseService;

@RestController
@RequestMapping("/read")
public class ReadController extends BaseService {
	@RequestMapping(value = "current_version", method = RequestMethod.GET)
	public Version getCurrent(Principal principal) throws ManagedException {
		return getCurrentVersion(principal);
	}

	@RequestMapping(value = "modules", method = RequestMethod.GET)
	public List<AppModule> getModules(Principal principal) throws ManagedException {
		return moduleRepository.findByApplication(
				userRepository.findByLogin(principal.getName()).getCurrentApplication());
	}

	@RequestMapping(value = "major_versions", method = RequestMethod.POST)
	public List<MajorVersion> getMajorVersions(@RequestBody AppModule module, Principal principal) throws ManagedException {
		return majorVersionRepository.findByModule(module);
	}

	@RequestMapping(value = "major_versions_by_application", method = RequestMethod.GET)
	public List<MajorVersion> getMajorVersionsByApplication(Principal principal) throws ManagedException {
		return majorVersionRepository.findByApplication(getApplication(principal));
	}

	@RequestMapping(value = "minor_versions", method = RequestMethod.POST)
	public List<Version> getMinorVersions(@RequestBody AppModule module, Principal principal) throws ManagedException {
		return versionRepository.findByModule(module);
	}

	@RequestMapping(value = "versions", method = RequestMethod.GET)
	public List<Version> getMinorVersionsByApplication(Principal principal) throws ManagedException {
		return versionRepository.findByApplication(getApplication(principal));
	}

	@RequestMapping(value = "dependencies_by_influencer_version", method = RequestMethod.GET)
	public List<Dependency> getDependensiesByInfluencer(Principal principal) throws ManagedException {
		return dependencyRepository.findByInfluencerVersion(getCurrentVersion(principal));
	}

	@RequestMapping(value = "dependencies_by_dependent_version", method = RequestMethod.GET)
	public List<Dependency> getDependensiesByDependent(Principal principal) throws ManagedException {
		return dependencyRepository.findByDependentVersion(getCurrentVersion(principal));
	}

	@RequestMapping(value = "metatypes", method = RequestMethod.GET)
	public List<Metatype> getMetatypes(Principal principal) throws ManagedException {
		return metatypeRepository.findAll();
	}
	@RequestMapping(value = "element_types", method = RequestMethod.GET)
	public List<ElementType> getElementTypes(Principal principal) throws ManagedException {
		return elementTypeRepository.findAll();
	}
	@RequestMapping(value = "stereotypes", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getStereotypesByMetaobject(Principal principal) throws ManagedException {
		return stereotypeRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "platforms", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getPlatforms(Principal principal) throws ManagedException {
		return platformRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "platforms_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getPlatformsLookup(Principal principal) throws ManagedException {
		return selectValidObjects(platformRepository, principal);
	}
	@RequestMapping(value = "entities_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getEntitiesLookup(Principal principal) throws ManagedException {
		return selectValidObjects(entityRepository, principal);
	}
	@RequestMapping(value = "structures_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getStructuresLookup(Principal principal) throws ManagedException {
		return selectValidObjects(structureRepository, principal);
	}
	@RequestMapping(value = "all_stereotypes_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getAllStereotypesLookup(Principal principal) throws ManagedException {
		return selectValidObjects(stereotypeRepository, principal);
	}
	@RequestMapping(value = "stereotypes_lookup", method = RequestMethod.POST)
	public List<ApplicationNamedObject> getStereotypesLookup(@RequestBody String metatypeCode, Principal principal) throws ManagedException {
		List<ApplicationNamedObject> result = selectValidObjects(stereotypeRepository, principal);
		List<ApplicationNamedObject> removingList = new ArrayList<ApplicationNamedObject>();
		for(ApplicationNamedObject object : result) {
			Stereotype stereotype = (Stereotype)object;
			if(!stereotype.getMetatype().getCode().equals(metatypeCode)) {
				removingList.add(object);
			}
		}
		result.removeAll(removingList);
		return result;
	}
	@RequestMapping(value = "stereotypes_lookup_by_metaobject", method = RequestMethod.POST)
	public List<ApplicationNamedObject> getStereotypesLookupByMetaobject(@RequestBody String metaobject, Principal principal) throws ManagedException {
		List<ApplicationNamedObject> result = selectValidObjects(stereotypeRepository, principal);
		List<ApplicationNamedObject> removingList = new ArrayList<ApplicationNamedObject>();
		for(ApplicationNamedObject object : result) {
			Stereotype stereotype = (Stereotype)object;
			if(!stereotype.getMetatype().getMetaobject().equals(metaobject)) {
				removingList.add(object);
			}
		}
		result.removeAll(removingList);
		return result;
	}
	@RequestMapping(value = "generators", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getGenerators(Principal principal) throws ManagedException {
		return generatorRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "components", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getComponents(Principal principal) throws ManagedException {
		return componentRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "components_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getComponentsLookup(Principal principal) throws ManagedException {
		return selectValidObjects(componentRepository, principal);
	}
	@RequestMapping(value = "packages", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getPackages(Principal principal) throws ManagedException {
		return packageRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "packages_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getPackagesLookup(Principal principal) throws ManagedException {
		return selectValidObjects(packageRepository, principal);
	}
	@RequestMapping(value = "areas", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getAreas(Principal principal) throws ManagedException {
		return areaRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "areas_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getAreasLookup(Principal principal) throws ManagedException {
		return selectValidObjects(areaRepository, principal);
	}
	@RequestMapping(value = "elements", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getElements(Principal principal) throws ManagedException {
		return elementRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "elements_lookup", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getElementsLookup(Principal principal) throws ManagedException {
		return selectValidObjects(elementRepository, principal);
	}
	@RequestMapping(value = "structures", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getStructures(Principal principal) throws ManagedException {
		return structureRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "entities", method = RequestMethod.GET)
	public List<ApplicationNamedObject> getEntities(Principal principal) throws ManagedException {
		return entityRepository.findByVersion(getCurrentVersion(principal));
	}
	@RequestMapping(value = "attributes", method = RequestMethod.POST)
	public List<Attribute> getAttributes(@RequestBody Structure structure, Principal principal) throws ManagedException {
		return attributeRepository.findByStructure(structure);
	}
	@RequestMapping(value = "key_atributes", method = RequestMethod.POST)
	public List<Attribute> getKeyAttributes(@RequestBody AppEntity entity, Principal principal) throws ManagedException {
		return entityKeyRepository.findAttributesByEntity(entity);
	}
	@RequestMapping(value = "keys", method = RequestMethod.POST)
	public List<Key> getKeys(@RequestBody AppEntity entity, Principal principal) throws ManagedException {
		return entityKeyRepository.findByEntity(entity);
	}
	@RequestMapping(value = "assemblies", method = RequestMethod.GET)
	public List<Assembly> getAssemblies(Principal principal) throws ManagedException {
		return assemblyRepository.findByApplication(getApplication(principal));
	}
	@RequestMapping(value = "builds", method = RequestMethod.GET)
	public List<Build> getBuilds(Principal principal) throws ManagedException {
		List<Build> result = new ArrayList<Build>();
		for(Build build : buildRepository.findByApplication(getApplication(principal))) {
			if(new File(build.getPath()).exists()) {
				result.add(build);
			}
		}
		return result;
	}
	@RequestMapping(value = "dependency_influencers", method = RequestMethod.GET)
	public Map<Integer, List<Dependency>> getDependencyInfluencer(Principal principal) throws ManagedException {
		Map<Integer, List<Dependency>> result = new HashMap<Integer, List<Dependency>>();
		for(Version version : versionRepository.findByApplication(getApplication(principal))) {
			result.put(version.getId(), dependencyRepository.findByDependentVersion(version));
		}
		return result;
	}
	@RequestMapping(value = "resource", method = RequestMethod.POST)
	public Resource getResource(@RequestBody Integer resourceId, Principal principal) throws ManagedException {
		return resourceRepository.findOne(resourceId);
	}
	@RequestMapping(value = "objects_by_stereotype", method = RequestMethod.POST)
	public List<ApplicationStereotypicalObject>  selectObjectsByComponent(@RequestBody Stereotype stereotype, Principal principal) throws ManagedException {
		return buildService.selectObjectsByComponent(getApplication(principal), stereotype);
	}
	@RequestMapping(value = "build_logs", method = RequestMethod.POST)
	public List<BuildLog>  getBuildlogs(@RequestBody Build build, Principal principal) throws ManagedException {
		return buildLogRepository.findByBuild(build);
	}
}
