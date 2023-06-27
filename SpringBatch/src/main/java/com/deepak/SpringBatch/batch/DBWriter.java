package com.deepak.SpringBatch.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.deepak.SpringBatch.Repository.UserRepository;
import com.deepak.SpringBatch.model.User;

@Component
@Qualifier("dbWriter")
public class DBWriter implements ItemWriter<User>  {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void write(Chunk<? extends User> users) throws Exception {
		System.out.println("Data Saved for Users:"+users);
		userRepository.saveAll(users);
		
	}
	
	
	

}
