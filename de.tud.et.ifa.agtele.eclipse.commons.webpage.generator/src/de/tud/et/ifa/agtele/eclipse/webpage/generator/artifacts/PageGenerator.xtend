package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts

import de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts.AbstractPageGenerator
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML
import de.tud.et.ifa.agtele.IStringSubstitutor
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPageModelPackage
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.StringValue
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.MainPage
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage
import java.util.List
import java.util.ArrayList
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AnnouncementLocationEnum
import de.tud.et.ifa.agtele.ResultReporter

class PageGenerator extends AbstractPageGenerator implements BootstrapHtmlGenerator{
	
	new(AbstractHTML fragment, ResultReporter reporter, IStringSubstitutor substitutor) {
		super(fragment, reporter, substitutor)
	}
	
	override navigation(){
		val BootstrapTreeMenuHelper helper = new BootstrapTreeMenuHelper(this.asPage !== null ? this.asPage : this.fragment.page, this.fragment);
		return '''
			<div class="col-sm-3 col-11 border">
				<div class="just-padding">
					<h4>
						<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-diagram-3-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
					  		<path fill-rule="evenodd" d="M8 5a.5.5 0 0 1 .5.5V7H14a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-1 0V8h-5v.5a.5.5 0 0 1-1 0V8h-5v.5a.5.5 0 0 1-1 0v-1A.5.5 0 0 1 2 7h5.5V5.5A.5.5 0 0 1 8 5zm-8 6.5A1.5 1.5 0 0 1 1.5 10h1A1.5 1.5 0 0 1 4 11.5v1A1.5 1.5 0 0 1 2.5 14h-1A1.5 1.5 0 0 1 0 12.5v-1zm6 0A1.5 1.5 0 0 1 7.5 10h1a1.5 1.5 0 0 1 1.5 1.5v1A1.5 1.5 0 0 1 8.5 14h-1A1.5 1.5 0 0 1 6 12.5v-1zm6 0a1.5 1.5 0 0 1 1.5-1.5h1a1.5 1.5 0 0 1 1.5 1.5v1a1.5 1.5 0 0 1-1.5 1.5h-1a1.5 1.5 0 0 1-1.5-1.5v-1z"/>
					  		<path fill-rule="evenodd" d="M6 3.5A1.5 1.5 0 0 1 7.5 2h1A1.5 1.5 0 0 1 10 3.5v1A1.5 1.5 0 0 1 8.5 6h-1A1.5 1.5 0 0 1 6 4.5v-1z"/>
						</svg>
					</h4>
					<div class="list-group list-group-root list-group-flush">
						«IF this.fragment.eContainingFeature == WebPageModelPackage.Literals.SUB_PAGE__SUB_PAGE»
							<a class="btn btn-light up-link" href="«(this.fragment.eContainer as AbstractHTML).url»">
								<span class="inline">
									<svg class="bi bi-arrow-up" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
									  <path fill-rule="evenodd" d="M8 3.5a.5.5 0 01.5.5v9a.5.5 0 01-1 0V4a.5.5 0 01.5-.5z" clip-rule="evenodd"/>
									  <path fill-rule="evenodd" d="M7.646 2.646a.5.5 0 01.708 0l3 3a.5.5 0 01-.708.708L8 3.707 5.354 6.354a.5.5 0 11-.708-.708l3-3z" clip-rule="evenodd"/>
									</svg>
								</span>
								Up to «(this.fragment.eContainer as AbstractHTML).name»
							</a>
						«ENDIF»						
						«helper.createMenu»
					</div>
				</div>
			</div>
		''';
	}

	def String getIcon(Page page) {
		if (page.hasIcon) {
			if (page.iconUrl !== null) {
				return '''
					<span style="width:16px;background-image:url('«page.iconUrl»');"
				'''
			} else if (page.navIcon instanceof StringValue) {
				return '''
					<span>
						«(page.navIcon as StringValue).value»
					</span>
				''';
			}
		}
		return '''''';
	}
	def boolean isRootNode (AbstractHTML element) {
		return element !== null && !(element instanceof Page && !(element.eContainer instanceof WebPage));
	}
	
	def List<Page> getMainBreadCrumbs(AbstractHTML element) {
		var List<Page> result = new ArrayList();
		var Page page = element instanceof Page ? element as Page : element.page;
		while (page !== null && ! (page instanceof WebPage)) {
			result.add(0,page);
			page = page.eContainer() instanceof Page ? page.eContainer() as Page : null;
		}
		
		return result;
	}
	
	def List<Page> mainPages (AbstractHTML element) {
		var List<Page> result = new ArrayList();
		if (!(element instanceof WebPage)) {
			var MainPage page = null;
			if (!(element instanceof MainPage)) {
				page = element.mainPage;
			} else {
				page = element as MainPage;
			}
			
			result.addAll(page.mainPages);
			result.addAll(page.additionalPages);
		}
		
		
		return result;
	}
	
