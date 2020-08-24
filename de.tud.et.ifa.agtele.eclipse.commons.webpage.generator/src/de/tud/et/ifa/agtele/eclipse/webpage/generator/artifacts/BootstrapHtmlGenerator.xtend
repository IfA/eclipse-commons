package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts

import java.util.List

interface BootstrapHtmlGenerator extends BasicHtmlGenerator {
		
		
	override List<String> linkedStylesheets() {
		val list = BasicHtmlGenerator.super.linkedStylesheets;
		list.add(this.webPage.baseUrl+"/resources/bootstrap/css/bootstrap.min.css");
		list.add(this.webPage.baseUrl+"/resources/bootstrap/css/style.css");
		return list;		
	}

	override List<String> linkedScripts() {		 
		val list = BasicHtmlGenerator.super.linkedScripts;
		list.add(this.webPage.baseUrl+"/resources/bootstrap/js/jquery-3.3.1.slim.min.js");
		list.add(this.webPage.baseUrl+"/resources/bootstrap/js/bootstrap.bundle.min.js");
		list.add(this.webPage.baseUrl+"/resources/bootstrap/js/application.js");
		return list;
	}
	
	def String rootNavBarContent() {
		return '''''';
	}
//	def String mainNavBarContent() {
//		return '''''';
//	}
	
	def boolean suppressNavBar(){
		return false;
	}
	
	def getLangSwitchLinks(){
		return '''''';
	}
	
	def getPageTitleText(){
		return '''''';
	}
	override header(){
		return '''
			<div class="container bg-tu p-3">
				<div class="row">
					«this.getValueContent(this.htmlFragment.header)»
«««					<div class="col">
«««						<img src="«this.webPage.baseUrl»/resources/bootstrap/img/tud_logo.png" width="143" height="42" class="d-inline-block align-top ml-0" alt=""/>
«««					</div>
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
				</div>
			«ENDIF»
		''';
	}
		
	override footer(){
		return '''
			<footer>
			    <div class="container bg-tu p-3">
					<div class="row">
						«this.getValueContent(this.htmlFragment.footer)»
«««					             «IF catalogueContext.organizationInfo !== null»
«««				«""»            <div class="col">
«««				«""»                <h4>Organization</h4>
«««				«""»                <p>«printOrganizationInfo(catalogueContext.organizationInfo)»</p>
«««				«""»            </div>
«««				«ENDIF»
«««				«IF catalogueContext.contactInfo !== null»
«««			«""»        	<div class="col">
«««			«""»				<h4>Contact</h4>
«««			«""»				<p>«printContactInfo(catalogueContext.contactInfo)»</p>
«««			«""»			</div>
«««					«ENDIF»
«««					«IF catalogueContext.legalInfo !== null»
«««			«""»			<div class="col">
«««			«""»				<h4>Legal Information</h4>
«««			«""»				<p>«printLegalInfo(catalogueContext.legalInfo)»</p>
«««			«""»			</div>
«««						«ENDIF»
«««						«IF catalogueContext.licenseInfo !== null»
«««			«""»			<div class="col">
«««			«""»				<h4>License</h4>
«««			«""»				<p>«printLicenseInfo(catalogueContext.licenseInfo)»</p>
«««			«""»			</div>
«««							«ENDIF»
«««							</div>
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
				<div class="row">
					<div class="col">		
						<h1>«getPageTitleText»</h1>
					</div>
				</div>
			«ENDIF»
		''';
	}
		
	override String mainStructure() {
		return '''
			<div class="container">
				«getPageTitle»
				<div class="row section-padding">
					«IF this.useNavigation»
						«navigation»
					«ENDIF»
					«mainContent»
				</div>
			</div>
		''';
	}
	
	//Helper Methods
}
