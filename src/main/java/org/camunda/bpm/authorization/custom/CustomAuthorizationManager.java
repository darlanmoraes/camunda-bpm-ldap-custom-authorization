package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.impl.db.PermissionCheck;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationManager;

import java.util.List;

// This class can be used to create any type of authorization. The code below retrieves the identity provider
// that can connect to LDAP to return any data needed for authorization.
// super.getCommandContext().getProcessEngineConfiguration().getIdentityProviderSessionFactory()
public class CustomAuthorizationManager extends AuthorizationManager {

    @Override
    public boolean isAuthorized(final String userId, final List<String> groupIds, final List<PermissionCheck> permissionChecks) {
        for (final PermissionCheck check : permissionChecks) {
            if (groupIds.contains(check.getResourceId())) {
                return true;
            }
        }
        return super.isAuthorized(userId, groupIds, permissionChecks);
    }

}
