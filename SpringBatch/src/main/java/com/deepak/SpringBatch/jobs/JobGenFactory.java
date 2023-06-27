package com.deepak.SpringBatch.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deepak.SpringBatch.customExceptions.NoJobwithSpecifiedName;

@Component
public class JobGenFactory {
	
	@Autowired
	private TransferJob transferjob;
	
	public JobGenerator jobGen(String jobName) throws NoJobwithSpecifiedName {
		switch(jobName) {
		
		case "OrganizeFileLoad":
			return transferjob;
		default:
			throw new  NoJobwithSpecifiedName("No Job with specified name present");
		}
		
	}

}
