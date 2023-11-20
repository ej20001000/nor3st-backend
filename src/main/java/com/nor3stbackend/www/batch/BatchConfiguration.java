package com.nor3stbackend.www.batch;

import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public ItemReader<MemberEntity> memberItemReader() {
        return new JpaPagingItemReaderBuilder<MemberEntity>()
                .name("memberItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT m FROM MemberEntity m")
                .build();
    }

    @Bean
    public SolvedProcessor solvedProcessor() {
        return new SolvedProcessor();
    }

    @Bean
    public SolvedWriter solvedWriter() {
        return new SolvedWriter();
    }

    @Bean
    public Step solvedAssignmentStep(ItemReader<MemberEntity> memberEntityItemReader,
                                     SolvedProcessor solvedProcessor,
                                     SolvedWriter solvedWriter) {
        return new StepBuilder("solvedAssignmentStep")
                .<MemberEntity, List<SolvedEntity>>chunk(10)
                .reader(memberEntityItemReader)
                .processor(solvedProcessor)
                .writer(solvedWriter)
                .build();
    }

    @Bean
    public Job solvedAssignmentJob(Step solvedAssignmentStep) {
        return new JobBuilder("solvedAssignmentJob")
                .incrementer(new RunIdIncrementer())
                .start(solvedAssignmentStep)
                .build();
    }


    @Scheduled(cron = "0 0 0 * * ?") // midnight every day
    public void performUserJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

    }
}
