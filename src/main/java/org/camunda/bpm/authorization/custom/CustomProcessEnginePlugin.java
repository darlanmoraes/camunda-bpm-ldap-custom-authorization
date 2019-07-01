package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.interceptor.CommandContextFactory;
import org.camunda.bpm.engine.impl.persistence.StrongUuidGenerator;

public class CustomProcessEnginePlugin implements ProcessEnginePlugin {

    @Override
    public void preInit(final ProcessEngineConfigurationImpl configuration) {
        final CommandContextFactory contextFactory = new CustomCommandContextFactory();
        contextFactory.setProcessEngineConfiguration(configuration);
        configuration.setCommandContextFactory(contextFactory);
        configuration.setIdGenerator(new StrongUuidGenerator());
        configuration.setAuthorizationEnabled(true);
    }

    @Override
    public void postInit(final ProcessEngineConfigurationImpl configuration) { }

    @Override
    public void postProcessEngineBuild(final ProcessEngine engine) { }

}
