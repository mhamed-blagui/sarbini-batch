package com.sarbini.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.sarbini.batch.domain.User;
import com.sarbini.batch.processor.UserProcessor;
import com.sarbini.batch.reader.UserReader;
import com.sarbini.batch.writer.UserWriter;

@Configuration
@EnableBatchProcessing
public class SarbiniJob {

	@Value("${max.threads}")
	private int maxThreads;
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	ItemReader<User> read() {
		return new UserReader();
	}

	@Bean
	ItemWriter<User> write() {
		return new UserWriter();
	}

	@Bean
	ItemProcessor<User, User> process() {
		return new UserProcessor();
	}

	@Bean
    public Job job() {
        return jobBuilderFactory.get("job")
        		.incrementer(new RunIdIncrementer())
        		.preventRestart()
                .flow(step1())
                .end()
                .build();
    }
 
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<User, User> chunk(100)
                .reader(read())
                .processor(process())
                .writer(write())
                .taskExecutor(taskExecutor())
                .build();
    }
    
    @Bean
    public TaskExecutor taskExecutor() {
    	SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
    	taskExecutor.setConcurrencyLimit(maxThreads);
    	return taskExecutor;
    }
}