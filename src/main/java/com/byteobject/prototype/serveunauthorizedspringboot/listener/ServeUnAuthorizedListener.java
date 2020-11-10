package com.byteobject.prototype.serveunauthorizedspringboot.listener;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("serveUnAuthorizedListener")
public class ServeUnAuthorizedListener implements TaskListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServeUnAuthorizedListener.class);

    @Override
    public String getTopicName() {
        return "ServeUnAuthorizedTopic";
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        LOGGER.info("logging for task execution id {} from thread {}", externalTask.getExecutionId(), Thread.currentThread().getName());
        LOGGER.info("logging for task activity id {}", externalTask.getActivityId());
        externalTask.getAllVariables().forEach((key, val) -> LOGGER.info("key: {}, value: {}", key, val));
        externalTaskService.complete(externalTask);
    }
}
