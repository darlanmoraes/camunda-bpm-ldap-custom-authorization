package org.camunda.bpm.authorization.custom;

import org.camunda.bpm.engine.impl.db.PermissionCheck;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationManager;

import java.util.List;

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
