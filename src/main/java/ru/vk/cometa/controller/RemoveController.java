package ru.vk.cometa.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.AppEntity;
import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.Area;
import ru.vk.cometa.model.Assembly;
import ru.vk.cometa.model.Attribute;
import ru.vk.cometa.model.Component;
import ru.vk.cometa.model.Dependency;
import ru.vk.cometa.model.Element;
import ru.vk.cometa.model.Generator;
import ru.vk.cometa.model.MajorVersion;
import ru.vk.cometa.model.Package;
import ru.vk.cometa.model.Platform;
import ru.vk.cometa.model.Stereotype;
import ru.vk.cometa.model.Structure;
import ru.vk.cometa.model.Transformation;
import ru.vk.cometa.model.Version;
import ru.vk.cometa.service.BaseService;

@RestController
@RequestMapping("/remove")
public class RemoveController extends BaseService{

	@RequestMapping(value = "module", method = RequestMethod.POST)
	public void removeModule(@RequestBody AppModule module, Principal principal) throws ManagedException {
		moduleService.removeModule(module);
	}

	@RequestMapping(value = "major_version", method = RequestMethod.POST)
	public void removeMajorVersion(@RequestBody MajorVersion majorVersion, Principal principal) throws ManagedException {
		moduleService.removeMajorVersion(majorVersion);
	}

	@RequestMapping(value = "minor_version", method = RequestMethod.POST)
	public void removeMinorVersion(@RequestBody Version minorVersion, Principal principal) throws ManagedException {
		moduleService.removeMinorVersion(minorVersion);
	}

	@RequestMapping(value = "dependency", method = RequestMethod.POST)
	public void removeDependency(@RequestBody Dependency dependency, Principal principal) throws ManagedException {
		dependencyRepository.delete(dependency);
	}

	@RequestMapping(value = "stereotype", method = RequestMethod.POST)
	public void removeStereotype(@RequestBody Stereotype stereotype, Principal principal) throws ManagedException {
		if(stereotype.getIsDefault()) {
			throw new ManagedException("You cannot remove the default stereotype");
		}
		stereotypeRepository.delete(stereotype);
	}

	@RequestMapping(value = "platform", method = RequestMethod.POST)
	public void removePlatform(@RequestBody Platform platform, Principal principal) throws ManagedException {
		platformRepository.delete(platform);
	}
	@RequestMapping(value = "generator", method = RequestMethod.POST)
	public void removeGenerator(@RequestBody Generator generator, Principal principal) throws ManagedException {
		generatorRepository.delete(generator);
	}
	@RequestMapping(value = "component", method = RequestMethod.POST)
	public void removeComponent(@RequestBody Component component, Principal principal) throws ManagedException {
		componentRepository.delete(component);
	}
	@RequestMapping(value = "package", method = RequestMethod.POST)
	public void removePackage(@RequestBody Package pack, Principal principal) throws ManagedException {
		packageService.removePackage(pack, getCurrentVersion(principal));
	}
	@RequestMapping(value = "area", method = RequestMethod.POST)
	public void removeArea(@RequestBody Area area, Principal principal) throws ManagedException {
		areaRepository.delete(area);
	}
	@RequestMapping(value = "element", method = RequestMethod.POST)
	public void removeElement(@RequestBody Element element, Principal principal) throws ManagedException {
		elementRepository.delete(element);
	}
	@RequestMapping(value = "structure", method = RequestMethod.POST)
	public void removeStructure(@RequestBody Structure structure, Principal principal) throws ManagedException {
		modelService.removeStructure(structure);
	}
	@RequestMapping(value = "attribute", method = RequestMethod.POST)
	public void removeAttribute(@RequestBody Attribute attribute, Principal principal) throws ManagedException {
		attributeRepository.delete(attribute);
	}
	@RequestMapping(value = "entity", method = RequestMethod.POST)
	public void removeEntity(@RequestBody AppEntity entity, Principal principal) throws ManagedException {
		modelService.removeEntity(entity);
	}
	@RequestMapping(value = "assembly", method = RequestMethod.POST)
	public void removeAssembly(@RequestBody Assembly assembly, Principal principal) throws ManagedException {
		buildService.removeAssembly(assembly);
	}
	@RequestMapping(value = "transformation", method = RequestMethod.POST)
	public void removeTransformation(@RequestBody Transformation transformation, Principal principal) throws ManagedException {
		transformationRepository.delete(transformation);
	}
}
