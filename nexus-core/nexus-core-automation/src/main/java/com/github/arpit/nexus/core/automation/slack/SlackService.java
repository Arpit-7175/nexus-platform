package com.github.arpit.nexus.core.automation.slack;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Environment environment;
    private final Map<SlackChannel, String> webhookUrls = new EnumMap<>(SlackChannel.class);

    @PostConstruct
    public void init() {
        for (SlackChannel channel : SlackChannel.values()) {
            String propertyKey = "nexus.automation.slack.webhooks." + channel.name();
            String url = environment.getProperty(propertyKey);
            if (url != null && !url.isEmpty()) {
                webhookUrls.put(channel, url);
            }
        }
    }

    public void sendAlert(SlackChannel channel, String message) {
        String url = webhookUrls.get(channel);
        if (url == null) {
            log.warn("No webhook URL configured for Slack channel: {}. Skipping alert.", channel);
            return;
        }

        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("text", message);

            restTemplate.postForEntity(url, payload, String.class);
            log.info("Slack alert sent to {}: {}", channel, message);
        } catch (Exception e) {
            log.error("Failed to send Slack alert to {}", channel, e);
        }
    }

    // Overload for backward compatibility or default channel
    public void sendAlert(String message) {
        // Default to GENERAL if available, otherwise try to find any configured channel
        if (webhookUrls.containsKey(SlackChannel.GENERAL)) {
            sendAlert(SlackChannel.GENERAL, message);
        } else if (!webhookUrls.isEmpty()) {
            SlackChannel firstAvailable = webhookUrls.keySet().iterator().next();
            sendAlert(firstAvailable, message);
        } else {
            log.warn("No Slack webhook URLs configured. Skipping alert.");
        }
    }
}