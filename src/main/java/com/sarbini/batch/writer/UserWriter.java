package com.sarbini.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.sarbini.batch.domain.User;
import com.sarbini.batch.repository.UserRepository;

public class UserWriter implements ItemWriter<User> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserWriter.class);

	@Autowired
	private UserRepository repository;
	
	@Override
	public void write(List<? extends User> userList) throws Exception {
		for(User user : userList){
			repository.save(user);	
		}
	}

}
