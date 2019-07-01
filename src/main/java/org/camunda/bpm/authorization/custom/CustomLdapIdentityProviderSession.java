package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.identity.impl.ldap.LdapConfiguration;
import org.camunda.bpm.identity.impl.ldap.LdapIdentityProviderSession;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public class CustomLdapIdentityProviderSession extends LdapIdentityProviderSession {

    public CustomLdapIdentityProviderSession(final LdapConfiguration configuration) {
        super(configuration);
    }

    @Override
    public CustomLdapUserEntity transformUser(final SearchResult result) throws NamingException {
        final Attributes attributes = result.getAttributes();
        final CustomLdapUserEntity user = new CustomLdapUserEntity();
        user.setDn(result.getNameInNamespace());
        user.setId(getStringAttributeValue(ldapConfiguration.getUserIdAttribute(), attributes));
        user.setFirstName(getStringAttributeValue(ldapConfiguration.getUserFirstnameAttribute(), attributes));
        user.setLastName(getStringAttributeValue(ldapConfiguration.getUserLastnameAttribute(), attributes));
        user.setEmail(getStringAttributeValue(ldapConfiguration.getUserEmailAttribute(), attributes));
        return user;
    }

}
