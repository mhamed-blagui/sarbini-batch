package com.sarbini.batch.reader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.sarbini.batch.domain.User;
import com.sarbini.batch.repository.UserRepository;

public class UserReader implements ItemReader<User> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserReader.class);
	
	private List<User> userData;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(!getUserData().isEmpty()){
			return userData.remove(0);
		}
		return null;
	}

	public List<User> getUserData() {
		if(userData ==null){
			userData = repository.findAll();
		}
		return userData;
	}

}
