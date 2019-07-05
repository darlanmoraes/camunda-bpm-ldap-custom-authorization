package org.camunda.bpm.authorization.custom;

import org.apache.commons.lang.StringUtils;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.db.PermissionCheck;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationManager;
import org.camunda.bpm.identity.impl.ldap.LdapIdentityProviderSession;

import java.util.List;
import java.util.stream.Collectors;

// This class can be used to create any type of authorization. The code below retrieves the identity provider
// that can connect to LDAP to return any data needed for authorization.
// super.getCommandContext().getProcessEngineConfiguration().getIdentityProviderSessionFactory()
public class CustomAuthorizationManager extends AuthorizationManager {

    @Override
    public boolean isAuthorized(final String userId, final List<String> groupIds, final List<PermissionCheck> permissionChecks) {
        this.disableAuthorization();
        final List<Permission> permissions = this.getPermissions(groupIds);
        for (final Permission permission : permissions) {
            for (final PermissionCheck check : permissionChecks) {
                if (this.matches(permission, check)) {
                    this.enableAuthorization();
                    return true;
                }
            }
        }
        this.enableAuthorization();
        return false;
    }

    private void disableAuthorization() {
        super.getCommandContext().getProcessEngineConfiguration().setAuthorizationEnabled(false);
    }

    private void enableAuthorization() {
        super.getCommandContext().getProcessEngineConfiguration().setAuthorizationEnabled(true);
    }

    private List<Permission> getPermissions(final List<String> groupIds) {
        final ProcessEngineConfigurationImpl configuration = super.getCommandContext().getProcessEngineConfiguration();
        final LdapIdentityProviderSession session = (LdapIdentityProviderSession) configuration.getIdentityProviderSessionFactory().openSession();
        return groupIds.stream()
                .map(session::findGroupById)
                .map(group -> (CustomLdapGroupEntity) group)
                .flatMap(group -> group.asPermission().stream())
            .collect(Collectors.toList());
    }

    private boolean matches(final Permission permission, final PermissionCheck check) {
        if (StringUtils.isNotBlank(permission.getPermission()) && permission.getPermission().equals(check.getPermission().getName())) {
            if (StringUtils.isNotBlank(permission.getResource()) && check.getResource() != null) {
                return permission.getResource().equalsIgnoreCase(((Resources) check.getResource()).name());
            }
            if (StringUtils.isNotBlank(permission.getResourceId()) && StringUtils.isNotBlank(check.getResourceId())) {
                return permission.getResourceId().equalsIgnoreCase(check.getResourceId());
            }
        }
        return false;
    }

    @SuppressWarnings("unused")
    static class Permission {
        private String resource;
        private String resourceId;
        private String permission;

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getResourceId() {
            return resourceId;
        }

        public void setResourceId(String resourceId) {
            this.resourceId = resourceId;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }
    }

}
