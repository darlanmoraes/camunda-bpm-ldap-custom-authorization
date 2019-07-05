package org.camunda.bpm.authorization.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.identity.impl.ldap.LdapGroupEntity;

import java.util.ArrayList;
import java.util.List;

import static org.camunda.bpm.authorization.custom.CustomAuthorizationManager.Permission;

// This can be used to load additional attributes
public class CustomLdapGroupEntity extends LdapGroupEntity {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> asPermission() {
        try {
            return MAPPER.readValue(this.description, MAPPER.getTypeFactory().constructCollectionType(List.class, Permission.class));
        } catch (final Exception e) {
            return new ArrayList<>();
        }
    }

    public static final CustomLdapGroupEntity ofEntity(final GroupEntity entity) {
        final CustomLdapGroupEntity custom = new CustomLdapGroupEntity();
        custom.setId(entity.getId());
        custom.setRevision(entity.getRevision());
        custom.setName(entity.getName());
        custom.setType(entity.getType());
        return custom;
    }

}
