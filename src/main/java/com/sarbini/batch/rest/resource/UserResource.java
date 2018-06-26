package com.sarbini.batch.rest.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Stopwatch;

@RestController
public class UserResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	@RequestMapping("/createUser")
	public String createUser(@RequestParam(value = "login", defaultValue = "admin") String login) {
		try {
			Stopwatch wStopwatch = Stopwatch.createStarted();
			JobParameters parameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
			jobLauncher.run(job, parameters);
			LOGGER.info(wStopwatch.toString());
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}
}
