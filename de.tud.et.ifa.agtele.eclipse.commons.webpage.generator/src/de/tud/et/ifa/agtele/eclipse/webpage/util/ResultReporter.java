package de.tud.et.ifa.agtele.eclipse.webpage.util;

import java.util.ArrayList;
import java.util.List;

public abstract class ResultReporter {
	protected List<Exception> errors = new ArrayList<>();
	protected List<String> reports = new ArrayList<>();
	
	public boolean hasErrors () {
		return !this.errors.isEmpty();
	}
	public boolean hasReports () {
		return !this.reports.isEmpty();
	}
	
	public List<Exception> getErrors () {
		return new ArrayList<>(this.errors);
	}
	
	public List<String> getReports () {
		return new ArrayList<>(this.reports);
	}
	
	public void addError(Exception error) {
		this.errors.add(error);
		this.onError(error);
	}
	
	public void addError(List<Exception> errors) {
		for (Exception e : errors) {
			this.addError(e);
		}
	}
	
	public void addReport(String report) {
		this.reports.add(report);
	}
	
	public void addReport(List<String> reports) {
		this.reports.addAll(reports);
	}
	
	public void resetResults() {
		this.errors.clear();
		this.reports.clear();
	}
	
	public void includeResults(ResultReporter reporter) {
		this.addError(reporter.getErrors());
		this.addReport(reporter.getReports());
	}
	
	protected void onError(Exception e) {}
}
