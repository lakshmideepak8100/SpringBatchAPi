package com.deepak.SpringBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.deepak.SpringBatch.model.User;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = "com.deepak.SpringBatch")
@CrossOrigin(origins = "*")
public class SpringBatchConfig {
	@Bean
	@Qualifier("EtlLoad")
	public Job EtlLoad(JobRepository jobRepository, Step FileLoad) {
	    return new JobBuilder("EtlLoad", jobRepository)
	                .start(FileLoad)
	                .build();
	}

	
	@Bean
	public Step FileLoad(JobRepository jobRepository, PlatformTransactionManager transactionManager,ItemReader<User> itemReader,ItemProcessor<User, User> itemProcessor ,ItemWriter<User> itemWriter) {
		return new StepBuilder("FileLoad", jobRepository)
					.<User, User>chunk(10, transactionManager)
					.reader(itemReader)
					.processor(itemProcessor)
					.writer(itemWriter)
					.build();
	}
	
	
	
	@Bean
	public FlatFileItemReader<User> itemReader(){
		FlatFileItemReader<User> csvReader = new FlatFileItemReader<>();
		csvReader.setResource(new ClassPathResource("users.csv") );
		csvReader.setName("CSV-Reader");
		csvReader.setLinesToSkip(1);
		csvReader.setLineMapper(lineMapper());	
		return csvReader;
	}
	
	@Bean
	public LineMapper<User> lineMapper(){
		
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer= new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"id","name","dept","salary"});
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
		
		
	}
	
	
	
	
		
}
