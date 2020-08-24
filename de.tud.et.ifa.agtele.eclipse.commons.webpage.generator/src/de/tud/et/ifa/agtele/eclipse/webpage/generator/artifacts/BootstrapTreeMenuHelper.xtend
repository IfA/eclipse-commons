package de.tud.et.ifa.agtele.eclipse.webpage.generator.artifacts

import java.util.List
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.AbstractHTML
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.SubPage
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.Page

class BootstrapTreeMenuHelper {
		
	int collapseCounter = 0;
	
	protected AbstractHTML element;
	
	protected Page localRoot;
		
	new (Page localRoot, AbstractHTML element) {
		this.element = element;
		this.localRoot = localRoot;
	}
	
	def boolean isNode(SubPage page) {
		return !page.subPage.empty;
	}
	
	def List<SubPage> getChildren(SubPage element) {
		return element.subPage;
	}
	
	def static boolean containsActiveElement (AbstractHTML parent, AbstractHTML page) {
		return AgteleEcoreUtil.getAllContainers(parent).contains(page);
	}
	def boolean containsActiveElement (AbstractHTML page) {
		return BootstrapTreeMenuHelper.containsActiveElement(page, this.element);
	}
	
	def String createMenu() {
		return '''
			<ul class="nav nav-list">
				«createNodeChildren(this.localRoot)»
			</ul>
		''';	
	}
	
	def String createNavElement(SubPage element) {
		if (isNode(element)) {
			return createNode(element);
		}
		return createLeaf(element);
	}
	
	def String createNodeChildren(SubPage node) {
		return '''		
			«FOR SubPage c : getChildren(node).sortBy[c|c.name?:""]»
				«createNavElement(c)»
			«ENDFOR»
		''';
	}
		
	def String getClass(SubPage node) {
		var String result = "";
		if (isNode(node)) {
			if (!(containsActiveElement(node) || node === this.element)) {
				result += " collapsed";				
			}
		}
		if (node === this.element || this.containsActiveElement(node)) {
			result += " active";
		}
		return result;
	}
	
	def String createNode(SubPage node) {
		return '''		
			<li class="tree-node">
				<a class="«getClass(node)» btn btn-outline-dark tree-node-button«IF getClass(node).contains("active")» active«ENDIF»" data-id="«node.id»" data-toggle="collapse" role="button" href="#collapsible«collapseCounter»" >
					<span class="inline-block">
						<svg class="bi bi-chevron-right" width="1.5em" height="1.5em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
						  <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 01.708 0l6 6a.5.5 0 010 .708l-6 6a.5.5 0 01-.708-.708L10.293 8 4.646 2.354a.5.5 0 010-.708z" clip-rule="evenodd"/>
						</svg>
						<svg class="bi bi-chevron-down" width="1.5em" height="1.5em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
						  <path fill-rule="evenodd" d="M1.646 4.646a.5.5 0 01.708 0L8 10.293l5.646-5.647a.5.5 0 01.708.708l-6 6a.5.5 0 01-.708 0l-6-6a.5.5 0 010-.708z" clip-rule="evenodd"/>
						</svg>
					</span>
				</a>
				<a class="btn btn-link tree-node-link«IF getClass(node).contains("active")» active«ENDIF»" role="button" href="«node.url»">
«««					<span class="inline ml-auto">
«««						<svg class="bi bi-reply" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
«««						  <path fill-rule="evenodd" d="M9.502 5.013a.144.144 0 00-.202.134V6.3a.5.5 0 01-.5.5c-.667 0-2.013.005-3.3.822-.984.624-1.99 1.76-2.595 3.876C3.925 10.515 5.09 9.982 6.11 9.7a8.741 8.741 0 011.921-.306 7.403 7.403 0 01.798.008h.013l.005.001h.001L8.8 9.9l.05-.498a.5.5 0 01.45.498v1.153c0 .108.11.176.202.134l3.984-2.933a.494.494 0 01.042-.028.147.147 0 000-.252.494.494 0 01-.042-.028L9.502 5.013zM8.3 10.386a7.745 7.745 0 00-1.923.277c-1.326.368-2.896 1.201-3.94 3.08a.5.5 0 01-.933-.305c.464-3.71 1.886-5.662 3.46-6.66 1.245-.79 2.527-.942 3.336-.971v-.66a1.144 1.144 0 011.767-.96l3.994 2.94a1.147 1.147 0 010 1.946l-3.994 2.94a1.144 1.144 0 01-1.767-.96v-.667z" clip-rule="evenodd"/>
«««						</svg>
«««					</span>
					«node.name»
				</a>
				<div class="collapse«IF !getClass(node).contains("collapsed")» show«ENDIF»" id="collapsible«collapseCounter++»">
					<ul class="nav nav-list">
				   		«createNodeChildren(node)»
					</ul>
				</div>
			</li>
			<li class="divider"></li>
		''';
	}
	
	def String createLeaf(SubPage element) {
		return '''
			<li data-id="«element.id»">
				<a class="«getClass(element)» btn  btn-link tree-link" href="«element.url»">
					<svg width=".5em" height=".5em" viewBox="0 0 16 16" class="bi bi-circle-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
					  <circle cx="8" cy="8" r="8"/>
					</svg>
					«element.name»
				</a>
			</li>
		''';
	}
	
}