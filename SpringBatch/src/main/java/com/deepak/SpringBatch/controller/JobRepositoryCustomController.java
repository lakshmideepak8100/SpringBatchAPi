package com.deepak.SpringBatch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.SpringBatch.JobRepoitory.Cutomization.CustomJobRepository;
import com.deepak.SpringBatch.jobscheduler.JobScheduler;
import com.deepak.SpringBatch.report.pojos.jobHistory.JobHistory;

@RestController
@RequestMapping("/jobRepo")
@CrossOrigin(origins = "http://localhost:4200")
public class JobRepositoryCustomController {
	
	@Autowired
	private CustomJobRepository jobRepo;
	
	@Autowired
	private JobScheduler jobscheduler;
	
	@GetMapping("/jobsList")
	public List<JobHistory>  getJobsList(){
		System.out.println("Request Reached Job List ontroller");
		return jobRepo.getJobHistory();
	}
	
	@GetMapping("/runningJobList")
	public Map<String,String> getRunningJobList(){
		System.out.println("Request Reached runningJobList controller");
		return jobRepo.currentRunningJobsInfo();	
		
	}
	
	@PostMapping("/scheduleJob")
	public String scheduleJob(@RequestParam("cronExpression") String cronExpression,@RequestParam("JobName")String jobName){
		System.out.println("Reached schedule Job contoller");
		jobscheduler.scheduleJob(cronExpression, jobName);	
		return "Job Scheduled";
	
	}

}
