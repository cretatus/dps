package ru.vk.cometa.service.transformation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MysqlAdapter extends Adapter{


	@Override
	protected void fillParameters(List<Map<String, String>> parameters) {
		parameters.add(createParameter("url", "\\.+"));
		parameters.add(createParameter("username", "\\w+"));
		parameters.add(createParameter("password", "\\.+"));
	}

}
