package com.deepak.SpringBatch.jobscheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.deepak.SpringBatch.customExceptions.NoJobwithSpecifiedName;
import com.deepak.SpringBatch.jobs.JobGenFactory;
import com.deepak.SpringBatch.jobs.JobGenerator;

@Component
@Scope("prototype")
public class GenericJobRunner implements Runnable {
	
	private String jobName;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobGenFactory jobGenFactory;
    
	@Override
	public void run() {
		
		System.out.println("Inside Generic Runner");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("batch-context.xml");
		
		
		try {
			JobGenerator jobToRun = (JobGenerator) jobGenFactory.jobGen(jobName);
			Job genericJob = jobToRun.generateJobObject();
		 JobParameters jobParameters = new JobParametersBuilder()
	                .addString("jobName",genericJob.getName() )
	                .addString("TimeofExecution", "time"+System.currentTimeMillis())
	                .toJobParameters();

				JobExecution jobExecution = jobLauncher.run(genericJob, jobParameters);
			} catch (JobExecutionAlreadyRunningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (JobRestartException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobInstanceAlreadyCompleteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JobParametersInvalidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoJobwithSpecifiedName e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		finally {
			context.close();
		}
		System.out.println("Exiting Generic Runner");
		
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
