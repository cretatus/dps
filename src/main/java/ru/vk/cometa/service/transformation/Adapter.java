package ru.vk.cometa.service.transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Adapter {
	
	protected Map<String, String> createParameter(String name, String regex){
		Map<String, String> result = new HashMap<String, String>();
		result.put("name", name);
		result.put("regex", regex);
		result.put("value", "");
		return result;
	}
	
	protected abstract void fillParameters(List<Map<String, String>> parameters);

	public List<Map<String, String>> createParamsList(){
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		fillParameters(result);
		return result;
	}
}
