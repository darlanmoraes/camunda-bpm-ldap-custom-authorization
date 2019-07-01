package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.identity.impl.ldap.LdapIdentityProviderFactory;

public class CustomLdapIdentityProviderFactory extends LdapIdentityProviderFactory {

    @Override
    public Session openSession() {
        return new CustomLdapIdentityProviderSession(this.getLdapConfiguration());
    }

}
