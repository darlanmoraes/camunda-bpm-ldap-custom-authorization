package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.identity.impl.ldap.LdapConfiguration;
import org.camunda.bpm.identity.impl.ldap.LdapGroupEntity;
import org.camunda.bpm.identity.impl.ldap.LdapIdentityProviderSession;
import org.camunda.bpm.identity.impl.ldap.LdapUserEntity;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public class CustomLdapIdentityProviderSession extends LdapIdentityProviderSession {

    public CustomLdapIdentityProviderSession(final LdapConfiguration configuration) {
        super(configuration);
    }

    @Override
    public CustomLdapUserEntity transformUser(final SearchResult result) throws NamingException {
        final LdapUserEntity entity = super.transformUser(result);
        return CustomLdapUserEntity.ofEntity(entity);
    }

    @Override
    protected LdapGroupEntity transformGroup(final SearchResult result) throws NamingException {
        final GroupEntity entity = super.transformGroup(result);
        final CustomLdapGroupEntity custom = CustomLdapGroupEntity.ofEntity(entity);
        final Attributes attributes = result.getAttributes();
        custom.setDn(getStringAttributeValue("dn", attributes));
        custom.setDescription(getStringAttributeValue("description", attributes));
        return custom;
    }
}
