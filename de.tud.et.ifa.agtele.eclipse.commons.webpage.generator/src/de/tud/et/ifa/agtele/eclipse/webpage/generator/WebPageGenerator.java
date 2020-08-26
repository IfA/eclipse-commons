package de.tud.et.ifa.agtele.eclipse.webpage.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.internal.variables.StringVariableManager;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IStringVariable;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.IValueVariable;
import org.eclipse.core.variables.IValueVariableListener;

import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.AbstractPageGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.PageGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.util.AbstractGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.util.DefaultStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ExtendedStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ExtendedStringSubstitutor.SubstitutionVariable;
import de.tud.et.ifa.agtele.eclipse.webpage.util.GenerateJob;
import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ResultReporter;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;

public class WebPageGenerator  extends AbstractGenerator  {
	protected ExecutionEvent event;
	
	protected String outDirectory = null;
	
	protected ArrayList<WebPage> pages = new ArrayList<>();
	protected IProgressMonitor monitor;
	protected GenerateJob job;
	protected DirectoryManager directoryManager;
	protected ArrayList<AbstractPageGenerator> pageGenerators;
	protected SubMonitor submonitor;
	
	protected Map<WebPage, PageStringSubstitutor> pageStringSubstitutors = new HashMap<>();
	
	public WebPageGenerator() {
		super();
	}
	
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
		this.directoryManager = new DirectoryManager(pages, this.createStringSubstitutor());
		this.preparePageStringSubstitutors();
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
	
	protected void preparePageStringSubstitutors() {
		for (WebPage page : this.pages) {
			this.pageStringSubstitutors.put(page, this.createPageStringSubstitutor(page));
		}
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
			.forEach(o -> {
				if (!((AbstractHTML)o).isSuppressArtifact()) {
					this.pageGenerators.add(this.factorGenerator((AbstractHTML)o));
				}
			});
	}
	
	protected AbstractPageGenerator factorGenerator(AbstractHTML element) {
		return new PageGenerator(element, this, this.createFragmentStringSubstitutor(element));
	}
	
	protected IStringSubstitutor createStringSubstitutor() {
		return new DefaultStringSubstitutor(this);
	}

	protected PageStringSubstitutor createPageStringSubstitutor(WebPage page) {
		return new PageStringSubstitutor(this, page);
	}
	
	protected FragmentStringSubstitutor createFragmentStringSubstitutor (AbstractHTML fragment) {
		return new FragmentStringSubstitutor(fragment);
	}
	
	public GenerateJob getJob () {
		if (this.job == null) {
			this.job = new GenerateJob("Generate Web Page", this, "de.tud.et.ifa.agtele.commons.webpage.generator");
		}
		return this.job;
	}
	
	static public class UrlVariable extends SubstitutionVariable {
		protected List<? extends AbstractHTML> elements;

		public UrlVariable (List<? extends AbstractHTML> elements) {
			super("URL");
			this.elements = elements;
		}
		@Override
		public String getValue(String parameter) {
			if (parameter == null || parameter.isBlank() || this.elements == null || this.elements.isEmpty()) {
				return null;
			}
			for (AbstractHTML html : this.elements) {
				if (html.getId().equals(parameter)) {
					return html.getUrl();
				}
			}
			return null;
		}
	}
	
	static public class RelativeUrlVariable extends SubstitutionVariable {
		protected AbstractHTML element;

		public RelativeUrlVariable (AbstractHTML element) {
			super("URL");
			this.element = element;
		}
		@Override
		public String getValue(String parameter) {
			if (this.element == null) {
				return null;
			}
			if (parameter == null || parameter.isBlank()) {
				return this.element.getUrl();
			}
			if ("parent".equals(parameter) && this.element.eContainer() instanceof AbstractHTML) {
				return ((AbstractHTML)this.element.eContainer()).getUrl();
			}
			if ("root".equals(parameter)) {
				WebPage root = this.element.getWebPage();
				if (this.element instanceof WebPage) {
					root = (WebPage) this.element;
				}
				if (root != null) {
					return root.getUrl();
				}				
			}
			if ("base".equals(parameter)) {
				WebPage root = this.element.getWebPage();
				if (this.element instanceof WebPage) {
					root = (WebPage) this.element;
				}
				if (root != null) {
					return root.getBaseUrl();
				}				
			}
			if ("page".equals(parameter)) {
				Page page = this.element.getPage();
				if (this.element instanceof Page) {
					page = (Page) this.element;
				}
				if (page != null) {
					return page.getUrl();
				}				
			}
			return null;
		}
	}
	
	static public class PageStringSubstitutor extends ExtendedStringSubstitutor {
		public PageStringSubstitutor(ResultReporter reporter, WebPage page) {
			super(reporter);
			this.factorVariables(page);
		}
		protected void factorVariables(WebPage page) {
			@SuppressWarnings("unchecked")
			List<? extends AbstractHTML> elements = (List<? extends AbstractHTML>) AgteleEcoreUtil.getAllInstances(WebPageModelPackage.Literals.ABSTRACT_HTML, page,true);

			this.addVariable(new UrlVariable(elements));
		}
	}
	
	public class FragmentStringSubstitutor extends ExtendedStringSubstitutor {
		public FragmentStringSubstitutor(AbstractHTML fragment) {
			super(WebPageGenerator.this.pageStringSubstitutors.get(fragment instanceof WebPage ? (WebPage) fragment : fragment.getWebPage()));	
			this.factorVariables(fragment);
		}
		protected void factorVariables(AbstractHTML fragment) {
			this.addVariable(new RelativeUrlVariable(fragment));
		}
	}
}
