package com.blogapp.scheduler;

import com.blogapp.repository.SeenPostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SeenPostScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(SeenPostScheduler.class);

    private final SeenPostRepository seenPostRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanOldSeenPosts(){

        LocalDateTime cutoffDate =
                LocalDateTime.now().minusDays(90);

        log.info("Cleaning Seen Posts older than {}", cutoffDate);

        seenPostRepository.deleteOldSeenPosts(cutoffDate);

        log.info("Seen Posts cleanup completed");
    }

}
