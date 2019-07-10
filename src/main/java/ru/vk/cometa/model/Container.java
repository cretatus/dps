package ru.vk.cometa.model;

import java.util.List;

public interface Container {
	List<Detail> getDetails();
	void setDetails(List<Detail> details);
	Version getVersion();
	Application getApplication();
}
