package de.tud.et.ifa.agtele.eclipse.webpage.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.AbstractPageGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.PageGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.util.AbstractGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.util.DefaultStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.GenerateJob;
import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

public class WebPageGenerator  extends AbstractGenerator  {
	protected ExecutionEvent event;
	protected List<Exception> errors;
	
	protected String outDirectory = null;
	
	protected ArrayList<WebPage> pages = new ArrayList<>();
	protected IProgressMonitor monitor;
	protected GenerateJob job;
	protected DirectoryManager directoryManager;
	protected ArrayList<AbstractPageGenerator> pageGenerators;
	protected SubMonitor submonitor;
	
	public WebPageGenerator() {}
	
	public void addWebPage(WebPage page) {
		if (!this.pages.contains(page)) {
			this.pages.add(page);			
		}
	}
		
	public boolean isEmpty() {
		return this.pages.isEmpty();
	}
	
	public boolean isGeneratable() {
		return !this.isEmpty();
	}
	
	
	public void generate (IProgressMonitor monitor) {
		this.monitor = monitor;	
		this.directoryManager = new DirectoryManager(pages);
		this.initGenerators();
		try {
			if (!this.directoryManager.prepareDirectories(monitor)) {
				this.includeResults(this.directoryManager);
				this.directoryManager.refreshOutDirectories(monitor);
				finish();
				return;
			}
	
			if (this.monitor != null) {
				this.submonitor = SubMonitor.convert(monitor, this.pageGenerators.size());
			}
			for (AbstractPageGenerator g : this.pageGenerators) {
				if (this.submonitor != null) {
					if (this.submonitor.isCanceled()) {
						break;
					}
					this.submonitor.setTaskName("Generating '" + g.getHtmlFragment().getTargetFilePath() + "'");
					this.submonitor.setWorkRemaining(this.pageGenerators.size() - this.pageGenerators.indexOf(g));
				}
	
				try {
					g.generate();
				} catch (Exception e) {
					this.addError(e);
				}
			}
		} catch (OperationCanceledException e) {
			this.directoryManager.cleanTempDirectory();
		}
		this.directoryManager.refreshOutDirectories(monitor);
		this.includeResults(this.directoryManager);

		if (this.submonitor != null) {
			this.submonitor.done();
		}
		finish();
	}	

	protected void finish() {		
		if (!this.errors.isEmpty()) {
			for (Exception e : this.errors) {
				e.printStackTrace();
			}
		}
	}
	
	protected void initGenerators() {
		this.pageGenerators = new ArrayList<>();
		this.pages.forEach(p -> {
			this.initGenerators(p);
		});
	}
	
	protected void initGenerators(WebPage page) {
		AgteleEcoreUtil.getAllInstances(WebPageModelPackage.Literals.ABSTRACT_HTML, Collections.singletonList(page), true)
			.forEach(o -> this.pageGenerators.add(this.factorGenerator((AbstractHTML)o)));
	}
	
	protected AbstractPageGenerator factorGenerator(AbstractHTML element) {
		return new PageGenerator(element, this, this.createStringSubstitutor());
	}

	protected IStringSubstitutor createStringSubstitutor() {
		return new DefaultStringSubstitutor(this);
	}
	
	public GenerateJob getJob () {
		if (this.job == null) {
			this.job = new GenerateJob("Generate Web Page", this, "de.tud.et.ifa.agtele.commons.webpage.generator");
		}
		return this.job;
	}
	
}
