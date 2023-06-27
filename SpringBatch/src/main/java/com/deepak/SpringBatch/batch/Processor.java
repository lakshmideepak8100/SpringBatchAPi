package com.deepak.SpringBatch.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.deepak.SpringBatch.model.User;

@Component
@Qualifier("userProcessor")
public class Processor implements ItemProcessor<User, User>{

	private static final Map<String,String> DEPT_NAMES=new HashMap<>();
	
	
	public Processor() {
		DEPT_NAMES.put("001A","Technology");
		DEPT_NAMES.put("002B","Operations");
		DEPT_NAMES.put("003C","Management");
		
	}
	
	
	@Override
	public User process(User user) throws Exception {	
		String dept_code= user.getDept();
		String dept =DEPT_NAMES.get(dept_code);
		Thread.sleep(1000);
		user.setDept(dept);
		System.out.println(String.format("Converted [%s] to [%s]",dept_code,dept));
		return user;
	}

}
