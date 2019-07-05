package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationManager;

public class CustomCommandContext extends CommandContext {

    private AuthorizationManager authorizationManager = new CustomAuthorizationManager();

    public CustomCommandContext(final ProcessEngineConfigurationImpl configuration) {
        super(configuration);
    }

    @Override
    public AuthorizationManager getAuthorizationManager() {
        return this.authorizationManager;
    }
}