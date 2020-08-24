package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts;

import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.IArtifactGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor;
import de.tud.et.ifa.agtele.eclipse.webpage.util.ResultReporter;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;

@SuppressWarnings("all")
public abstract class AbstractPageGenerator implements IArtifactGenerator {
  protected AbstractHTML fragment;
  
  protected ResultReporter reporter;
  
  protected IStringSubstitutor stringSubstitutor;
  
  public AbstractPageGenerator(final AbstractHTML fragment, final ResultReporter reporter, final IStringSubstitutor substitutor) {
    this.fragment = fragment;
    this.reporter = reporter;
    this.stringSubstitutor = substitutor;
  }
  
  @Override
  public AbstractHTML getHtmlFragment() {
    return this.fragment;
  }
  
  @Override
  public ResultReporter getReporter() {
    return this.reporter;
  }
  
  @Override
  public IStringSubstitutor getStringSubstitutor() {
    return this.stringSubstitutor;
  }
  
  public WebPage asWebPage() {
    if ((this.fragment instanceof WebPage)) {
      return ((WebPage) this.fragment);
    }
    return null;
  }
  
  public MainPage asMainPage() {
    if ((this.fragment instanceof MainPage)) {
      return ((MainPage) this.fragment);
    }
    return null;
  }
  
  public Page asPage() {
    if ((this.fragment instanceof Page)) {
      return ((Page) this.fragment);
    }
    return null;
  }
  
  public SubPage asSubPage() {
    if ((this.fragment instanceof SubPage)) {
      return ((SubPage) this.fragment);
    }
    return null;
  }
}
