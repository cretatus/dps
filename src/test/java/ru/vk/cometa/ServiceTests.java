package ru.vk.cometa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import ru.vk.cometa.core.ManagedException;
import ru.vk.cometa.model.AppModule;
import ru.vk.cometa.model.Application;
import ru.vk.cometa.model.MajorVersion;
import ru.vk.cometa.model.Package;
import ru.vk.cometa.model.Version;
import ru.vk.cometa.repositories.ApplicationRepository;
import ru.vk.cometa.repositories.DependencyRepository;
import ru.vk.cometa.repositories.MajorVersionRepository;
import ru.vk.cometa.repositories.MetatypeRepository;
import ru.vk.cometa.repositories.ModuleRepository;
import ru.vk.cometa.repositories.PlatformRepository;
import ru.vk.cometa.repositories.StereotypeRepository;
import ru.vk.cometa.repositories.UserRepository;
import ru.vk.cometa.repositories.VersionRepository;
import ru.vk.cometa.service.ModuleService;
import ru.vk.cometa.service.PackageService;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTests {

	@Mock
	protected UserRepository userRepository;
	@Mock
	protected ApplicationRepository applicationRepository;
	@Mock
	protected ModuleRepository moduleRepository;
	@Mock
	protected MajorVersionRepository majorVersionRepository;
	@Mock
	protected VersionRepository versionRepository;
	@Mock
	protected DependencyRepository dependencyRepository;
	@Mock
	protected StereotypeRepository stereotypeRepository;
	@Mock
	protected MetatypeRepository metatypeRepository;
	@Mock
	protected PlatformRepository platformRepository;

	private ModuleService moduleService;
	private PackageService packageService;
		
	@Before
	public void setUp() {
		moduleService = new ModuleServiceMock(userRepository, applicationRepository,
				moduleRepository, majorVersionRepository,
				versionRepository, dependencyRepository,
				stereotypeRepository, metatypeRepository,
				platformRepository);
		packageService = new PackageServiceMock(userRepository, applicationRepository,
				moduleRepository, majorVersionRepository,
				versionRepository, dependencyRepository,
				stereotypeRepository, metatypeRepository,
				platformRepository);
	}
	
	@Test
	public void testRemoveMinorVersion() throws Exception {
		MajorVersion majorVersion = new MajorVersion();
		majorVersion.setId(100);
		majorVersion.setNumber(1);
		Version v0 = new Version();
		v0.setMajorVersion(majorVersion);
		v0.setNumber(0);
		Version v1 = new Version();
		v1.setMajorVersion(majorVersion);
		v1.setNumber(1);
		when(majorVersionRepository.findOne(100)).thenReturn(majorVersion);
		when(versionRepository.findByMajorVersion(majorVersion)).thenReturn(Arrays.asList(v0, v1));
		moduleService.removeMinorVersion(v1);
		verify(versionRepository).delete(v1);
		try {
			moduleService.removeMinorVersion(v0);
			assertFalse(true);
		}
		catch(ManagedException ex) {
			assertEquals(ex.getMessage(), "You can remove only last minor version");
		}
	}

	@Test
	public void testSaveModule() throws Exception {
		Application application = new Application();
		AppModule module = new AppModule();
		when(moduleRepository.findOne(anyInt())).thenReturn(module);
		List<MajorVersion> list = new ArrayList<MajorVersion>();
		doAnswer((Answer<MajorVersion>) invocation -> {
			list.add((MajorVersion)invocation.getArguments()[0]);
	        return null;
	    }).when(majorVersionRepository).save(any(MajorVersion.class));
		doAnswer((Answer<MajorVersion>) invocation -> {
	        return list.get(0);
	    }).when(majorVersionRepository).findOne(anyInt());
		module.setApplication(application);
		moduleService.saveModule(module);
		verify(moduleRepository).save(module);
		ArgumentCaptor<MajorVersion> ac1 = ArgumentCaptor.forClass(MajorVersion.class);
		verify(majorVersionRepository).save(ac1.capture());
		MajorVersion mv0 = ac1.getValue();
		assertEquals(mv0.getModule(), module);
		assertEquals(mv0.getNumber(), Integer.valueOf(0));
		ArgumentCaptor<Version> ac2 = ArgumentCaptor.forClass(Version.class);
		verify(versionRepository).save(ac2.capture());
		Version v0 = ac2.getValue();
		assertEquals(v0.getModule(), module);
		assertEquals(v0.getNumber(), Integer.valueOf(0));
		
	}
	
	@Test
	public void testFindPath() throws Exception {
		Package p1 = new Package();p1.setSysname("p1");
		Package p2 = new Package();p2.setSysname("p2");

		Package p11 = new Package();p11.setSysname("p11");p11.setParent(p1);
		Package p12 = new Package();p12.setSysname("p12");p12.setParent(p1);
		
		Package p111 = new Package();p111.setSysname("p111");p111.setParent(p11);
		Package p112 = new Package();p112.setSysname("p112");p112.setParent(p11);
		Package p113 = new Package();p113.setSysname("p113");p113.setParent(p11);
		Package p121 = new Package();p121.setSysname("p121");p121.setParent(p12);
		
		assertEquals("p1.p12.p121", p121.getPath());
		assertEquals("p2", p2.getPath());
		assertEquals("p1.p11", p11.getPath());
		
	}
	
	@Test
	public void testSaveDependency() throws Exception {
		MajorVersion majorVersion = new MajorVersion();
		majorVersion.setId(100);
		majorVersion.setNumber(1);
		Version v0 = new Version();
		v0.setMajorVersion(majorVersion);
		v0.setNumber(0);
		Version v1 = new Version();
		v1.setMajorVersion(majorVersion);
		v1.setNumber(1);
		when(majorVersionRepository.findOne(100)).thenReturn(majorVersion);
		when(versionRepository.findByMajorVersion(majorVersion)).thenReturn(Arrays.asList(v0, v1));
		moduleService.removeMinorVersion(v1);
		verify(versionRepository).delete(v1);
		try {
			moduleService.removeMinorVersion(v0);
			assertFalse(true);
		}
		catch(ManagedException ex) {
			assertEquals(ex.getMessage(), "You can remove only last minor version");
		}
	}

}