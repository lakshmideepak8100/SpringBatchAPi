package com.deepak.SpringBatch.steps.transferJob;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.deepak.SpringBatch.batch.TransferFileLoadReader;
import com.deepak.SpringBatch.model.User;

@Component
public class FileLoad {
	
	@Autowired
	@Qualifier("transferFileReader")
	private TransferFileLoadReader itemReader;
	@Autowired
	@Qualifier("userProcessor")
	private ItemProcessor<User, User> itemProcessor ;
	@Autowired
	@Qualifier("dbWriter")
	private ItemWriter<User> itemWriter;

	@Autowired
	public Step FileLoader(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("FileLoad", jobRepository)
					.<User, User>chunk(10, transactionManager)
					.reader(this.itemReader.itemReader())
					.processor(this.itemProcessor)
					.writer(this.itemWriter)
					.build();
	}
	
	
}
