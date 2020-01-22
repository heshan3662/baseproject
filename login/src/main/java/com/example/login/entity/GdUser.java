package com.example.login.entity;

import com.alibaba.fastjson.JSON;

/**
 * table name:  gd_user
 * author name: heshan
 * create time: 2020-01-14 16:45:49
 */ 
public class GdUser extends EntityHelper{

	private long id;
	/*部门id*/
	private String department_id;
	/*用户名*/
	private String username;
	/*用户密码*/
	private String userpass;
	/*真实姓名*/
	private String truename;
	/*手机号*/
	private String mobile;
	/*用户头像*/
	private String portrait;
	/*登录标识*/
	private String token;
	/*token过期时间*/
	private long token_expiration;
	/*App登录标识*/
	private String app_token;
	/*AppToken过期时间*/
	private long app_token_expiration;
	/*最后登录时间*/
	private long last_login_time;
	/*最后登录ip*/
	private String last_login_ip;
	/*0: PC, 1: APP, 2: 其它*/
	private int type;

	public GdUser() {
		super();
	}
	public GdUser(long id,String department_id,String username,String userpass,String truename,String mobile,String portrait,String token,long token_expiration,String app_token,long app_token_expiration,long last_login_time,String last_login_ip,int type) {
		this.id=id;
		this.department_id=department_id;
		this.username=username;
		this.userpass=userpass;
		this.truename=truename;
		this.mobile=mobile;
		this.portrait=portrait;
		this.token=token;
		this.token_expiration=token_expiration;
		this.app_token=app_token;
		this.app_token_expiration=app_token_expiration;
		this.last_login_time=last_login_time;
		this.last_login_ip=last_login_ip;
		this.type=type;
	}
	public void setId(long id){
		this.id=id;
	}
	public long getId(){
		return id;
	}
	public void setDepartment_id(String department_id){
		this.department_id=department_id;
	}
	public String getDepartment_id(){
		return department_id;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername(){
		return username;
	}
	public void setUserpass(String userpass){
		this.userpass=userpass;
	}
	public String getUserpass(){
		return userpass;
	}
	public void setTruename(String truename){
		this.truename=truename;
	}
	public String getTruename(){
		return truename;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}
	public String getMobile(){
		return mobile;
	}
	public void setPortrait(String portrait){
		this.portrait=portrait;
	}
	public String getPortrait(){
		return portrait;
	}
	public void setToken(String token){
		this.token=token;
	}
	public String getToken(){
		return token;
	}
	public void setToken_expiration(long token_expiration){
		this.token_expiration=token_expiration;
	}
	public long getToken_expiration(){
		return token_expiration;
	}
	public void setApp_token(String app_token){
		this.app_token=app_token;
	}
	public String getApp_token(){
		return app_token;
	}
	public void setApp_token_expiration(long app_token_expiration){
		this.app_token_expiration=app_token_expiration;
	}
	public long getApp_token_expiration(){
		return app_token_expiration;
	}
	public void setLast_login_time(long last_login_time){
		this.last_login_time=last_login_time;
	}
	public long getLast_login_time(){
		return last_login_time;
	}
	public void setLast_login_ip(String last_login_ip){
		this.last_login_ip=last_login_ip;
	}
	public String getLast_login_ip(){
		return last_login_ip;
	}
	public void setType(int type){
		this.type=type;
	}
	public int getType(){
		return type;
	}
	@Override
	public String toString() {
		return "gd_user[" + 
			"id=" + id + 
			", department_id=" + department_id + 
			", username=" + username + 
			", userpass=" + userpass + 
			", truename=" + truename + 
			", mobile=" + mobile + 
			", portrait=" + portrait + 
			", token=" + token + 
			", token_expiration=" + token_expiration + 
			", app_token=" + app_token + 
			", app_token_expiration=" + app_token_expiration + 
			", last_login_time=" + last_login_time + 
			", last_login_ip=" + last_login_ip + 
			", type=" + type + 
			"]";
	}
	@Override
	public String getPrimaryKey() {
		return "id";
	}
}

