package com.deepak.SpringBatch.report.pojos.jobInstanceMetric;

public abstract class JobInstanceMetric {

	private int jobInstanceId;
	private String jobInstanceStatus;
	private String lastUpdated;

	public int getJobInstanceId() {
		return jobInstanceId;
	}

	public void setJobInstanceId(int jobInstanceId) {
		this.jobInstanceId = jobInstanceId;
	}

	public String getJobInstanceStatus() {
		return jobInstanceStatus;
	}

	public void setJobInstanceStatus(String jobInstanceStatus) {
		this.jobInstanceStatus = jobInstanceStatus;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
