package ru.vk.cometa.controller;

import java.security.Principal;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.AppEntity;
import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Area;
import ru.vk.cometa.model.Assembly;
import ru.vk.cometa.model.Component;
import ru.vk.cometa.model.Dependency;
import ru.vk.cometa.model.Element;
import ru.vk.cometa.model.Generator;
import ru.vk.cometa.model.Invitation;
import ru.vk.cometa.model.MajorVersion;
import ru.vk.cometa.model.Package;
import ru.vk.cometa.model.Platform;
import ru.vk.cometa.model.Stereotype;
import ru.vk.cometa.model.Structure;
import ru.vk.cometa.model.User;
import ru.vk.cometa.model.Version;
import ru.vk.cometa.service.EmailUtil;

@RestController
@RequestMapping("/save")
public class WriteController extends BaseService {

	@RequestMapping(value = "application", method = RequestMethod.POST)
	public void saveApplication(@RequestBody Application application, Principal principal) throws ManagedException {
		application.setOwnerUser(userRepository.findByLogin(principal.getName()));
		assertNotNull(application.getName(), "Name");
		validationService.unique(application).addParameter("name", application.getName())
				.addParameter("ownerUser", application.getOwnerUser()).check();
		applicationRepository.save(application);
	}

	@RequestMapping(value = "module", method = RequestMethod.POST)
	public void saveModule(@RequestBody AppModule module, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		assertNotNull(module.getName(), "Name");
		assertNotNull(module.getSysname(), "Sysname");
		module.setApplication(getApplication(principal));
		validationService.unique(module).addParameter("name", module.getName()).check();
		validationService.unique(module).addParameter("sysname", module.getSysname()).check();
		moduleService.saveModule(module);
	}

	@RequestMapping(value = "major_version", method = RequestMethod.POST)
	public void saveMajorVersion(@RequestBody MajorVersion major, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		assertNotNull(major.getModule(), "Module");
		moduleService.saveMajorVersion(major, getApplication(principal));
	}

	@RequestMapping(value = "minor_version", method = RequestMethod.POST)
	public void saveMinorVersion(@RequestBody Version version, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		assertNotNull(version.getModule(), "Module");
		assertNotNull(version.getMajorVersion(), "MajorVersion");
		moduleService.saveVersion(version, getApplication(principal));
	}

	@RequestMapping(value = "dependency", method = RequestMethod.POST)
	public void saveDependency(@RequestBody Dependency dependency, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		assertNotNull(dependency.getInfluencerVersion(), "Influencer");
		assertNotNull(dependency.getDependentVersion(), "Dependent");
		moduleService.saveDependency(dependency, getApplication(principal));
	}

	@RequestMapping(value = "stereotype", method = RequestMethod.POST)
	public void saveStereotype(@RequestBody Stereotype stereotype, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		stereotype.setApplication(getApplication(principal));
		stereotype.setVersion(getCurrentVersion(principal));
		checkApplicationNamedObject(stereotype);
		stereotype.setIsDefault((stereotype.getIsDefault() != null) && stereotype.getIsDefault());
		if (stereotype.getIsDefault()) {
			for (Stereotype s : stereotypeRepository.findByVersionAndMetatype(stereotype.getVersion(),
					stereotype.getMetatype())) {
				if (!s.getId().equals(stereotype.getId())) {
					s.setIsDefault(false);
					stereotypeRepository.save(s);
				}
			}
		}
		stereotypeRepository.save(stereotype);
	}

	@RequestMapping(value = "current_version", method = RequestMethod.POST)
	public void saveCurrentVersion(@RequestBody Version currentVersion, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		User currentUser = userRepository.findByLogin(principal.getName());
		if (currentVersion != null) {
			currentUser.setCurrentVersion(currentVersion);
			userRepository.save(currentUser);
		}
	}

