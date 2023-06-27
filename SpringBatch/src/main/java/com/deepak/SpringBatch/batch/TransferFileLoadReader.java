package com.deepak.SpringBatch.batch;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.deepak.SpringBatch.model.User;

@Component
@Qualifier("transferFileReader")
public class TransferFileLoadReader{
	
	public FlatFileItemReader<User> itemReader(){
		FlatFileItemReader<User> csvReader = new FlatFileItemReader<>();
		csvReader.setResource(new ClassPathResource("users.csv") );
		csvReader.setName("CSV-Reader");
		csvReader.setLinesToSkip(1);
		csvReader.setLineMapper(lineMapper());	
		return csvReader;
	}
	
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
