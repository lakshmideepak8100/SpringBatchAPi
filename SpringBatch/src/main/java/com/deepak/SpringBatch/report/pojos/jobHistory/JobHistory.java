package com.deepak.SpringBatch.report.pojos.jobHistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deepak.SpringBatch.report.pojos.jobInstanceMetric.JobInstanceMetric;

public abstract class JobHistory {
	private String JobTitle;
	private List<JobInstanceMetric> jobInstancedata;
	private String jobDescription;
	

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}


	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public List<JobInstanceMetric> getJobInstancedata() {
		return jobInstancedata;
	}

	public void setJobInstancedata(List<JobInstanceMetric> jobInstancedata) {
		this.jobInstancedata = jobInstancedata;
	}


}
