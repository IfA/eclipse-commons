package de.tud.et.ifa.agtele.eclipse.webpage.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.WebPageGenerator;
import de.tud.et.ifa.agtele.emf.AgteleEcoreUtil;
import de.tud.et.ifa.agtele.ui.util.UIHelper;

public class GenerateWebPageCommandHandler extends AbstractHandler {
	
	static protected WebPageGenerator currentGenerator = null;
	
	static public boolean onlySingleInstances = true;
	
	@Override
	public synchronized Object execute(ExecutionEvent event) throws ExecutionException {
		final WebPageGenerator generator = currentGenerator;
		
		if (generator == null) {
			return null;
		}
		
		generator.setPriority(Job.INTERACTIVE);
		generator.setUser(true);
		UIHelper.getCurrentEditor().getEditorSite().getActionBars().getStatusLineManager()
			.setErrorMessage(null);
		
		if (generator.isGeneratable()) {
			generator.start(event);			
		}
		generator.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				super.done(event);
				if (GenerateWebPageCommandHandler.onlySingleInstances) {
					GenerateWebPageCommandHandler.currentGenerator = null;
				}
			}
			
		});
		
		return null;
	}

	@Override
	public boolean isEnabled() {
		if (onlySingleInstances && currentGenerator != null) {
			return false;
		}
		currentGenerator = new WebPageGenerator();
		
		return this.evaluateSelection(currentGenerator);
	}
	
	public boolean evaluateSelection (WebPageGenerator generator) {
		ISelection select = UIHelper.getCurrentSelection();
		IStructuredSelection selection = select instanceof IStructuredSelection ? (IStructuredSelection) select : null;
		
		if (selection != null) {
			for (Object o : selection) {
				if (o instanceof WebPage) {
					generator.addWebPage((WebPage) o);
				} 
			}
		}
		
		return currentGenerator.isGeneratable();
	}
}
