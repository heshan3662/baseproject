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
public class DepartmentService {

    @Autowired
    DBinterface DBinterface;

    public int add_dept(String deptname,
                        String comid,
                        String parentdeptid,
                        String principalid,
                        String creatuser) {
        Map codeMap = DBinterface.queryForObj(SystemUtil.CONFIG_DB1, "SELECT seq('PRIMARYKEY') code ", 0);
        if (CollectionUtils.isEmpty(codeMap)) {
            return 0;
        }
        String code = "DP-" + codeMap.get("code").toString();
        String sql = "INSERT INTO `department` (`DEPT_ID`, `COM_ID`,`NAME`, `PARENT_DEPT`,`PRINCIPAL_ID`,`CREATED_ON`, `CREATED_BY`)\n" +
                "    VALUES (";
        sql +=   "'" + code + "', ";
        sql +=   "'" + comid + "', ";
        sql +=   "'" + deptname + "', ";
        sql +=   "'" + parentdeptid + "', ";
        sql +=   "'" + principalid + "', ";
        sql +=   "CURRENT_TIMESTAMP,";
        sql +=   "'" + creatuser + "'  ";
        sql += ")";
        int i = DBinterface.update(SystemUtil.CONFIG_DB1, sql);
        return i;
    }


    public boolean cancellation_Dept(String modifiedUser,
                                     String deptid) {
        //将用户信息置位不可操作
        return update_Dept(deptid, null, null, null, "T", modifiedUser);
    }
    public boolean activated_Dept(String modifiedUser,
                                     String deptid) {
        //将用户信息置位不可操作
        return update_Dept(deptid, null, null, null, "F", modifiedUser);
    }

    public boolean update_Dept(String deptid,
                               String deptname,
                               String parentdeptid,
                               String principalid,
                               String isdisable,
                               String modifiedUser
    ) {
        String sql = " update department  set  ";
        if (!StringUtils.isEmpty(deptname)) {
            sql += " NAME = '" + deptname + "',";
        }
        if (!StringUtils.isEmpty(parentdeptid)) {
            sql += " PARENT_DEPT = '" + parentdeptid + "',";
        }
        if (!StringUtils.isEmpty(principalid)) {
            sql += " PRINCIPAL_ID = '" + principalid + "',";
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
        sql += " where  DEPT_ID  = '" + deptid + "'";
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
    public List<User> get_dept_info(String extentsql) {
        String sql = "select   *from  department where  1 = 1  ";
        if (!StringUtils.isEmpty(extentsql)) {
            sql += extentsql;
        }
        List list = DBinterface.queryForList(SystemUtil.CONFIG_DB1, sql);
        return list;
    }

    public Long get_dept_info_count(String extentsql) {
        String sql = "select  count(1) total from  department where 1 = 1  ";
        if (!StringUtils.isEmpty(extentsql)) {
            sql += extentsql;
        }
        Long total = DBinterface.queryForTotal(SystemUtil.CONFIG_DB1, sql);
        return total;
    }
}