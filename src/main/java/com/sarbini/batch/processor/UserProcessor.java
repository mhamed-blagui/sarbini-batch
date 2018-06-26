package com.sarbini.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.sarbini.batch.domain.User;

public class UserProcessor implements ItemProcessor<User, User>{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProcessor.class);

	private Long counter = 200000L;
	@Override
	public User process(User user) throws Exception {
		User newUser = new User(counter, user.getLogin(), 
				user.getPassword(), user.getFirstName(), user.getFirstName(), user.getEmail());
		counter = counter + 1L;
		return newUser;
	}

}
