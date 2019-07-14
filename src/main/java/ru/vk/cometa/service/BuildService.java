package ru.vk.cometa.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import ru.vk.cometa.controller.BaseService;
import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.ApplicationStereotypicalObject;
import ru.vk.cometa.model.Assembly;
import ru.vk.cometa.model.Build;
import ru.vk.cometa.model.BuildLog;
import ru.vk.cometa.model.Component;
import ru.vk.cometa.model.Dependency;
import ru.vk.cometa.model.Generator;
import ru.vk.cometa.model.Package;
import ru.vk.cometa.model.Version;

@Service
@Transactional(rollbackFor=Exception.class)
public class BuildService extends BaseService{
	@PersistenceContext
	EntityManager entityManager;
	
	private boolean findVersionInAssembly(Assembly assembly, Version version) {
		for(Version v : assembly.getVersions()) {
			if(v.getId().equals(version.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public void saveAssembly(Assembly assembly) throws ManagedException{
		for(Version version : assembly.getVersions()) {
			for(Dependency dependency : dependencyRepository.findByDependentVersion(version)) {
				if(!findVersionInAssembly(assembly, dependency.getInfluencerVersion())) {
					throw new ManagedException("The module " + version.getLabel() + " depends on the module " + dependency.getInfluencerVersion().getLabel());
				}
			}
		}
		assemblyRepository.save(assembly);
	}
	
	public void removeAssembly(Assembly assembly) throws ManagedException{
		assemblyRepository.delete(assembly);
	}
	
	private File findOrCreateDir(File dir, String subdirName) throws ManagedException{
		return findOrCreateDir(new File(dir, subdirName));
	}
	
	private File findOrCreateDir(File dir) throws ManagedException{
		if(!dir.exists()) {
			if(!dir.mkdir()) {
				throw new ManagedException("The folder " + dir.getAbsolutePath() + " does not exist");
			}
		}
		return dir;
	}
	
	protected List<Package> findRootPackages(Assembly assembly) throws ManagedException{
		List<Package> result = new ArrayList<Package>();
		for(Version version : assembly.getVersions()) {
			result.addAll(packageRepository.findByVersionAndParent(version, null));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<ApplicationStereotypicalObject> selectObjectsByComponent(Component component){
		String queryText = "select a from " + component.getMetatype().getMetaobject() + 
				" a where a.application = :application";
		Query query = entityManager.createQuery(queryText);
		query.setParameter("application", component.getApplication());
		
		return new ArrayList<ApplicationStereotypicalObject>(query.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public ApplicationStereotypicalObject selectObjectsByComponentAndId(Component component, Integer id) throws ManagedException{
		String queryText = "select a from " + component.getMetatype().getMetaobject() + 
				" a where a.application = :application and a.id = :id";
		Query query = entityManager.createQuery(queryText);
		query.setParameter("application", component.getApplication());
		query.setParameter("id", id);
		List<ApplicationStereotypicalObject> result = new ArrayList<ApplicationStereotypicalObject>(query.getResultList());
		if(result.size() == 1) {
			return result.get(0);
		}
		else {
			throw new ManagedException("The object " + component.getMetatype().getMetaobject() + " was not found");
		}
	}
	
	protected void processGenerator(Generator generator, Component component, File packageDir, Build build) throws ManagedException{
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate(generator.getSysname(), generator.getResource().getText());
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setTemplateLoader(stringLoader);
		
		try {
			Template temp = cfg.getTemplate(generator.getSysname());
			for(ApplicationStereotypicalObject object : selectObjectsByComponent(component)) {
				BuildLog buildLog = new BuildLog();
				buildLog.setApplication(build.getApplication());
				buildLog.setBuild(build);
				buildLog.setIsDirectory(false);
				buildLog.setFile(component.getSysname() + object.getSysname());
				buildLog.setGenerator(generator);
				buildLog.setMetaobject(component.getMetatype().getMetaobject());
				buildLog.setObjectId(object.getId());
				
				Map<String, Object> root = new HashMap<>();
				root.put(component.getMetatype().getMetaobject(), object);
				File outputFile = new File(packageDir, buildLog.getFile());
				FileOutputStream fileOutStream = new FileOutputStream(outputFile);
				OutputStreamWriter outWriter = new OutputStreamWriter(fileOutStream);
				temp.process(root, outWriter);
				
				buildLog.setPath(outputFile.getAbsolutePath());
				buildLogRepository.save(buildLog);
			}
		}
		catch(Exception ex) {
			throw new ManagedException(ex.getMessage());
		}
	}

	public String processTemplate(String templateName, String templateTetx, ApplicationStereotypicalObject object) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate(templateName, templateTetx);
		
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		
		cfg.setTemplateLoader(stringLoader);
		
		Template temp = cfg.getTemplate(templateName);
		Map<String, Object> root = new HashMap<>();
		root.put("object", object);
		StringWriter out = new StringWriter();
		temp.process(root, out);
		return out.toString();
	}
	
	protected void processComponent(Component component, File packageDir, Build build) throws ManagedException{
		for(Generator generator : generatorRepository.findByPlatform(component.getPlatform())) {
			processGenerator(generator, component, packageDir, build);
		}
	}
	
	protected void processPackages(List<Package> packages, File dir, Build build) throws ManagedException{
		for(Package pack: packages) {
			BuildLog buildLog = new BuildLog();
			buildLog.setApplication(build.getApplication());
			buildLog.setBuild(build);
			buildLog.setIsDirectory(true);
			buildLog.setFile(pack.getSysname());
			buildLog.setGenerator(null);
			buildLog.setMetaobject(Package.class.getSimpleName());
			buildLog.setObjectId(pack.getId());
			
			File packageDir = findOrCreateDir(dir, pack.getSysname());
			for(Component component : componentRepository.findByPack(pack)) {
				processComponent(component, packageDir, build);
			}
			processPackages(packageRepository.findByParent(pack), packageDir, build);

			buildLog.setPath(packageDir.getAbsolutePath());
			buildLogRepository.save(buildLog);
		}
	}
	
	public void buildAssembly(Assembly assembly) throws ManagedException{
		Application application = assembly.getApplication();
		Build build = new Build();
		build.setAssembly(assembly);
		build.setApplication(application);
		build.setOnTime(new Date());
		build.setNumber(buildRepository.findByAssembly(assembly).size());
		build = buildRepository.save(build);

		File rootDir = findOrCreateDir(new File (config.getProperty("dir.root")));
		File appDir = findOrCreateDir(rootDir, "app_" + application.getSysname());
		File assemblyDir = findOrCreateDir(appDir, "assembly_" + assembly.getSysname());
		File buildDir = findOrCreateDir(assemblyDir, "build_" + build.getNumber());
		processPackages(findRootPackages(assembly), buildDir, build);
		build.setPath(buildDir.getAbsolutePath());
		buildRepository.save(build);
	}
	
}
