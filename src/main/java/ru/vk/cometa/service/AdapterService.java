package ru.vk.cometa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.service.transformation.Adapter;
import ru.vk.cometa.service.transformation.CometaAdapter;
import ru.vk.cometa.service.transformation.MysqlAdapter;
import ru.vk.cometa.service.transformation.XmlAdapter;

@Service
public class AdapterService {
	@Autowired
	private CometaAdapter cometaAdapter;
	@Autowired
	private XmlAdapter xmlAdapter;
	@Autowired
	private MysqlAdapter mysqlAdapter;

	public Adapter getAdapterByClassName(String className) throws ManagedException {
		if (className.contentEquals(CometaAdapter.class.getSimpleName())) {
			return cometaAdapter;
		} 
		else if (className.contentEquals(XmlAdapter.class.getSimpleName())) {
			return xmlAdapter;
		}
		else if (className.contentEquals(MysqlAdapter.class.getSimpleName())) {
			return mysqlAdapter;
		}
		throw new ManagedException("Adapter " + className + " not found");
	}
}
