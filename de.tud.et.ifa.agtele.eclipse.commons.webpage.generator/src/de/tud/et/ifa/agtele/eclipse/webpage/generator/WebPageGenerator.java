package de.tud.et.ifa.agtele.eclipse.webpage.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.et.ifa.agtele.eclipse.webpage.webpagemodel.WebPage;

public class WebPageGenerator  extends Job  {
	protected ExecutionEvent event;
	protected List<Exception> errors;
	
	protected String outDirectory = null;
	
	protected ArrayList<WebPage> pages = new ArrayList<>();
	
	public WebPageGenerator() {
		super("Web Page Generator");
	}
	
	public void addWebPage(WebPage page) {
		this.pages.add(page);
	}
		
	public boolean isEmpty() {
		return this.pages.isEmpty();
	}
	
	public boolean isGeneratable() {
		return !this.isEmpty();
	}
	protected void updateStatusLine () {
		IActionBars actionBars = HandlerUtil.getActiveEditor(this.event).getEditorSite().getActionBars();
		if (actionBars != null) {
			IStatusLineManager statusLineManager = actionBars.getStatusLineManager();
			if (statusLineManager != null) {
				if (this.errors.isEmpty()) {
					statusLineManager.setErrorMessage(null);
					statusLineManager.setMessage("Web Page Generation completed successfully.");
				} else {
					statusLineManager
					.setErrorMessage(Integer.toString(this.errors.size())
							+ " Errors occurred during web page generation.");
				}
			}
		}
	}

	protected void spawnErrorDialog() {
		ErrorDialog.openError(
				HandlerUtil.getActiveEditor(this.event).getEditorSite().getWorkbenchWindow().getShell(), "Error(s)",
				Integer.toString(this.errors.size()) + " Errors occurred during Web Page Generation",
				this.getErrorStatus(this.errors));
	}

	protected MultiStatus getErrorStatus(List<Exception> errors) {
		MultiStatus[] errorStati = this.convertErrorsToStatusArray(errors);

		if (errors.size() == 1) {
			return errorStati[0];
		}

		return new MultiStatus("de.tud.et.ifa.agtele.commons.webpage.generator", IStatus.ERROR, errorStati,
				"Exceptions occurred web page generation", new Exception("Errors occurred during Web Page Generation"));

	}

	protected MultiStatus[] convertErrorsToStatusArray(List<Exception> errors) {
		MultiStatus[] result = new MultiStatus[errors.size()];

		for (Exception e : errors) {
			List<Status> childStatuses = new ArrayList<>();
			StackTraceElement[] stackTraces = e.getStackTrace();

			for (StackTraceElement stackTrace : stackTraces) {
				Status status = new Status(IStatus.ERROR, "de.tud.et.ifa.agtele.commons.webpage.generator", stackTrace.toString());
				childStatuses.add(status);
			}
			result[errors.indexOf(e)] = new MultiStatus("de.tud.et.ifa.agtele.commons.webpage.generator", IStatus.ERROR,
					childStatuses.toArray(new Status[] {}), e.toString(), e);
		}

		return result;
	}

	public void start(ExecutionEvent event) {
		this.event = event;
		this.schedule();
	}
	
	protected void generate () {
		//TODO 		
	}
	
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		this.generate();
		
		try {
			if (this.event != null) {
				HandlerUtil.getActiveEditor(this.event).getEditorSite().getWorkbenchWindow().getShell().getDisplay()
				.asyncExec(() -> {
					try {
						this.updateStatusLine();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (!this.errors.isEmpty()) {
						try {
							this.spawnErrorDialog();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return this.errors.isEmpty() ? Status.OK_STATUS : Status.CANCEL_STATUS;
	}
}
