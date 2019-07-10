package ru.vk.cometa.repositories;

import java.util.List;

import ru.vk.cometa.model.ApplicationNamedObject;
import ru.vk.cometa.model.Version;

public interface VersionedObjectRepository {
	List<ApplicationNamedObject> findByVersion(Version version);
}