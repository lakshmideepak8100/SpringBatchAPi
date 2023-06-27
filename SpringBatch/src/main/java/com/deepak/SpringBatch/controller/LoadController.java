package com.deepak.SpringBatch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.SpringBatch.Repository.UserRepository;
import com.deepak.SpringBatch.model.User;

@RestController
@RequestMapping("/load")
public class LoadController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	JobLauncher jobLauncher; // provided by spring boot
	
	@Autowired
	Job EtlJob;
	
	@SuppressWarnings("unchecked")
	@GetMapping
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		Map<String, JobParameter<?> >maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis(), Long.class, false));
		JobParameters parameters = new JobParameters(maps);
		jobLauncher.run(EtlJob,parameters );
		
		JobExecution jobExecution = jobLauncher.run(EtlJob,parameters);
		
		while(jobExecution.isRunning()) {
			System.out.println("Batch is Running....");
		}
		
		return jobExecution.getStatus();
		
	}
	
	@PostMapping
	public User create(@RequestBody User user) {
		System.out.println(user);
		return userRepo.save(user);
		
	}
	

}
