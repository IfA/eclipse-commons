package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts

import java.util.List
import java.util.ArrayList
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Announcement
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Base

interface BasicHtmlGenerator extends IArtifactGenerator {
		
	def String title() {
		return "NO_NAME";
	}
	
	def List<String> linkedStylesheets() {
		var ArrayList<String> result = new ArrayList();
		result.addAll(
			this.getValueContents(
				this.htmlFragment.expandIncludes(this.htmlFragment.styleList, false)
			)
		);		
		return result;
	}
	
	def List<String> linkedScripts() {
		var ArrayList<String> result = new ArrayList();
		result.addAll(
			this.getValueContents(
				this.htmlFragment.expandIncludes(this.htmlFragment.scriptList, false)
			)
		);		
		return result;
	}
	
	def List<String> inlineStylesheets() {
		var ArrayList<String> result = new ArrayList();
		result.addAll(
			this.getValueContents(
				this.htmlFragment.expandIncludes(this.htmlFragment.styleList, true)
			)
		);		
		return result;
	}
	
	def List<String> inlineScripts() {
		var ArrayList<String> result = new ArrayList();
		result.addAll(
			this.getValueContents(
				this.htmlFragment.expandIncludes(this.htmlFragment.scriptList, true)
			)
		);		
		return result;
	}
	
		
	def header() {
		return '''''';
	}
	
	def navigation(){
		return '''''';
	}
	
	def footer(){
		return '''''';
	}
	
	def mainContent(){
		return '''''';
	}
	def specialContent(){
		return '''''';
	}
	def pageTitle() {
		return '''<h1>«this.title»</h1>''';
	}
	def mainStructure() {
		return '''
			«pageTitle»
			«mainContent»
		''';
	}
	def boolean useNavigation() {
		return true;
	}
	def String beforeHeader() {
		return "";
	}
	def String beforeFooter() {
		return "";
	}
	def String belowFooter() {
		return "";
	}
	override String getContent() {
		return '''
			<!DOCTYPE html>
			<html>
				<head>				
					<meta charset="UTF-8"/>
					<meta name="viewport" content="width=device-width, initial-scale=1.0"/>		
					<title>«title»</title>
					«FOR ls : linkedStylesheets»
						«IF ls !== null && !ls.blank»
							<link rel="stylesheet"  type="text/css" href="«ls»"/>
						«ENDIF»
					«ENDFOR»
					«FOR inlineStyle : inlineStylesheets»
						«IF inlineStyle !== null && !inlineStyle.blank»
							<style>
								«inlineStyle»
							</style>
						«ENDIF»
					«ENDFOR»
					«FOR ls : linkedScripts»
						«IF ls !== null && !ls.blank»
							<script src="«ls»">/*no content*/</script>
						«ENDIF»
					«ENDFOR»
				</head>
				<body>
					«this.beforeHeader»
					<header>
						«header»
					</header>
					<main>
						«mainStructure»
					</main>
					<footer>
						«this.beforeFooter»
						«footer»
						«this.belowFooter»
					</footer>
					«FOR inlineScript : inlineScripts»
						«IF inlineScript !== null && !inlineScript.blank»
							<script type="text/javascript">
								«inlineScript»
							</script>
						«ENDIF»
					«ENDFOR»
				</body>
			</html>		
		''';
		
	}
	
	//Helper Methods	
	def String stringToLink(String uri, String displayedString) {
		return '''
			<a href="«uri»">«displayedString»</a>
		''';
	}
	
	def String printMetaData(Base b) {
		return '''
			«IF b.createdOn !== null && !b.createdOn.blank || b.createdBy !== null && !b.createdBy.blank || b.lastModified !== null && !b.lastModified.blank || b.lastModifiedBy !== null && !b.lastModifiedBy.blank»
				<footer class="metadata blockquote-footer">
				«IF b.createdOn !== null && !b.createdOn.blank || b.createdBy !== null && !b.createdBy.blank»
					Created «IF b.createdOn !== null && !b.createdOn.blank»on «b.createdOn»«ENDIF»«IF b.createdBy !== null && !b.createdBy.blank» by «b.createdBy»«ENDIF» 
					«IF b.lastModified !== null && !b.lastModified.blank || b.lastModifiedBy !== null && !b.lastModifiedBy.blank»; «ENDIF»
				«ENDIF»
				«IF b.lastModified !== null && !b.lastModified.blank || b.lastModifiedBy !== null && !b.lastModifiedBy.blank»
					Last modified «IF b.lastModified !== null && !b.lastModified.blank»on «b.lastModified»«ENDIF»«IF b.lastModifiedBy !== null && !b.lastModifiedBy.blank» by «b.lastModifiedBy»«ENDIF» 
				«ENDIF»
				</footer>
			«ENDIF»
		''';
	}
	
	def String getAnnouncementCssClass(Announcement a) {
		return a.type.literal.toLowerCase;
	}
	
	def String printAnnouncement(Announcement a) {
		var String content = this.getValueContent(a.content);
		if (content === null) {
			return '''''';
		}
		return '''
			<div class="alert alert-«this.getAnnouncementCssClass(a)» «IF a.closable»alert-dismissible«ENDIF»" role="alert">
			 	«content»«this.printMetaData(a)»
			 	«IF a.closable»
			 		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			 			<span aria-hidden="true">&times;</span>
			 		</button>
		 	  	«ENDIF»
			</div>
		''';
	}
	
	def String printAnnouncements(List<Announcement> announcements) {
		return '''
			«FOR Announcement a : announcements»
				«this.printAnnouncement(a)»
			«ENDFOR»
		''';
	}
}
