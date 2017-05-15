package com.bs.service;

import com.bs.common.entity.ImageDetail;
import com.bs.dao.entity.FileStorageInfo;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */
public interface FileService {
    /**
     * 获取图片信息
     * @param fileIds
     * @return
     */
    List<ImageDetail> getImageDetails(String fileIds) throws Exception;

    /**
     * 获取图片信息
     * @param fileId
     * @return
     * @throws Exception
     */
    ImageDetail getImageDetail(String fileId) throws Exception;

    /**
     * 获取domain
     * @return
     */
    String getDomain();

    /**
     * 获取不带Key的uploadToken
     * @return
     */
    String getUpToken() throws Exception;


    /**
     * 获取带key的uploadToken
     * @param key
     * @return
     */
    String getUpToken(String key) throws Exception;


    /**
     * 上传文件到七牛服务器
     * @param is
     * @param userid
     * @return
     * @throws Exception
     */
    String uploadFileQN(InputStream is, String fileName, Integer userid)
            throws Exception;


    /**
     * 根据文件ID获取文件地址
     * @param fileId
     * @param autoOrient 是否根据原图EXIF信息自动旋正
     * @return
     * @throws Exception
     */
    String getFileUrl(String fileId, boolean autoOrient) throws Exception;


    /**
     * 根据文件ID获取文件地址
     * @param fileId
     * @return
     * @throws Exception
     */
    String getFileUrl(String fileId) throws Exception;

    /**
     * 根据key保存文件相关信息(七牛)
     * @param key
     * @return 文件ID
     * @throws Exception
     */
    FileStorageInfo saveFile(String key, Integer userId) throws Exception;


    /**
     * 根据key批量保存文件相关信息(七牛)
     * @param keys
     * @return 文件ID
     * @throws Exception
     */
    List<FileStorageInfo> saveFiles(String keys, Integer userId) throws Exception;
}
