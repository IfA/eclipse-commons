package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts

import java.util.List

interface BootstrapHtmlGenerator extends BasicHtmlGenerator {
		
	override List<String> linkedStylesheets() {
		val list = BasicHtmlGenerator.super.linkedStylesheets;
		list.add(this.webPage.baseUrl+"/templateResources/css/bootstrap.min.css");
		list.add(this.webPage.baseUrl+"/templateResources/css/style.css");
		return list;		
	}

	override List<String> linkedScripts() {		 
		val list = BasicHtmlGenerator.super.linkedScripts;
		list.add(this.webPage.baseUrl+"/templateResources/js/jquery-3.3.1.slim.min.js");
		list.add(this.webPage.baseUrl+"/templateResources/js/bootstrap.bundle.min.js");
		list.add(this.webPage.baseUrl+"/templateResources/js/application.js");
		return list;
	}
	
	def String rootNavBarContent() {
		return '''''';
	}
	
	def String additionalRooNavbarContent(){
		return '''''';
	}

	def boolean suppressNavBar(){
		return false;
	}
	
	def getLangSwitchLinks(){
		return '''''';
	}
	
	override pageTitle() {
		return '''
			<div class="row">
				<div class="col">
					<h1>«this.title»</h1>
				</div>
			</div>
		''';
	}
		
	def getPageTitleText(){
		return '''''';
	}
	override header(){
		return '''
			<div class="container bg-tu p-3">
				<div class="row">
					«this.getValueContent(this.htmlFragment.targetHeader)»
				</div>
			</div>
			«IF !this.suppressNavBar»
				<div class="container navcontainer">
					<nav class="navbar navbar-expand-sm navbar-dark bg-tu">
						<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
							<span class="navbar-toggler-icon"></span>
						</button>
				
						<div class="collapse navbar-collapse" id="navbarNav">
							<ul class="navbar-nav">
								<!-- Links -->
								«this.rootNavBarContent»
						
	«««					                     «IF catalogueContext !== null && version !== null»  
	«««						<li class="nav-item btn btn-outline-light">                        
	«««				«""»					<a class="nav-link text-white" href="«catalogueIndex.baseUrl+catalogueContext.getQueryString(version,language)»">Catalogue «version.versionString»</a>
	«««									</li>
	«««							«ENDIF»
	«««			                         «IF artifactContext !== null» 
	«««				<li class="nav-item btn btn-outline-light">                         
	«««				«""»					<a class="nav-link text-white" href="«artifactContext.getHTMLFrontendURL(artifactContext.model,version)»">Model «artifactContext.model.name»</a>
	«««									</li> 
	«««							«ENDIF»
							</ul>
	«««						«IF catalogueIndex.enableSearch»
	«««							<form class="form-inline">
	«««								<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
	«««								<button class="btn btn-outline-secondary my-2 my-sm-0 mr-md-5" type="submit" disabled>Search</button>
	«««							</form>
	«««						«ENDIF»
	«««						«IF catalogueContext.supportedLanguages.size > 1»
	«««							<ul class="navbar-nav ml-auto">
	«««								<li class="nav-item dropdown">
	«««									<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">Language</a>
	«««									<div class="dropdown-menu dropdown-menu-right">
	«««										«langSwitchLinks»
	«««									</div>
	«««								</li>
	«««							</ul>
	«««						«ENDIF»
						</div> 
					</nav>
					«this.additionalRooNavbarContent()»
				</div>
			«ENDIF»
		''';
	}
		
	override footer(){
		return '''
			<footer>
			    <div class="container bg-tu p-3">
					<div class="row">
						«this.getValueContent(this.htmlFragment.targetFooter)»
					</div>
				</div>
			</footer>
		''';
	}
	
	override useNavigation () {
		return false;
	}
	
	def String getPageTitle () {
		return '''			
			«IF !getPageTitleText.blank»
				<div class="row «IF this.useNavigation»justify-content-end«ENDIF»">
					<div class="col«IF this.useNavigation»-11«ENDIF»">		
						<h1>«getPageTitleText»</h1>
					</div>
				</div>
			«ENDIF»
		''';
	}
		
	override String mainStructure() {
		return '''
			<div class="container">
				«this.beforeTitle»
				«getPageTitle»
				«this.belowTitle»
				<div class="row section-padding">
					«IF this.useNavigation»
						«navigation»
					«ENDIF»
					«mainContent»
				</div>
			</div>
		''';
	}
	
	def String belowTitle() {
		return "";
	}
	
	def String beforeTitle() {
		return "";
	}
	
	//Helper Methods
}
