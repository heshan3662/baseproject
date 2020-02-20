package com.example.login.entity;

import java.util.Date;

public class User {
    private String userId;

    private String loginName;

    private String password;

    private String phone;

    private String email;

    private String fullName;

    private String avatarUrl;

    private String jobTitle;

    private String comId;

    private String deptId;

    private String roleId;

    private String isDisabled;

    private String quickCode;

    private String createdBy;

    private Date createdOn;

    private Date modifiedOn;

    private String modifiedBy;

    private String token;

    private String type;

    public User(String userId, String loginName, String password, String phone, String email, String fullName, String avatarUrl, String jobTitle, String comId, String deptId, String roleId, String isDisabled, String quickCode, String createdBy, Date createdOn, Date modifiedOn, String modifiedBy, String token,String type) {
        this.userId = userId;
        this.loginName = loginName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.jobTitle = jobTitle;
        this.comId = comId;
        this.deptId = deptId;
        this.roleId = roleId;
        this.isDisabled = isDisabled;
        this.quickCode = quickCode;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.modifiedBy = modifiedBy;
        this.token = token;
        this.type = type;
    }

    public User() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle == null ? null : jobTitle.trim();
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled == null ? null : isDisabled.trim();
    }

    public String getQuickCode() {
        return quickCode;
    }

    public void setQuickCode(String quickCode) {
        this.quickCode = quickCode == null ? null : quickCode.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy == null ? null : modifiedBy.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}