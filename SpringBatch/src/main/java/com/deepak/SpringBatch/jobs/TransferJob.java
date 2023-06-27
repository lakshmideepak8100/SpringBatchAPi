package com.deepak.SpringBatch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.deepak.SpringBatch.steps.transferJob.FileLoad;

@Component
public class TransferJob extends JobGenerator{
	
	
	private FileLoad fileLoad;
	
	public TransferJob (FileLoad fileLoad) {
		this.fileLoad=fileLoad;
	}


	@Override
	public Job generateJobObject() {
		return new JobBuilder("OrganizeLoad", super.jobRepository)
        .start(this.fileLoad.FileLoader(jobRepository, super.transactionManager))
        .build();
		
		
	}


}
