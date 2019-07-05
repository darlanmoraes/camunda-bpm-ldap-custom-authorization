package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.identity.impl.ldap.LdapUserEntity;

// This can be used to load additional attributes
public class CustomLdapUserEntity extends LdapUserEntity {

    public static final CustomLdapUserEntity ofEntity(final LdapUserEntity entity) {
        final CustomLdapUserEntity custom = new CustomLdapUserEntity();
        custom.setId(entity.getId());
        custom.setRevision(entity.getRevision());
        custom.setFirstName(entity.getFirstName());
        custom.setLastName(entity.getLastName());
        custom.setEmail(entity.getEmail());
        custom.setPassword(entity.getPassword());
        custom.setDn(entity.getDn());
        return custom;
    }

}
