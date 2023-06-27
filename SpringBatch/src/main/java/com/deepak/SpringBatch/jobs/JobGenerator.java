package com.deepak.SpringBatch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class JobGenerator {
	@Autowired
	protected PlatformTransactionManager transactionManager;
	
	@Autowired
	protected JobRepository jobRepository;
	
	public abstract Job generateJobObject(); 

}
