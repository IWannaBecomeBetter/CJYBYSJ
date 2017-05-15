package com.bs.controller;

import com.bs.cache.SessionCache;
import com.bs.common.constants.CommonConstant;
import com.bs.common.entity.BaseController;
import com.bs.common.entity.ResultBean;
import com.bs.common.entity.SessionDetail;
import com.bs.common.util.CookieUtil;
import com.bs.dao.entity.FileStorageInfo;
import com.bs.service.FileService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件处理
 * Created by fusj on 16/4/5.
 */
@Controller
@RequestMapping("file")
public class FileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private SessionCache sessionCache;


    @RequestMapping(value = "uptoken", method = RequestMethod.GET)
    public ResponseEntity<Object> uptoken(){
        try {
            String uptoken = fileService.getUpToken();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uptoken",uptoken);
            return new ResponseEntity<Object>(jsonObject, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 将保存到七牛的文件信息保存到数据库
     * @param key
     * @param request
     * @return
     */
    @RequestMapping(value = "fileSave", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean fileSave(String key, HttpServletRequest request) {
        try {
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());

            FileStorageInfo fileStorageInfo = fileService.saveFile(key,sessionDetail.getAccountId());

            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(fileStorageInfo.getFileId());

            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }

    /**
     * 将保存到七牛的文件信息批量保存到数据库
     * @param keys
     * @param request
     * @return
     */
    @RequestMapping(value = "filesSave", method = RequestMethod.POST)
    @ResponseBody
    public ResultBean filesSave(String keys, HttpServletRequest request) {
        try {
            Cookie cookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_VALUE);
            SessionDetail sessionDetail = (SessionDetail) sessionCache.get(cookie.getValue());

            List<FileStorageInfo> fileStorageInfoList = fileService.saveFiles(keys,sessionDetail.getAccountId());
            List<String> keyList = new ArrayList();
            for (FileStorageInfo info : fileStorageInfoList) {
                keyList.add(info.getFileId());
            }
            ResultBean resultBean = new ResultBean(true);
            resultBean.setData(keyList);
            return resultBean;
        } catch (Exception ex) {
            return ajaxException(ex);
        }
    }
}
