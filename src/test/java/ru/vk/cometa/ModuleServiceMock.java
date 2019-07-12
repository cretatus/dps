package ru.vk.cometa;

import ru.vk.cometa.repositories.ModuleRepository;
import ru.vk.cometa.repositories.ApplicationRepository;
import ru.vk.cometa.repositories.DependencyRepository;
import ru.vk.cometa.repositories.MajorVersionRepository;
import ru.vk.cometa.repositories.PlatformRepository;
import ru.vk.cometa.repositories.StereotypeRepository;
import ru.vk.cometa.repositories.MetatypeRepository;
import ru.vk.cometa.repositories.UserRepository;
import ru.vk.cometa.repositories.VersionRepository;
import ru.vk.cometa.service.ModuleService;

public class ModuleServiceMock extends ModuleService {
	public ModuleServiceMock(UserRepository userRepository, ApplicationRepository applicationRepository,
			ModuleRepository moduleRepository, MajorVersionRepository majorVersionRepository,
			VersionRepository minorVersionRepository, DependencyRepository dependencyRepository,
			StereotypeRepository stereotypeRepository, MetatypeRepository metatypeRepository,
			PlatformRepository platformRepository) {

		this.userRepository = userRepository;
		this.applicationRepository = applicationRepository;
		this.moduleRepository = moduleRepository;
		this.majorVersionRepository = majorVersionRepository;
		this.versionRepository = minorVersionRepository;
		this.dependencyRepository = dependencyRepository;
		this.stereotypeRepository = stereotypeRepository;
		this.metatypeRepository = metatypeRepository;
		this.platformRepository = platformRepository;
	}
}
