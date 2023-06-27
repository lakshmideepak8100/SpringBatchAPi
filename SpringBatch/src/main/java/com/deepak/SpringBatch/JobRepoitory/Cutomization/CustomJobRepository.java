package com.deepak.SpringBatch.JobRepoitory.Cutomization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.deepak.SpringBatch.report.pojos.jobHistory.JobHistory;
import com.deepak.SpringBatch.report.pojos.jobHistory.JobHistroy_V1;
import com.deepak.SpringBatch.report.pojos.jobInstanceMetric.JobInstanceMetric;

@Component
public class CustomJobRepository extends JobRepositoryFactoryBean {
	
	private DataSource dataSource;
	private Connection con;


	@Autowired
	public CustomJobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws SQLException {
		this.dataSource = dataSource;
		setDataSource(dataSource);
		setTransactionManager(transactionManager);
		this.con=this.dataSource.getConnection();
	}

	public synchronized List<JobHistory> getJobHistory() {
		System.out.println("Inside get JobHistroy method");
		PreparedStatement jobstmt = null;
		ResultSet jobResultSet = null;
		List<JobHistory> jobDataList = new LinkedList<JobHistory>();

		try {
			con = dataSource.getConnection();
			jobstmt = this.con.prepareStatement(
					"select JOB_NAME, group_concat(JOB_INSTANCE_ID) as instanceIdgroup from batch_job_instance group by JOB_NAME");
			jobResultSet = jobstmt.executeQuery();
			while (jobResultSet.next()) {
				 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("batch-context.xml");
					JobHistory jobData = (JobHistory) context.getBean("JobHistory");
				jobData.setJobTitle(jobResultSet.getString("JOB_NAME"));
				String[] instanceIdList = jobResultSet.getString(2).split(",");
				jobData.setJobInstancedata(this.jobInstancedataListBuilder(instanceIdList,context));
				jobData.setJobDescription("Yet to be included");			
				jobDataList.add(jobData);
				// jobList.put(rs.getString("JOB_NAME"), rs.getInt("reps"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (jobstmt != null) {
					jobstmt.close();
				}
				if (jobResultSet != null) {
					jobResultSet.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		System.out.println("Exiting get JobHistroy method");
		return jobDataList;

	}

	public synchronized List<JobInstanceMetric> jobInstancedataListBuilder(String[] instanceIdList,ClassPathXmlApplicationContext context)
			{
		System.out.println("Inside jobInstancedataListBuilder ");
		PreparedStatement jobInstancestmt = null;
		ResultSet jobInstanceResultSet = null;
		StringBuilder instanceStmntBuilder = new StringBuilder();
		List<JobInstanceMetric> jobInstanceList = new LinkedList<JobInstanceMetric>();
		
		instanceStmntBuilder.append("select JOB_INSTANCE_ID,STATUS,LAST_UPDATED\r\n"
				+ "from (select JOB_INSTANCE_ID,STATUS,LAST_UPDATED from batch_job_execution where JOB_INSTANCE_ID In ( ");

		for (String instanceid : instanceIdList) {
			instanceStmntBuilder.append(instanceid + ",");
		}
		instanceStmntBuilder.deleteCharAt(instanceStmntBuilder.length() - 1);
		instanceStmntBuilder.append(") )jb\r\n" + "order by jb.LAST_UPDATED desc limit");
		instanceStmntBuilder.append(" "+instanceIdList.length);
		System.out.println("Sql Statement Builder" + instanceStmntBuilder.toString());
		
		try {
		jobInstancestmt = this.con.prepareStatement(instanceStmntBuilder.toString());
		jobInstanceResultSet=jobInstancestmt.executeQuery();

		while(jobInstanceResultSet.next()) {
			JobInstanceMetric jobInstanceData = (JobInstanceMetric) context.getBean("JobInstanceMetric");
			jobInstanceData.setJobInstanceId(jobInstanceResultSet.getInt(1));
			jobInstanceData.setJobInstanceStatus(jobInstanceResultSet.getString(2));
			jobInstanceData.setLastUpdated(jobInstanceResultSet.getString(3));
			jobInstanceList.add(jobInstanceData);
				
			}
		}
		catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				if(jobInstancestmt!=null) {
					jobInstancestmt.close();
				}
				if(jobInstanceResultSet!=null) {
					jobInstanceResultSet.close();
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Exiting jobInstancedataListBuilder ");

		return jobInstanceList;

	}
	
	public Map<String,String> currentRunningJobsInfo(){
		System.out.println("Inside CurrentRunningJobs method");
		PreparedStatement runningJobstmt = null;
		ResultSet runingJobStmtResultSet = null;
		Map<String,String> currentRunningJobs = new HashMap<>();
		
		try {
			runningJobstmt=this.con.prepareStatement("select batchInstance.JOB_NAME ,jobExecution.CREATE_TIME\r\n"
					+ "from (select distinct(JOB_INSTANCE_ID),CREATE_TIME from batch_job_execution where STATUS =\"STARTED\" or STATUS =\"FAILED\" ) jobExecution \r\n"
					+ "join batch_job_instance batchInstance On jobExecution.JOB_INSTANCE_ID=batchInstance.JOB_INSTANCE_ID;");
			runingJobStmtResultSet=runningJobstmt.executeQuery();
			while(runingJobStmtResultSet.next()) {
				currentRunningJobs.put(runingJobStmtResultSet.getString(1), runingJobStmtResultSet.getString(2));
			}
			
			System.out.println("Exiting CurrentRunningJobs method");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			try {
				if(runningJobstmt!=null) {
					runningJobstmt.close();
				}
				if(runingJobStmtResultSet!=null) {
					runingJobStmtResultSet.close();
				}
				
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		return currentRunningJobs;
		
		
	}
	
	

}
