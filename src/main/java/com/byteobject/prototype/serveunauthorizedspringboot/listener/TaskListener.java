package com.byteobject.prototype.serveunauthorizedspringboot.listener;

import org.camunda.bpm.client.task.ExternalTaskHandler;

public interface TaskListener extends ExternalTaskHandler {

    String getTopicName();

}
