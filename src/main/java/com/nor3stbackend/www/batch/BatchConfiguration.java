package com.nor3stbackend.www.batch;

import com.nor3stbackend.www.member.command.domain.aggregate.MemberEntity;
import com.nor3stbackend.www.solved.command.domain.aggregate.SolvedEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
public class BatchConfiguration {


    private final JobRepository jobRepository;
    private final JobLauncher jobLauncher;
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;


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
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .<MemberEntity, List<SolvedEntity>>chunk(10)
                .reader(memberEntityItemReader)
                .processor(solvedProcessor)
                .writer(solvedWriter)
                .build();
    }

    @Bean
    public Job solvedAssignmentJob(Step solvedAssignmentStep) {
        return new JobBuilder("solvedAssignmentJob")
                .repository(jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(solvedAssignmentStep)
                .build();
    }

    @Scheduled(cron = "0 0 0 * * ?") // midnight every day
    public void performUserJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("datetime", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();

            jobLauncher.run(solvedAssignmentJob(solvedAssignmentStep(memberItemReader(), solvedProcessor(), solvedWriter())), jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
