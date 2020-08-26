package de.tud.et.ifa.agtele.eclipse.webpage.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;
import de.tud.et.ifa.agtele.eclipse.webpage.generator.WebPageGenerator;
import de.tud.et.ifa.agtele.eclipse.webpage.util.GenerateJob;
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
		GenerateJob job = generator.getJob();
		job.setPriority(Job.INTERACTIVE);
		job.setUser(true);
		UIHelper.getCurrentEditor().getEditorSite().getActionBars().getStatusLineManager()
			.setErrorMessage(null);
		
		if (generator.isGeneratable()) {
			job.start(event);			
		}
		job.addJobChangeListener(new JobChangeAdapter() {
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
		if (onlySingleInstances && currentGenerator != null && currentGenerator.getJob().isRunning()) {
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
					for (WebPage p : ((WebPage)o).getAllAlternativePages()) {
						generator.addWebPage(p);
					}
				}
				if (o instanceof XMIResource) {
					List<EObject> contents = ((XMIResource)o).getContents();
					if (!contents.stream().anyMatch(e -> !(e instanceof WebPage))) {
						for (EObject e : contents) {
							generator.addWebPage((WebPage)e);
						}
					}
				}
			}
		}
		
		return currentGenerator.isGeneratable();
	}
	
}
