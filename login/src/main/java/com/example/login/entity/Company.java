package com.example.login.entity;

import java.util.Date;

public class Company {
    private String comId;

    private String name;

    private String principalId;

    private String isDisabled;

    private String quickCode;

    private String createdBy;

    private Date createdOn;

    private Date modifiedOn;

    private String modifiedBy;

    public Company(String comId, String name, String principalId, String isDisabled, String quickCode, String createdBy, Date createdOn, Date modifiedOn, String modifiedBy) {
        this.comId = comId;
        this.name = name;
        this.principalId = principalId;
        this.isDisabled = isDisabled;
        this.quickCode = quickCode;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.modifiedBy = modifiedBy;
    }

    public Company() {
        super();
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId == null ? null : principalId.trim();
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
}