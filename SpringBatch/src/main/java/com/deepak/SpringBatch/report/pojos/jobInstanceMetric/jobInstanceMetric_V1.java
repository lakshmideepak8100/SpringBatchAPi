package com.deepak.SpringBatch.report.pojos.jobInstanceMetric;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Qualifier("jobInstanceMetric_V1")
public class jobInstanceMetric_V1 extends JobInstanceMetric{
	
	
}
