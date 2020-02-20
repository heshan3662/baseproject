package com.example.login.entity;

import java.util.Date;

public class ComMember {
    private String memberId;

    private String comId;

    private String userId;

    private String isDisabled;

    private Date createdOn;

    private String createdBy;

    private Date modifiedOn;

    private String modifiedBy;

    public ComMember(String memberId, String comId, String userId, String isDisabled, Date createdOn, String createdBy, Date modifiedOn, String modifiedBy) {
        this.memberId = memberId;
        this.comId = comId;
        this.userId = userId;
        this.isDisabled = isDisabled;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.modifiedOn = modifiedOn;
        this.modifiedBy = modifiedBy;
    }

    public ComMember() {
        super();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled == null ? null : isDisabled.trim();
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
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