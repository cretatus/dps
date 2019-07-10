package ru.vk.cometa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vk.cometa.controller.BaseService;
import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.Package;
import ru.vk.cometa.model.Version;

@Service
@Transactional(rollbackFor=Exception.class)
public class PackageService extends BaseService{
	public void savePackage(Package pack, Version version) throws ManagedException{
		pack.setApplication(version.getApplication());
		pack.setVersion(version);
		packageRepository.save(pack);
	}
	
	public void removePackage(Package pack, Version version) throws ManagedException{
		removePackageTree(pack, version);
	}

	public void removePackageTree(Package pack, Version version) throws ManagedException{
		for(Package child : packageRepository.findByVersionAndParent(version, pack)) {
			removePackageTree(child, version);
		}
		packageRepository.delete(pack);
	}
}
