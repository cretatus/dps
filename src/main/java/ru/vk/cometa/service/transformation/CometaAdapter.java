package ru.vk.cometa.service.transformation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CometaAdapter extends Adapter{

	@Override
	protected void fillParameters(List<Map<String, String>> parameters) {
		parameters.add(createParameter("Application sysname", "[a-zA-Z][\\w]*"));
		parameters.add(createParameter("Module sysname", "[a-zA-Z][\\w]*"));
		parameters.add(createParameter("Version", "[0-9]+\\.[0-9]+"));
	}

}
