package com.byteobject.prototype.serveunauthorizedspringboot.listener;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class AddAuthorizationListener implements TaskListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddAuthorizationListener.class);

    @Override
    public String getTopicName() {
        return "AddAuthorizationTopic";
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        LOGGER.info("logging for task execution id {} from thread {}", externalTask.getExecutionId(), Thread.currentThread().getName());
        LOGGER.info("logging for task activity id {}", externalTask.getActivityId());
        externalTask.getAllVariables().forEach((key, val) -> LOGGER.info("key: {}, value: {}", key, val));

        Random random = new Random();

        Map<String, Object> valuesToPass = new HashMap<>();
        ObjectValue testValues = Variables.objectValue(Arrays.asList(1, 2, 3)).serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();
        valuesToPass.put("authorized", random.nextBoolean());
        valuesToPass.put("testVal", testValues);
        externalTaskService.complete(externalTask, valuesToPass);
    }
}
