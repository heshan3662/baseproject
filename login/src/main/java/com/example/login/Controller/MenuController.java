package com.example.login.Controller;


import com.alibaba.fastjson.JSON;
import com.example.login.Utils.DBParamUtils;
import com.example.login.Utils.GB2Alpha;
import com.example.login.Utils.ResponseUtils;
import com.example.login.entity.Company;
import com.example.login.entity.Menu;
import com.example.login.entity.User;
import com.example.login.entity.responsetype.ApiRes;
import com.example.login.service.EntityManageService;
import com.example.login.service.LoginService;
import com.example.login.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "menu", description = "菜单管理")
@RequestMapping("/menu")
public class MenuController {

//    @Autowired
//    MenuService menuService;

    @Autowired
    LoginService loginService;

    @Autowired
    EntityManageService entityManageService;

    private String zclass = "com.example.demo.mybatis.dbs.db1.model.Menu" ;
    private String Mapper = "MenuMapper" ;

    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name="module",value="标识",dataType="string", paramType = "query",required = true,example="",defaultValue ="menu1" ),
            @ApiImplicitParam(name="title",value="标题",dataType="string", paramType = "query",required = true,example="",defaultValue ="测试功能1" ),
            @ApiImplicitParam(name="pid",value="上级菜单id",dataType="string", paramType = "query",required = false,example=""  ),
            @ApiImplicitParam(name="url",value="链接",dataType="string", paramType = "query",required = false,example=""  ),
            @ApiImplicitParam(name="icon",value="图标名",dataType="string", paramType = "query",required = false,example=""  ),

    })
    @ResponseBody
    @RequestMapping(value = "/add_menu",method = RequestMethod.POST)
    public ApiRes  addRole(
            @RequestParam(value = "module", defaultValue = "") String module,
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "pid", defaultValue = "") String pid,
            @RequestParam(value = "url", defaultValue = "") String url,
            @RequestParam(value = "icon", defaultValue = "") String icon
            ){
        ApiRes  apiRes = new  ApiRes ();
        if(StringUtils.isBlank(module) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("唯一标识不能为空！");
            return apiRes;
        }
        if(StringUtils.isBlank(title) ){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("标题！");
            return apiRes;
        }

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user = loginService.get_cur_user_info(token);
        String createuser = user.getUserId();
        if( StringUtils.isBlank(createuser)){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("创建者获取错误！");
            return apiRes;
        }
        Menu menu = new Menu();
        menu.setCreatedBy(createuser);
        menu.setCreatedOn(new Date());
        menu.setTitle(title);
        menu.setPid(pid);
        menu.setIcon(icon);
        menu.setUrl(url);

        menu.setQuickCode(new GB2Alpha().String2Alpha(title));

        String sqlid = "insert";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(menu),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "注销")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuid",value="菜单id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/cancellation_menu",method = RequestMethod.POST)
    public ApiRes  cancellationMenu(@RequestParam(value = "menuid", defaultValue = "") String menuid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Menu menu = new Menu();
        menu.setMenuId(menuid );
        menu.setIsDisabled("T");
        menu.setModifiedOn(new Date());
        menu.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(menu),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "激活")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuid",value="菜单id",dataType="string", paramType = "query",required = true,example="",defaultValue ="dp-001" )
    })
    @ResponseBody
    @RequestMapping(value = "/activated_menu",method = RequestMethod.POST)
    public ApiRes  activated_menu(@RequestParam(value = "menuid", defaultValue = "") String menuid
    ){
        ApiRes  apiRes = new  ApiRes ();
        String token =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user    = loginService.get_cur_user_info(   token);
        String modifiedUser =   user.getUserId();
        Menu menu = new Menu();
        menu.setMenuId(menuid );
        menu.setIsDisabled("F");
        menu.setModifiedOn(new Date());
        menu.setModifiedBy(modifiedUser);
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(menu),paramsClass);
        return apiRes ;
    }

    @ApiOperation(value = "菜单修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="menuid",value="菜单id",dataType="string", paramType = "query",required = true,example="",defaultValue ="" ),
            @ApiImplicitParam(name="param",value="json参数",dataType="string", paramType = "query",required = false,example="",defaultValue ="{" +
                    "    \"title\": \"test\"}" ),
    })
    @ResponseBody
    @RequestMapping(value = "/modify_menu",method = RequestMethod.POST)
    public ApiRes  modifyMenu(
            @RequestParam(value = "menuid", defaultValue = "") String menuid,
            @RequestParam(value = "param", defaultValue = "") String param

    ){
        ApiRes  apiRes = new  ApiRes ();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("token");
        User user = loginService.get_cur_user_info(token);
        String modifiedUser = user.getUserId();
        if( StringUtils.isBlank(modifiedUser)){
            apiRes.set_result_code(-1);
            apiRes.set_result_message("修改部门失败！");
            return apiRes;
        }
        Menu menu   = JSON.parseObject(param,Menu.class);
        menu.setMenuId(menuid);
        menu.setModifiedOn(new Date());
        menu.setModifiedBy(user.getUserId());
        if(StringUtils.isBlank(menu.getTitle())){
            menu.setQuickCode(new GB2Alpha().String2Alpha(menu.getTitle()));
        }
        String sqlid = "updateByPrimaryKeySelective";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes  = entityManageService.excute(Mapper,sqlid,JSON.toJSONString(menu),paramsClass);
        return apiRes;
    }

    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "入参", dataType = "string", paramType = "query", required = true, example = "",
                    defaultValue = "{\"start\":0,\"end\":10}"),
    })
    @ResponseBody
    @RequestMapping(value = "/get_menus_info", method = RequestMethod.POST)
    public ApiRes<List<Menu>>  get_menus_info(
                                              @RequestParam(value = "params", defaultValue = "") String params
    ) {
        ApiRes  apiRes = new ApiRes<List<Menu>>();
        String sqlid = "selectForPage";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid,params,paramsClass);
        return apiRes;
    }


    @ApiOperation(value = "通过id获取菜单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuid", value = "菜单id", dataType = "string", paramType = "query", required = true, example = "", defaultValue = "test"),

    })
    @ResponseBody
    @RequestMapping(value = "/get_menu_info", method = RequestMethod.POST)
    public ApiRes<Menu>  get_menu_info(@RequestParam(value = "menuid", defaultValue = "") String menuid
    ) {
        ApiRes  apiRes = new ApiRes<List<User>>();
        String sqlid = "selectByPrimaryKey";
        String paramsClass = DBParamUtils.getParamClass(  sqlid,   zclass );
        apiRes = entityManageService.excute(Mapper,sqlid ,menuid ,paramsClass);
        return apiRes;
    }
}