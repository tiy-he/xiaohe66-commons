package com.xiaohe66.common.web.sec.impl;

import com.xiaohe66.common.util.ex.BusinessException;
import com.xiaohe66.common.util.ex.ErrorCodeEnum;
import com.xiaohe66.common.web.sec.CurrentAccount;
import com.xiaohe66.common.web.sec.SecurityService;
import com.xiaohe66.common.web.util.WebUtils;
import lombok.NonNull;

import java.util.Collection;

/**
 * @author xiaohe
 * @since 2022.01.17 11:19
 */
public class DefaultSecurityServiceImpl implements SecurityService<CurrentAccount> {

    @Override
    public void login(CurrentAccount currentAccount) {
        WebUtils.getSession().setAttribute(SecurityService.CURRENT_USER_KEY, currentAccount);
    }

    @Override
    public void logout() {
        WebUtils.getSession().removeAttribute(SecurityService.CURRENT_USER_KEY);
    }

    @Override
    public boolean isLogin() {
        return getCurrentAccountNotEx() != null;
    }

    @Override
    public String getSessionId() {
        return WebUtils.getSession().getId();
    }

    @Override
    public void setCurrentAccount(CurrentAccount account) {
        setAttribute(CURRENT_USER_KEY, account);
    }

    @Override
    public CurrentAccount getCurrentAccount() {

        CurrentAccount currentAccount = getCurrentAccountNotEx();

        if (currentAccount == null) {
            throw new BusinessException(ErrorCodeEnum.NOT_LOGIN);
        }

        return currentAccount;
    }

    protected CurrentAccount getCurrentAccountNotEx() {
        return (CurrentAccount) WebUtils.getSession().getAttribute(SecurityService.CURRENT_USER_KEY);
    }

    @Override
    public Long getCurrentAccountId() {
        return getCurrentAccount().getId();
    }

    @Override
    public void setAttribute(String key, Object value) {
        WebUtils.getSession().setAttribute(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAttribute(String key) {
        return (T) WebUtils.getSession().getAttribute(key);
    }

    @Override
    public boolean isAdmin() {
        return hasRole(SecurityService.ROLE_NAME_ADMIN);
    }

    @Override
    public boolean hasRole(String role) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasRole(role);
    }

    @Override
    public boolean hasRoleAll(String... roles) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasRoleAll(roles);
    }

    @Override
    public boolean hasRoleAll(Collection<String> roles) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasRoleAll(roles);
    }

    @Override
    public boolean hasRoleAny(String... roles) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasRoleAny(roles);
    }

    @Override
    public boolean hasRoleAny(Collection<String> roles) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasRoleAny(roles);
    }

    @Override
    public boolean hasPermission(String permission) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasPermission(permission);
    }

    @Override
    public boolean hasPermissionAll(String... permission) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasPermissionAll(permission);
    }

    @Override
    public boolean hasPermissionAll(Collection<String> permission) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasPermissionAll(permission);
    }

    @Override
    public boolean hasPermissionAny(String... permission) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasPermissionAny(permission);
    }

    @Override
    public boolean hasPermissionAny(Collection<String> permission) {
        CurrentAccount currentAccount = getCurrentAccount();
        return currentAccount.hasPermissionAny(permission);
    }

    @Override
    public boolean hasCurrentAccountPermission(@NonNull Long createId) {
        if (isAdmin()) {
            return true;
        }

        Long accountId = getCurrentAccountId();

        return accountId.equals(createId);
    }

}
