package ru.vk.cometa.model;

public interface Detail {
	public Integer getId();
	public void copyFrom(Detail detail);
	public void setContainer(Container container);
	public void setVersion(Version currentVersion);
	public void setApplication(Application application);
}
