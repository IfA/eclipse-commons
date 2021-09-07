package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts

import de.tud.et.ifa.agtele.eclipse.webpage.util.IStringSubstitutor
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage
import de.tud.et.ifa.agtele.ResultReporter

abstract class AbstractPageGenerator implements IArtifactGenerator {
	
	protected AbstractHTML fragment;
	
	protected ResultReporter reporter;
	
	protected IStringSubstitutor stringSubstitutor;
	
	new (AbstractHTML fragment, ResultReporter reporter, IStringSubstitutor substitutor) {
		this.fragment = fragment;
		this.reporter = reporter;
		this.stringSubstitutor = substitutor;
	}
	
	override getHtmlFragment() {
		return this.fragment;
	}
	
	override getReporter() {
		return this.reporter;
	}
	
	override getStringSubstitutor() {
		return this.stringSubstitutor;
	}
	
	def WebPage asWebPage () {
		if (this.fragment instanceof WebPage) {
			return this.fragment as WebPage;
		}
		return null;
	}
	
	def MainPage asMainPage() {
		if (this.fragment instanceof MainPage) {
			return this.fragment as MainPage;
		}
		return null;
	}
	
	def Page asPage() {		
		if (this.fragment instanceof Page) {
			return this.fragment as Page;
		}
		return null;
	}
	
	def SubPage asSubPage() {		
		if (this.fragment instanceof SubPage) {
			return this.fragment as SubPage;
		}
		return null;
	}
	
}