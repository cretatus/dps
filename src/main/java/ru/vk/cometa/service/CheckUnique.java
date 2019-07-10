package ru.vk.cometa.service;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.ApplicationNamedObject;
import ru.vk.cometa.model.ApplicationObject;
import ru.vk.cometa.model.Identified;

public class CheckUnique {
	EntityManager entityManager;
	Identified object;
	Map<String, Object> params = new HashMap<String, Object>();
	Map<String, String> messages = new HashMap<String, String>();
	
	CheckUnique(EntityManager entityManager, Identified object){
		this.entityManager = entityManager;
		this.object = object;
		if(object instanceof ApplicationObject) {
			addParameter("application", ((ApplicationObject)object).getApplication(), ((ApplicationObject)object).getApplication().getName());
		}
		if(object instanceof ApplicationNamedObject) {
			addParameter("version", ((ApplicationNamedObject)object).getVersion(), ((ApplicationNamedObject)object).getVersion().getName());
		}
	}
	public CheckUnique addParameter(String name, Object value) {
		return addParameter(name, value, value.toString());
	}
	
	public CheckUnique addParameter(String name, Object value, String message) {
		params.put(name, value);
		messages.put(name, message);
		return this;
	}
	public void check() throws ManagedException{
		String queryText = "select a from " + object.getClass().getSimpleName() + " a where (a.id <> :id or :id is null)";
		for(String key : params.keySet()) {
			queryText += " and a." + key + " = :" + key;
		}
		Query query = entityManager.createQuery(queryText);
		query.setParameter("id", object.getId());
		for(String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		if(query.getResultList().size() > 0) {
			String message = "";
			for(String key : messages.keySet()) {
				message += key + " = '" + format(messages.get(key)) + "' ";
			}
			throw new ManagedException("The " + object.getClass().getSimpleName() + " with parameters " + message + " already exists");
		}
	}
	private String format(Object object) {
		if(object instanceof Identified) {
			return object.getClass().getSimpleName() + "[id=" + ((Identified)object).getId() + "]";
		}
		else {
			return object.toString();
		}
	}
}