	override String rootNavBarContent() {
		return '''
			«IF this.fragment.webPage !== null»
				<li class="nav-item btn btn-outline-light">
					<a class="nav-link text-white" href="«this.webPage.url»">
						«this.getIcon(this.webPage)»
«««						<span>
«««							<svg class="bi bi-house" color="white" width="1.5em" height="1.5em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
«««							  <path fill-rule="evenodd" d="M2 13.5V7h1v6.5a.5.5 0 00.5.5h9a.5.5 0 00.5-.5V7h1v6.5a1.5 1.5 0 01-1.5 1.5h-9A1.5 1.5 0 012 13.5zm11-11V6l-2-2V2.5a.5.5 0 01.5-.5h1a.5.5 0 01.5.5z" clip-rule="evenodd"/>
«««							  <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 011.414 0l6.647 6.646a.5.5 0 01-.708.708L8 2.207 1.354 8.854a.5.5 0 11-.708-.708L7.293 1.5z" clip-rule="evenodd"/>
«««							</svg>
«««						</span>
						«this.fragment.webPage.compiledNavName»
					</a>
				</li>
				«FOR MainPage page : this.webPage.mainPages»
					<li class="nav-item btn btn-outline-light «this.getNavbarItemClass(page)»">
						<a class="nav-link text-white «BootstrapTreeMenuHelper.containsActiveElement(page, this.fragment) ? "active" : ""»" href="«page.url»">
							«this.getIcon(page)»
							«page.compiledNavName»
						</a>
					</li>
				«ENDFOR»
				«FOR Page page : this.webPage.additionalPages»
					<li class="nav-item btn btn-outline-light ml-auto «this.getNavbarItemClass(page)»">
						<a class="nav-link text-white «BootstrapTreeMenuHelper.containsActiveElement(page, this.fragment) ? "active" : ""»" href="«page.url»">
							«this.getIcon(page)»
							«page.compiledNavName»
						</a>
					</li>
				«ENDFOR»
			«ENDIF»
		''';
	}
	def String getNavbarItemClass(AbstractHTML menuItem) {
		if (BootstrapTreeMenuHelper.containsActiveElement(menuItem, this.fragment) || this.fragment == menuItem) {
			return "active";
		}
		return "";
	}
	
	override String additionalRootNavbarContent () {
		var  List<Page> breadCrumbs = this.getMainBreadCrumbs(this.fragment);
		var  List<Page> mainPages = this.mainPages(this.fragment);
		return '''
			«IF !(this.fragment instanceof WebPage)»
			<nav class="navbar navbar-expand-sm navbar-light navbar-breadcrumbs" aria-label="breadcrumb">
				<ul class="navbar-nav">
					«FOR Page page : breadCrumbs»
						<li class="nav-item btn btn-outline ml-auto «IF breadCrumbs.indexOf(page) == breadCrumbs.size-1» active" aria-current="page"«ELSE»"«ENDIF»>
							<a class="nav-link text-tud" href="«page.url»">
								«this.getIcon(page)»
								«page.compiledNavName»
							</a>
							«IF breadCrumbs.indexOf(page) < breadCrumbs.size -1»
							<svg width="1.5em" height="2em" viewBox="4 0 10 16" class="bi bi-chevron-compact-right breadcrumb-separator" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
							  <path fill-rule="evenodd" d="M6.776 1.553a.5.5 0 0 1 .671.223l3 6a.5.5 0 0 1 0 .448l-3 6a.5.5 0 1 1-.894-.448L9.44 8 6.553 2.224a.5.5 0 0 1 .223-.671z"/>
							</svg>
							«ENDIF»
						</li>
					«ENDFOR»
				</ul>
			</nav>
			«ENDIF»
			«IF !mainPages.empty»
				<nav class="navbar navbar-expand-sm navbar-dark bg-tu2 navbar-sub">
					<ul class="navbar-nav">
						«FOR Page page : mainPages»
							<li class="nav-item btn btn-outline-light ml-auto">
								<a class="nav-link text-white" href="«page.url»">
									«this.getIcon(page)»
									«page.compiledNavName»
								</a>
							</li>
						«ENDFOR»
					</ul>
				</nav>
			«ENDIF»
		''';
	}
	
	override boolean suppressNavBar(){
		return this.asPage !== null ? this.asPage.isSuppressMainMenu: this.htmlFragment.page.isSuppressMainMenu;
	}
	
	override mainContent(){
		var String content = this.getValueContent(this.htmlFragment.content);
		return '''
			<div class="col">
				<div class="row justify-content-end">					
					«this.beforeContent»
					«IF content !== null»
						«content»
					«ELSE»
						[NO CONTENT]
					«ENDIF»
					«this.belowContent»
				</div>
				«this.printMetaData(this.htmlFragment)»
			</div>
		''';	
	}
	
	
	def String belowContent() {
		return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.BELOW_CONTENT));
	}
	
	def String beforeContent() {
		return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_CONTENT));
	}
	
	override beforeFooter() {
		return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_FOOTER));
	}
	
	override beforeHeader() {
		return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_PAGE));
	}
	
	override belowFooter() {
		return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.BELOW_FOOTER));
	}
	
	override beforeTitle() {
		return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.ABOVE_HEADING));
	}	
		
	override belowTitle() {
		return this.printAnnouncements(this.fragment.getCompiledAnnouncements(AnnouncementLocationEnum.BELOW_HEADING));
	}
	
	override getPageTitleText(){
		return '''
			«this.htmlFragment.title»
		''';
	}
	
	override String title() {
		return '''«this.htmlFragment.title»''';
	}
	
	
	override useNavigation () {
		return this.asSubPage !== null ? (
			!this.asSubPage.subPage.empty || 
			this.asSubPage.eContainingFeature === WebPageModelPackage.Literals.SUB_PAGE__SUB_PAGE
		) : false;
	}
}