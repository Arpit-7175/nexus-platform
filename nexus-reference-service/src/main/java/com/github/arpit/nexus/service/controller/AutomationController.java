package com.github.arpit.nexus.service.controller;

import com.github.arpit.nexus.core.automation.AsyncTaskExecutor;
import com.github.arpit.nexus.core.automation.slack.SlackChannel;
import com.github.arpit.nexus.core.automation.slack.SlackService;
import com.github.arpit.nexus.core.web.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
@Slf4j
public class AutomationController {

    private final SlackService slackService;
    private final AsyncTaskExecutor asyncTaskExecutor;

    @PostMapping("/slack")
    public ApiResponse<String> sendSlackAlert(@RequestParam String message, @RequestParam(defaultValue = "GENERAL") SlackChannel channel) {
        slackService.sendAlert(channel, message);
        return ApiResponse.success("Alert sent to " + channel, "Success");
    }

    @PostMapping("/task")
    public ApiResponse<String> triggerTask(@RequestParam String taskName) {
        asyncTaskExecutor.execute(taskName, () -> {
            log.info("Executing background task: {}", taskName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.info("Background task finished: {}", taskName);
        });
        return ApiResponse.success("Task submitted", "Success");
    }
}