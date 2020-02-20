package com.example.login.service;

import com.example.login.DBinterface.DBinterface;
import com.example.login.Utils.SystemUtil;
import com.example.login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class MenuService {

    @Autowired
    DBinterface DBinterface;

    public int add_menu(String module, String title, String pid, String url, String icon, String creatuser) {
        Map codeMap = DBinterface.queryForObj(SystemUtil.CONFIG_DB1, "SELECT seq('PRIMARYKEY') code ", 0);
        if (CollectionUtils.isEmpty(codeMap)) {
            return 0;
        }
        String code = "MN-" + codeMap.get("code").toString();
        String sql = "INSERT INTO `menu` (`MENU_ID`, `MODULE`, `TITLE`,`PID`,`URL`,`ICON`, `CREATED_ON`, `CREATED_BY`)\n" +
                "    VALUES (";
        sql += "'" + code + "', ";
        sql += "'" + module + "', ";
        sql += "'" + title + "', ";
        sql += "'" + pid + "', ";
        sql += "'" + url + "', ";
        sql += "'" + icon + "', ";

        sql += "CURRENT_TIMESTAMP,";
        sql += "'" + creatuser + "'  ";
        sql += ")";
        int i = DBinterface.update(SystemUtil.CONFIG_DB1, sql);
        return i;
    }


    public boolean cancellation_menu(String modifiedUser,
                                     String menuid) {
        //将用户信息置位不可操作
        return update_menu(menuid, null, null,null,null,null,"T", modifiedUser);
    }

    public boolean activated_menu(String modifiedUser,
                                  String menuid) {
        //将用户信息置位不可操作
        return update_menu(menuid, null, null,null,null,null,"F", modifiedUser);
    }

    public boolean update_menu(String menuid,
                               String module,
                               String title,
                               String pid,
                               String url,
                               String icon,
                               String isdisable,
                               String modifiedUser
    ) {
        String sql = " update menu  set  ";
        if (!StringUtils.isEmpty(module)) {
            sql += " MODULE = '" + module + "',";
        }
        if (!StringUtils.isEmpty(title)) {
            sql += " TITLE = '" + title + "',";
        }
        if (!StringUtils.isEmpty(pid)) {
            sql += " PID = '" + pid + "',";
        }
        if (!StringUtils.isEmpty(url)) {
            sql += " URL = '" + url + "',";
        }
        if (!StringUtils.isEmpty(icon)) {
            sql += " ICON = '" + icon + "',";
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
        sql += " where  MENU_ID  = '" + menuid + "'";
        int i = DBinterface.update(SystemUtil.CONFIG_DB1, sql);
        if (i <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 查询符合条件的部门列表
     *
     * @return
     */
    public List<User> get_menu_info(String extentsql) {
        String sql = "select   *from  menu where  1 = 1  ";
        if (!StringUtils.isEmpty(extentsql)) {
            sql += extentsql;
        }
        List list = DBinterface.queryForList(SystemUtil.CONFIG_DB1, sql);
        return list;
    }

    public Long get_menu_info_count(String extentsql) {
        String sql = "select  count(1) total from  role where 1 = 1  ";
        if (!StringUtils.isEmpty(extentsql)) {
            sql += extentsql;
        }
        Long total = DBinterface.queryForTotal(SystemUtil.CONFIG_DB1, sql);
        return total;
    }
}