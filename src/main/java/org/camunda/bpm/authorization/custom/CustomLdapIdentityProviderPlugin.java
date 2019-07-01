package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.identity.impl.ldap.plugin.LdapIdentityProviderPlugin;

public class CustomLdapIdentityProviderPlugin extends LdapIdentityProviderPlugin {

    @Override
    public void preInit(final ProcessEngineConfigurationImpl configuration) {
        super.preInit(configuration);
        final CustomLdapIdentityProviderFactory providerFactory = new CustomLdapIdentityProviderFactory();
        providerFactory.setLdapConfiguration(this);
        configuration.setIdentityProviderSessionFactory(providerFactory);
    }

}
