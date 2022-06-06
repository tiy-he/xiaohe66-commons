package com.xiaohe66.common.web.sec;

import com.xiaohe66.common.util.Assert;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xiaohe
 * @since 2022.01.17 11:21
 */
@RequiredArgsConstructor
public class CurrentAccount {

    @Getter
    private final Long id;

    private final Set<String> roles = new HashSet<>();

    private final Set<String> permissions = new HashSet<>();

    public void addRole(String role) {
        Assert.requireNotEmpty(role);
        roles.add(role);
    }

    public void addRole(@NonNull Collection<String> roles) {
        for (String role : roles) {
            addRole(role);
        }
    }

    public void addRole(@NonNull String... roles) {
        for (String role : roles) {
            addRole(role);
        }
    }

    public void removeRole(@NonNull String role) {
        roles.remove(role);
    }

    public void addPermission(String permission) {
        Assert.requireNotEmpty(permission);
        permissions.add(permission);
    }

    public void addPermission(@NonNull Collection<String> permissions) {
        for (String permission : permissions) {
            addRole(permission);
        }
    }

    public void addPermission(@NonNull String... permissions) {
        for (String permission : permissions) {
            addRole(permission);
        }
    }

    public void removePermission(@NonNull String permission) {
        permissions.remove(permission);
    }

    public boolean hasRole(@NonNull String roleName) {
        return roles.contains(roleName);
    }

    public boolean hasRoleAll(@NonNull String... roleNames) {
        for (String roleName : roleNames) {
            if (!roles.contains(roleName)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasRoleAll(@NonNull Collection<String> roleNames) {
        for (String roleName : roleNames) {
            if (!roles.contains(roleName)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasRoleAny(@NonNull String... roleNames) {
        for (String roleName : roleNames) {
            if (roles.contains(roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRoleAny(@NonNull Collection<String> roleNames) {
        for (String roleName : roleNames) {
            if (roles.contains(roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPermission(@NonNull String permissionName) {
        return permissions.contains(permissionName);
    }

    public boolean hasPermissionAll(@NonNull String... permissionNames) {
        for (String permissionName : permissionNames) {
            if (!permissions.contains(permissionName)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPermissionAll(@NonNull Collection<String> permissionNames) {
        for (String permissionName : permissionNames) {
            if (!permissions.contains(permissionName)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPermissionAny(@NonNull String... permissionNames) {
        for (String permissionName : permissionNames) {
            if (permissions.contains(permissionName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPermissionAny(@NonNull Collection<String> permissionNames) {
        for (String permissionName : permissionNames) {
            if (permissions.contains(permissionName)) {
                return true;
            }
        }
        return false;
    }

}
