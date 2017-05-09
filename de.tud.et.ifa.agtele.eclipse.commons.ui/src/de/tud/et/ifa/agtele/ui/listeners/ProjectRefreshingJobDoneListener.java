package de.tud.et.ifa.agtele.ui.listeners;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;

import de.tud.et.ifa.agtele.resources.ResourceHelper;

/**
 * This {@link IJobChangeListener} will refresh a project in the current workspace after a job is
 * {@link #done(IJobChangeEvent) done}. It can be used to see the results of a job that adds/modifies/deletes
 * resource in/from the workspace.
 * 
 * @author mfreund
 */
public class ProjectRefreshingJobDoneListener implements IJobChangeListener {
	
	/**
	 * The {@link IProject project} that will be refreshed when the job is done.
	 */
	private final IProject projectResource;
	
	/**
	 * This creates an instance that will refresh the project with the given '<em>projectName</em>'
	 * when the job that this is attached to is {@link #done(IJobChangeEvent) done}.
	 * 
	 * @param projectName The name of the project in the current workspace that shall be refreshed.
	 */
	public ProjectRefreshingJobDoneListener(String projectName) {
		super();
		
		this.projectResource = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
	}

	@Override
	public void done(IJobChangeEvent event) {
		
		// refresh the project to see the results of the job
		if(projectResource != null) {
			
			ResourceHelper.refresh(projectResource);
		}
	}

	@Override
	public void aboutToRun(IJobChangeEvent event) {
		// do nothing
	}

	@Override
	public void awake(IJobChangeEvent event) {
		// do nothing
	}

	@Override
	public void running(IJobChangeEvent event) {
		// do nothing
	}

	@Override
	public void scheduled(IJobChangeEvent event) {
		// do nothing
	}

	@Override
	public void sleeping(IJobChangeEvent event) {
		// do nothing
	}

}
