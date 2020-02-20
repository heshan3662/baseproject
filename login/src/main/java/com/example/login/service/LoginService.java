package com.example.login.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.login.DBinterface.DBinterface;
import com.example.login.Utils.SystemUtil;
import com.example.login.Utils.UUIDTool;
import com.example.login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    DBinterface DBinterface;
    @Autowired
    CacheService cacheService;

    /**
     * 校验用户是否存在
     *
     * @param username
     * @return
     */
    public boolean ckeck_username_exist(String username) {
        String sql = "select   *from  user where LOGIN_NAME = '" + username + "'";
        Map map = DBinterface.queryForObj(SystemUtil.CONFIG_DB1, sql, 0);
        if (CollectionUtils.isEmpty(map)) {
            return false;
        }

        return true;
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @param fullname
     * @param phone
     * @param email
     * @param createuser
     * @param deptid
     * @param roleid
     * @return
     */
    public int regist(String username,
                      String password,
                      String fullname,
                      String phone,
                      String email,
                      String createuser,
                      String deptid,
                      String roleid) {
        Map codeMap = DBinterface.queryForObj(SystemUtil.CONFIG_DB1, "SELECT seq('PRIMARYKEY') code ", 0);
        if (CollectionUtils.isEmpty(codeMap)) {
            return 0;
        }
        String code = codeMap.get("code").toString();
        String sql = "  INSERT INTO `user` (`USER_ID`, `LOGIN_NAME`, `PASSWORD`, `PHONE`,`EMAIL`,`FULL_NAME`, `DEPT_ID`, " +
                "`ROLE_ID`,  `CREATED_ON`, `CREATED_BY`,  `QUICK_CODE` )\n" +
                "    VALUES" +
                "(";
        sql += "'US-" + code + "', ";
        sql += "'" + username + "', ";
        sql += "'" + password + "', ";
        sql += "'" + phone + "', ";
        sql += "'" + email + "', ";
        sql += "'" + fullname + "', ";
        sql += "'" + deptid + "', ";
        sql += "'" + roleid + "', ";
        sql += "CURRENT_TIMESTAMP,";
        sql += "'" + createuser + "', ";
        sql += "''";
        sql += ")";
        int i = DBinterface.update(SystemUtil.CONFIG_DB1, sql);
        return i;
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param type
     * @return
     */
    public String login_in(String username,
                           String password,
                           String type) {
        String sql = "select   *from  user where   IS_DISABLED = 'F'   and LOGIN_NAME = '" + username + "'  and  PASSWORD = '" + password + "'";
        Map map = DBinterface.queryForObj(SystemUtil.CONFIG_DB1, sql, 0);
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        UUIDTool uuidTool = new UUIDTool();
        String token = uuidTool.getUUID(); //生成token
        map.put("token", token);
        map.put("type", type);
        User User = JSON.parseObject(JSON.toJSONString(map), User.class);
        cacheService.SetCacheForUserInfo(User);
        return JSON.toJSONString(map);
    }

    /**
     * 注销人员信息
     *
     * @param modifiedUser
     * @return
     */
    public boolean cancellation_user(String modifiedUser,
                                     String username) {
        //先清除缓存中的数据
        cacheService.deleteCacheForUserInfoByUserName(username);
        //将用户信息置位不可操作
        return update_user(username, null, null, null, null, null, null,
                "T", modifiedUser);
    }

    /**
     * 激活人员信息
     *
     * @param modifiedUser
     * @return
     */
    public boolean activated_user(String modifiedUser,
                                  String username) {
        return update_user(username, null, null, null, null, null, null,
                "F", modifiedUser);
    }

    /**
     * 获取当前人员信息
     *
     * @param token
     * @return
     */
    public User get_cur_user_info(String token) {
        User User = cacheService.GetCacheForUserInfoByToken(token);
        return User;
    }

    /**
     * 查询符合条件的人员信息列表
     *
     * @return
     */
    public List<User> get_user_info(String extentsql) {
        String sql = "select   *from  user where  1 = 1  ";
        if (!StringUtils.isEmpty(extentsql)) {
            sql += extentsql;
        }
        List list = DBinterface.queryForList(SystemUtil.CONFIG_DB1, sql);
        return list;
    }

    public Long get_user_info_count(String extentsql) {
        String sql = "select  count(1) total from  user where 1 = 1  ";
        if (!StringUtils.isEmpty(extentsql)) {
            sql += extentsql;
        }
        Long total = DBinterface.queryForTotal(SystemUtil.CONFIG_DB1, sql);
        return total;
    }

    /**
     * 修改人员信息
     *
     * @param
     * @return
     */
    public boolean update_user(String username,
                               String password,
                               String fullname,
                               String phone,
                               String email,
                               String deptid,
                               String roleid,
                               String isdisable,
                               String modifiedUser) {
        String sql = " update user  set  ";
        if (!StringUtils.isEmpty(password)) {
            byte[] bytes = password.getBytes();
            //Base64 加密
            String encoded = Base64.getEncoder().encodeToString(bytes);
            sql += " PASSWORD = '" + encoded + "',";
        }
        if (!StringUtils.isEmpty(fullname)) {
            sql += " FULL_NAME = '" + fullname + "',";
        }
        if (!StringUtils.isEmpty(phone)) {
            sql += " PHONE = '" + phone + "',";
        }
        if (!StringUtils.isEmpty(email)) {
            sql += " EMAIL = '" + email + "',";
        }
        if (!StringUtils.isEmpty(deptid)) {
            sql += " DEPT_ID = '" + deptid + "',";
        }
        if (!StringUtils.isEmpty(roleid)) {
            sql += " ROLE_ID = '" + roleid + "',";
        }
        if (!StringUtils.isEmpty(isdisable)) {
            sql += " IS_DISABLED = '" + isdisable + "',";
        }
        //无修改
        if (!",".equals(sql.substring(sql.length() - 1))) {
            return false;
        }
        if (!StringUtils.isEmpty(modifiedUser)) {
            sql += " MODIFIED_BY = '" + modifiedUser + "',";
            sql += " MODIFIED_ON = CURRENT_TIMESTAMP ";
        } else {
            return false;
        }
        sql += " where  LOGIN_NAME  = '" + username + "'";
        int i = DBinterface.update(SystemUtil.CONFIG_DB1, sql);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    public String get_authority(String userid) {
        String sql = "SELECT auths  FROM `gd_sys_role`  where id = '" + userid + "' ";
        Map map = DBinterface.queryForObj(SystemUtil.CONFIG_DB1, sql, 0);
        return JSON.toJSONString(map);
    }

    public boolean login_out(String token) {
        return cacheService.deleteCacheForUserInfo(token);
    }

}