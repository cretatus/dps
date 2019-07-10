package ru.vk.cometa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vk.cometa.controller.BaseService;
import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.Dependency;
import ru.vk.cometa.model.MajorVersion;
import ru.vk.cometa.model.Stereotype;
import ru.vk.cometa.model.Subtype;
import ru.vk.cometa.model.Version;

@Service
@Transactional(rollbackFor=Exception.class)
public class ModuleService extends BaseService {
	public void saveModule(AppModule module) throws ManagedException {
		boolean isNew = module.getId() == null;
		moduleRepository.save(module);
		if (isNew) {
			MajorVersion major1 = new MajorVersion();
			major1.setModule(module);
			major1.setDescription("Default version");
			saveMajorVersion(major1, module.getApplication());
		}
	}
	
	public void saveMajorVersion(MajorVersion majorVersion, Application application) throws ManagedException {
		AppModule module = moduleRepository.findOne(majorVersion.getModule().getId());
		boolean isNew = majorVersion.getId() == null;
		if( isNew ) {
			int number = 0;
			for(MajorVersion mv : majorVersionRepository.findByModule(module)) {
				mv.setStatus(MajorVersion.STATUS_CLOSED);
				majorVersionRepository.save(mv);
				number++;
			}
			majorVersion.setNumber(number);
			majorVersion.setStatus(MajorVersion.STATUS_OPENED);
		}
		else {
			MajorVersion majorVersionOld = majorVersionRepository.findOne(majorVersion.getId());
			majorVersion.setNumber(majorVersionOld.getNumber());
			majorVersion.setStatus(majorVersionOld.getStatus());
		}
		majorVersion.setApplication(application);
		majorVersionRepository.save(majorVersion);
		if( isNew ) {
			Version minorVersion = new Version();
			minorVersion.setModule(module);
			minorVersion.setMajorVersion(majorVersion);
			saveVersion(minorVersion, application);
		}
	}
	public void saveVersion(Version version, Application application) throws ManagedException {
		AppModule module = moduleRepository.findOne(version.getModule().getId());
		MajorVersion majorVersion = majorVersionRepository.findOne(version.getMajorVersion().getId());
		boolean isNew = version.getId() == null;
		if( version.getId() == null ) {
			int number = 0;
			for(Version minor : versionRepository.findByMajorVersion(majorVersion)) {
				minor.setStatus(MajorVersion.STATUS_CLOSED);
				versionRepository.save(minor);
				number++;
			}
			version.setNumber(number);
			version.setStatus(Version.STATUS_OPENED);
		}
		else {
			Version minorVersionOld = versionRepository.findOne(version.getId());
			version.setNumber(minorVersionOld.getNumber());
			version.setStatus(minorVersionOld.getStatus());
		}
		version.setApplication(application);
		version.setMajorVersion(majorVersion);
		version.setModule(module);
		version.setName(majorVersion.getNumber().toString() + "." + version.getNumber());
		versionRepository.save(version);
		if(isNew) {
			createDefaultStereotypes(version, application);
		}
	}
	
	public void removeMajorVersion(MajorVersion majorVersion) throws ManagedException {
		if(majorVersion == null) return;
		majorVersion = majorVersionRepository.findOne(majorVersion.getId());
		AppModule module = moduleRepository.findOne(majorVersion.getModule().getId());
		List<MajorVersion> majorVersions = majorVersionRepository.findByModule(module);
		if(majorVersion.getNumber() == majorVersions.size() - 1) {
			int size = versionRepository.findByMajorVersion(majorVersion).size();
			for(int i = 0; i < size; i++) {
				removeMinorVersion(findMaxMinorVersion(majorVersion));
			}
			majorVersionRepository.delete(majorVersion);
		}
		else {
			throw new ManagedException("You can remove only last major version");
		}
	}
	
	public Version findMaxMinorVersion(MajorVersion majorVersion) {
		List<Version> minorVersions = versionRepository.findByMajorVersion(majorVersion);
		if(minorVersions.size() == 0) return null;
		return versionRepository.findByMajorVersionAndNumber(majorVersion, minorVersions.size() - 1);
	}
	
	public MajorVersion findMaxMajorVersion(AppModule module) {
		List<MajorVersion> majorVersions = majorVersionRepository.findByModule(module);
		if(majorVersions.size() == 0) return null;
		return majorVersionRepository.findByModuleAndNumber(module, majorVersions.size());
	}
	
	public void removeMinorVersion(Version minorVersion) throws ManagedException {
		MajorVersion majorVersion = majorVersionRepository.findOne(minorVersion.getMajorVersion().getId());
		List<Version> minorVersions = versionRepository.findByMajorVersion(majorVersion);
		if(minorVersion.getNumber() == minorVersions.size() - 1) {

			versionRepository.delete(minorVersion);
		}
		else {
			throw new ManagedException("You can remove only last minor version");
		}
	}
	
	public void removeModule(AppModule module) throws ManagedException {
		int size = majorVersionRepository.findByModule(module).size();
		for(int i = 0; i < size; i++) {
			removeMajorVersion(findMaxMajorVersion(module));
		}
		moduleRepository.delete(module);
	}
	
	public void saveDependency(Dependency dependency, Application application) throws ManagedException{
		dependency.setApplication(application);
		if(dependency.getDependentVersion().getModule().getId().equals(dependency.getInfluencerVersion().getModule().getId())) {
			throw new ManagedException("The module cannot depend on itself");
		}
		for(Dependency d : dependencyRepository.findByDependentVersion(dependency.getDependentVersion())) {
			if(d.getInfluencerVersion().getModule().getId().equals(dependency.getInfluencerVersion().getModule().getId())) {
				throw new ManagedException("The module can only have one dependency on another module");
			}
				
		}
		dependencyRepository.save(dependency);
	}
	
	public void createDefaultStereotypes(Version version, Application application) {
		for(Subtype subtype : subtypeRepository.findAll()) {
			Stereotype stereotype = new Stereotype();
			stereotype.setApplication(application);
			stereotype.setDescription(null);
			stereotype.setIsDefault(true);
			stereotype.setSubtype(subtype);
			stereotype.setName("Default " + subtype.getCode());
			stereotype.setSysname("default_" + subtype.getCode());
			stereotype.setVersion(version);
			stereotypeRepository.save(stereotype);
		}
	}
}
