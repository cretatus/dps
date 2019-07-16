package ru.vk.cometa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.AppEntity;
import ru.vk.cometa.model.Attribute;
import ru.vk.cometa.model.Container;
import ru.vk.cometa.model.Detail;
import ru.vk.cometa.model.Element;
import ru.vk.cometa.model.Key;
import ru.vk.cometa.model.Stereotype;
import ru.vk.cometa.model.Structure;
import ru.vk.cometa.model.Metatype;

@Service
@Transactional(rollbackFor=Exception.class)
public class ModelService extends BaseService{
	private void save(Detail object) throws ManagedException{
		if(object instanceof Attribute) {
			Attribute attribute = (Attribute)object;
			Element element = attribute.getElement();
			if(attribute.getElement().getId() == null) {
				Element e = elementRepository.findByVersionAndAreaAndSysname(attribute.getVersion(), attribute.getStructure().getArea(), attribute.getSysname());
				if(e != null) {
					attribute.setElement(e);
				}
				else {
					element.copyFrom(attribute);
					element.setArea(attribute.getStructure().getArea());
					element.setStereotype(stereotypeRepository.findDefault(attribute.getVersion(), Metatype.ELEMENT));
					element.setVersion(attribute.getVersion());
					checkApplicationStereotypicalObject(element);
					attribute.setElement(elementRepository.save(element));
				}
			}
			attributeRepository.save((Attribute)object);
		}
		else
		if(object instanceof Key) {
			entityKeyRepository.save((Key)object);
		}
		else throw new ManagedException("The type " + object.getClass().getSimpleName() + "is not supported in ModelService.save()");
	}
	
	private Container save(Container object) throws ManagedException{
		if(object instanceof Structure) {
			return structureRepository.save((Structure)object);
		}
		else
		if(object instanceof AppEntity) {
			return entityRepository.save((AppEntity)object);
		}
		else throw new ManagedException("The type " + object.getClass().getSimpleName() + "is not supported in ModelService.save()");
	}
	
	private void delete(Detail object) throws ManagedException{
		if(object instanceof Attribute) {
			attributeRepository.delete((Attribute)object);
		}
		else
		if(object instanceof Key) {
			Key key = (Key)object;
			Structure structure = key.getStructure();
			entityKeyRepository.delete(key);
			removeStructure(structure);
		}
		else throw new ManagedException("The type " + object.getClass().getSimpleName() + "is not supported in ModelService.delete()");
	}
	
	private List<Detail> findByParent(Container object) throws ManagedException{
		if(object instanceof Structure) {
			return new ArrayList<Detail>(attributeRepository.findByStructure((Structure)object));
		}
		else
		if(object instanceof AppEntity) {
			return new ArrayList<Detail>(entityKeyRepository.findByEntity((AppEntity)object));
		}
		else throw new ManagedException("The type " + object.getClass().getSimpleName() + "is not supported in ModelService.delete()");
	}
	
	@Transactional
	public Structure saveStructure(Structure structure) throws ManagedException {
		saveChildren(structure);
		return structureRepository.save(structure);
	}

	private void saveChildren(Container container)
			throws ManagedException {
		List<Detail> newDetails = container.getDetails();
		container.setDetails(null);
		container = save(container);
		List<Detail> details = findByParent(container);
		List<Detail> dels = new ArrayList<Detail>();
		dels.addAll(details);
		List<Detail> detailList = new ArrayList<Detail>();
		for(Detail detail : newDetails) {
			if(detail.getId() != null){			
				for(Detail oldDetail : details) {
					if(oldDetail.getId().equals(detail.getId())) {
						dels.remove(oldDetail);
						oldDetail.copyFrom(detail);
						save(oldDetail);
						detailList.add(oldDetail);
					}
				}
			}
			else {
				detail.setContainer(container);
				detail.setVersion(container.getVersion());
				detail.setApplication(container.getApplication());
				save(detail);
				detailList.add(detail);
			}
		}
		for(Detail del : dels) {
			delete(del);
		}
		container.setDetails(detailList);
	}
	
	public void saveEntity(AppEntity entity) throws ManagedException{
		entity.getStructure().setApplication(entity.getApplication());
		entity.getStructure().setVersion(entity.getVersion());
		entity.getStructure().setArea(entity.getArea());
		entity.getStructure().setDescription(entity.getDescription());
		entity.getStructure().setGlossary(entity.getGlossary());
		entity.getStructure().setName(entity.getName());
		entity.getStructure().setSysname(entity.getSysname());
		Structure parentStructure = modelService.saveStructure(entity.getStructure());
		Stereotype structureStereotype = stereotypeRepository.findDefault(entity.getVersion(), Metatype.KEY_STRUCTURE);
		for(Key key : entity.getKeys()) {
			key.setStereotype(stereotypeRepository.findDefault(entity.getVersion(), key.getMetatypeCode()));
			key.setApplication(entity.getApplication());
			key.setVersion(entity.getVersion());
			key.setEntity(entity);

			Structure structure = key.getStructure();
			structure.copyFrom(key);
			structure.setStereotype(structureStereotype);
			structure.setArea(entity.getStructure().getArea());
			structure.setParent(parentStructure);
			for(Attribute detail : structure.getAttributes()) {
				detail.setParent(attributeRepository.findByStructureAndSysname(parentStructure, detail.getSysname()));
			}
			structure = saveStructure(structure);
			
			key.setStructure(structure);
		}
		saveChildren(entity);
		entityRepository.save(entity);
	}

	public void removeStructure(Structure structure) {
		for(Attribute attribute : attributeRepository.findByStructure(structure)) {
			attributeRepository.delete(attribute);
		}
		structureRepository.delete(structure);
	}
	
	public void removeEntity(AppEntity entity) {
		removeStructure(entity.getStructure());
		entityRepository.delete(entity);
	}
}