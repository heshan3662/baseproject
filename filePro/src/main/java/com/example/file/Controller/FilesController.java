package com.example.file.Controller;

 import org.apache.commons.io.FileUtils;
 import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FilesController {

    Logger logger = Logger.getLogger(this.getClass());



    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public void getOption(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(204);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadLocal", method = RequestMethod.POST)
    public Map<String, Object> uploadLocal(
            @RequestParam(value = "file") MultipartFile file  ,
            @RequestParam(value = "md5") String  md5  ,

            HttpServletRequest request,
            HttpServletResponse response
    ) {
        //检查是否已经存在此文件！
         Map  _data= new HashMap();
        String msg ="";
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("result_code","0");
        String  md5C =""  ;

        File file1  =new File(file.getOriginalFilename());
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), file1);
               md5C = DigestUtils.md5DigestAsHex( new FileInputStream(file1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!md5C.equals(md5)){
            msg =  "上传过程中文件损坏，md5不匹配！";
            res.put("result_code","0");
            res.put("result_message",msg);
            return res;
        }
        res.put("result_message",  "");
        res.put("r_msg",msg);
        res.put("data", _data);
        return res;
    }



}