	@RequestMapping(value = "platform", method = RequestMethod.POST)
	public void savePlatform(@RequestBody Platform platform, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		platform.setApplication(getApplication(principal));
		platform.setVersion(getCurrentVersion(principal));
		checkApplicationNamedObject(platform);
		platformRepository.save(platform);
	}

	@RequestMapping(value = "generator", method = RequestMethod.POST)
	public void saveGenerator(@RequestBody Generator generator, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		assertNotNull(generator.getPlatform(), "Platform");
		assertNotNull(generator.getStereotype(), "Stereotype");
		generator.setApplication(getApplication(principal));
		generator.setVersion(getCurrentVersion(principal));
		generator.setExtension(generator.getExtension() == null ? "" : generator.getExtension());
		generator.setEncoding(generator.getEncoding() == null ? "UTF-8" : generator.getEncoding());
		checkApplicationNamedObject(generator);
		generatorRepository.save(generator);
	}

	@RequestMapping(value = "component", method = RequestMethod.POST)
	public void saveComponent(@RequestBody Component component, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		assertNotNull(component.getPlatform(), "Platform");
		component.setApplication(getApplication(principal));
		component.setVersion(getCurrentVersion(principal));
		checkApplicationNamedObject(component);
		componentRepository.save(component);
	}

	@RequestMapping(value = "package", method = RequestMethod.POST)
	public void savePackage(@RequestBody Package pack, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		pack.setApplication(getApplication(principal));
		pack.setVersion(getCurrentVersion(principal));
		checkApplicationNamedObject(pack);
		packageService.savePackage(pack, getCurrentVersion(principal));
	}

	@RequestMapping(value = "element", method = RequestMethod.POST)
	public void saveElement(@RequestBody Element element, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		element.setApplication(getApplication(principal));
		element.setVersion(getCurrentVersion(principal));
		assertNotNull(element.getArea(), "Area");
		assertNotNull(element.getType(), "Type");
		assertNotNull(element.getStereotype(), "Stereotype");
		checkApplicationStereotypicalObject(element);
		elementRepository.save(element);
	}

	@RequestMapping(value = "area", method = RequestMethod.POST)
	public void saveArea(@RequestBody Area area, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		area.setApplication(getApplication(principal));
		area.setVersion(getCurrentVersion(principal));
		assertNotNull(area.getStereotype(), "Stereotype");
		checkApplicationStereotypicalObject(area);
		areaRepository.save(area);
	}

	@RequestMapping(value = "structure", method = RequestMethod.POST)
	public void saveStructure(@RequestBody Structure structure, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		structure.setApplication(getApplication(principal));
		structure.setVersion(getCurrentVersion(principal));
		checkApplicationStereotypicalObject(structure);
		assertNotNull(structure.getArea(), "Stereotype");
		modelService.saveStructure(structure);
	}

	@RequestMapping(value = "entity", method = RequestMethod.POST)
	public void saveEntity(@RequestBody AppEntity entity, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		entity.setApplication(getApplication(principal));
		entity.setVersion(getCurrentVersion(principal));
		checkApplicationStereotypicalObject(entity);
		assertNotNull(entity.getArea(), "Area");
		assertNotNull(entity.getStructure().getStereotype(), "Structure stereotype");
		assertNotNull(entity.getStructure().getAttributes(), "Attributes");
		modelService.saveEntity(entity);
	}

	@RequestMapping(value = "assembly", method = RequestMethod.POST)
	public void saveAssembly(@RequestBody Assembly assembly, Principal principal) throws ManagedException {
		checkCurrentApplicationIsNotNull(principal);
		assembly.setApplication(getApplication(principal));
		validationService.unique(assembly).addParameter("name", assembly.getName()).check();
		validationService.unique(assembly).addParameter("sysname", assembly.getSysname()).check();
		assertNotNull(assembly.getVersions(), "Versions");
		buildService.saveAssembly(assembly);
	}

	@RequestMapping(value = "invitation", method = RequestMethod.POST)
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

}
