package com.github.arpit.nexus.core.automation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsyncTaskExecutor {

    /**
     * Executes a task asynchronously.
     *
     * @param taskName The name of the task for logging purposes.
     * @param task     The Runnable task to execute.
     * @return A CompletableFuture indicating completion.
     */
    @Async
    public CompletableFuture<String> execute(String taskName, Runnable task) {
        log.info("Starting async task: {}", taskName);
        long start = System.currentTimeMillis();
        try {
            task.run();
        } catch (Exception e) {
            log.error("Error executing task: {}", taskName, e);
            return CompletableFuture.failedFuture(e);
        }
        long duration = System.currentTimeMillis() - start;
        log.info("Completed async task: {} in {}ms", taskName, duration);
        return CompletableFuture.completedFuture("Task " + taskName + " completed");
    }
}