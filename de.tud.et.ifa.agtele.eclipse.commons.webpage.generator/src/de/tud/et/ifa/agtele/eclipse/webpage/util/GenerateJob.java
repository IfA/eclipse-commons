package de.tud.et.ifa.agtele.eclipse.webpage.util;

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

import de.tud.et.ifa.agtele.eclipse.webpage.generator.WebPageGenerator;

public class GenerateJob extends Job {
	protected AbstractGenerator generator;
	protected ExecutionEvent event;
	protected List<Exception> errors = new ArrayList<>();
	protected String jobName = null;
	protected String bundleId = null;
	protected boolean isRunning = false;

	public GenerateJob(String name, AbstractGenerator generator, String bundleId) {
		super(name);
		this.jobName = name;
		this.bundleId = bundleId;
		this.generator = generator;
	}

	public boolean isRunning() {
		return this.isRunning;
	}
	
	protected void updateStatusLine () {
		IActionBars actionBars = HandlerUtil.getActiveEditor(this.event).getEditorSite().getActionBars();
		if (actionBars != null) {
			IStatusLineManager statusLineManager = actionBars.getStatusLineManager();
			if (statusLineManager != null) {
				if (this.errors.isEmpty()) {
					statusLineManager.setErrorMessage(null);
					statusLineManager.setMessage("'" + this.jobName +"' completed successfully.");
				} else {
					statusLineManager
					.setErrorMessage(Integer.toString(this.errors.size())
							+ " Errors occurred during '" + this.jobName + "'.");
				}
			}
		}
	}

	protected void spawnErrorDialog() {
		ErrorDialog.openError(
				HandlerUtil.getActiveEditor(this.event).getEditorSite().getWorkbenchWindow().getShell(), "Error(s)",
				Integer.toString(this.errors.size()) + " Errors occurred during '" + this.jobName +"'",
				this.getErrorStatus(this.errors));
	}

	protected MultiStatus getErrorStatus(List<Exception> errors) {
		MultiStatus[] errorStati = this.convertErrorsToStatusArray(errors);

		if (errors.size() == 1) {
			return errorStati[0];
		}

		return new MultiStatus(this.bundleId, IStatus.ERROR, errorStati,
				"Exceptions occurred during '" + this.jobName +"'", new Exception("Errors occurred during '" + this.jobName +"'"));

	}

	protected MultiStatus[] convertErrorsToStatusArray(List<Exception> errors) {
		MultiStatus[] result = new MultiStatus[errors.size()];

		for (Exception e : errors) {
			List<Status> childStatuses = new ArrayList<>();
			StackTraceElement[] stackTraces = e.getStackTrace();

			for (StackTraceElement stackTrace : stackTraces) {
				Status status = new Status(IStatus.ERROR, this.bundleId, stackTrace.toString());
				childStatuses.add(status);
			}
			result[errors.indexOf(e)] = new MultiStatus(this.bundleId, IStatus.ERROR,
					childStatuses.toArray(new Status[] {}), e.toString(), e);
		}

		return result;
	}

	public void start(ExecutionEvent event) {
		this.event = event;
		this.isRunning = true;
		this.schedule();
	}
	

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		this.generator.generate(monitor);
		
		this.errors.addAll(this.generator.getErrors());
		
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