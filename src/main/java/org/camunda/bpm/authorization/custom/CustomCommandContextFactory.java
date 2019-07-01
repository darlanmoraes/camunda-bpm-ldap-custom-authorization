package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandContextFactory;

public class CustomCommandContextFactory extends CommandContextFactory {

    @Override
    public CommandContext createCommandContext() {
        return new CustomCommandContext(super.getProcessEngineConfiguration());
    }

}
