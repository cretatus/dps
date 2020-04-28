package ru.vk.cometa.service.transformation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class XmlAdapter extends Adapter {

	@Override
	protected void fillParameters(List<Map<String, String>> parameters) {
		parameters.add(createParameter("url", ""));
		parameters.add(createParameter("username", ""));
		parameters.add(createParameter("password", ""));	
	}


}
