package com.xiaohe66.common.web.sec;

import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;

import java.util.Collection;

/**
 * @author xiaohe
 * @since 2021.10.28 16:15
 */
public interface SecurityService {

    String CURRENT_USER_KEY = "session_current_user";
    String ROLE_NAME_ADMIN = "admin";

    void login(CurrentAccount currentAccount);

    void logout();

    boolean isLogin();

    String getSessionId();

    void setCurrentAccount(CurrentAccount account);

    CurrentAccount getCurrentAccount();

    Long getCurrentAccountId();

    void setAttribute(String key, Object value);

    <V> V getAttribute(String key);

    boolean isAdmin();

    boolean hasRole(String role);

    boolean hasRoleAll(String... roles);

    boolean hasRoleAll(Collection<String> roles);

    boolean hasRoleAny(String... roles);

    boolean hasRoleAny(Collection<String> roles);

    boolean hasPermission(String permission);

    boolean hasPermissionAll(String... permission);

    boolean hasPermissionAll(Collection<String> permission);

    boolean hasPermissionAny(String... permission);

    boolean hasPermissionAny(Collection<String> permission);

    /**
     * 是否有创建人权限（检查当前用户是否为指定值，管理员默认拥有全部权限）
     */
    boolean hasCurrentAccountPermission(Long createId);

    default void checkLogin() {
        if (!isLogin()) {
            throw new BusinessException(ErrorCodeEnum.NOT_LOGIN);
        }
    }

    default void checkRole(String role) {
        if (!hasRole(role)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkRoleAll(String... roles) {
        if (!hasRoleAll(roles)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkRoleAll(Collection<String> roles) {
        if (!hasRoleAll(roles)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkRoleAny(String... roles) {
        if (!hasRoleAny(roles)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkRoleAny(Collection<String> roles) {
        if (!hasRoleAny(roles)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkPermission(String permission) {
        if (!hasPermission(permission)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkPermissionAll(String... permission) {
        if (!hasPermissionAll(permission)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkPermissionAll(Collection<String> permission) {
        if (!hasPermissionAll(permission)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkPermissionAny(String... permission) {
        if (!hasPermissionAny(permission)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    default void checkPermissionAny(Collection<String> permission) {
        if (!hasPermissionAny(permission)) {
            throw new BusinessException(ErrorCodeEnum.NOT_FUNCTION_PERMISSION);
        }
    }

    /**
     * 检查是否有创建人权限（检查当前用户是否为指定值，管理员默认拥有全部权限）
     */
    default void checkCurrentAccountPermission(Long createId) {
        if (!hasCurrentAccountPermission(createId)) {
            throw new BusinessException(ErrorCodeEnum.NOT_DATA_PERMISSION);
        }
    }

}
