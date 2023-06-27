package com.deepak.SpringBatch.jobscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {


    
    @Autowired
    private TaskScheduler taskScheduler;
    
    @Autowired
    private GenericJobRunner genericRunner;
    
    public void scheduleJob(String cronExpression,String jobName) {
    	System.out.println("Inside Scheduler");
    	genericRunner.setJobName(jobName);
        taskScheduler.schedule(genericRunner, new CronTrigger(cronExpression) );
        System.out.println("Exiting Scheduler");
        
    }
    

}
