package com.byteobject.prototype.serveunauthorizedspringboot.config;

import com.byteobject.prototype.serveunauthorizedspringboot.listener.TaskListener;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:camunda.properties")
public class TaskListenerConfiguration {

    private final List<TaskListener> taskListeners;

    @Value(value = "${baseUrl}")
    private String baseUrl;

    @Value(value = "${maxTasks}")
    private int maxTasks;

    @PostConstruct
    public void init() {
        taskListeners.forEach(taskListener -> {
            externalTaskClient().subscribe(taskListener.getTopicName())
                    .handler(taskListener).open();
        });
    }

    @Bean
    public ExternalTaskClient externalTaskClient() {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(baseUrl).maxTasks(maxTasks)
                .build();

        return client;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setMaxTasks(int maxTasks) {
        this.maxTasks = maxTasks;
    }

}
