package com.bs.service.Impl;

import com.bs.common.config.QiniuSetting;
import com.bs.common.entity.ImageDetail;
import com.bs.dao.entity.FileStorageInfo;
import com.bs.dao.entity.FileStorageInfoExample;
import com.bs.dao.mapping.FileStorageInfoMapper;
import com.bs.service.FileService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * Created by Administrator on 2017/4/11.
 */
@Transactional
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private QiniuSetting qiniuSetting;
    @Autowired
    private FileStorageInfoMapper fileStorageInfoMapper;
    @Autowired
    private static final AtomicDouble ad = new AtomicDouble(95);


    public List<ImageDetail> getImageDetails(String fileIds) throws Exception {
        List<ImageDetail> list = new ArrayList<ImageDetail>();
        String[] fileIdArray = fileIds.split(",");
        for (String fileId : fileIdArray) {
            list.add(getImageDetail(fileId));
        }
        return list;
    }

    public ImageDetail getImageDetail(String fileId) throws Exception {

        FileStorageInfo fileStorageInfo = fileStorageInfoMapper.selectByPrimaryKey(fileId);

        if (fileStorageInfo == null) {
            throw new Exception("文件不存在");
        }
        String fileUrl = fileStorageInfo.getFileDomain() + fileStorageInfo.getFileKey();

        ImageDetail imageDetail = new ImageDetail(fileUrl, fileStorageInfo.getFileId(), fileStorageInfo.getFileKey(), fileStorageInfo.getCreateTime());
        return imageDetail;
    }

    public String getDomain() {
        return qiniuSetting.getDomain();
    }

    public String getUpToken() throws Exception {
        Auth auth = Auth.create(qiniuSetting.getAccessKey(), qiniuSetting.getSecretKey());
        return auth.uploadToken(qiniuSetting.getBucket());
    }

    public String getUpToken(String key) throws Exception {
        Auth auth = Auth.create(qiniuSetting.getAccessKey(), qiniuSetting.getSecretKey());
        return auth.uploadToken(qiniuSetting.getBucket(), key);
    }

    public String uploadFileQN(InputStream is, String fileName, Integer userid) throws Exception {
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        String key = String.format("CJY%s", fileName);

        String token = getUpToken(key);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bs = new byte[1024];
        int len = -1;
        while ((len = is.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        byte b[] = bos.toByteArray();
        bos.close();

        Response res;

        try {
            //调用put方法上传，这里指定的key和上传策略中的key要一致
            res = uploadManager.put(b, key, token);
        } catch (QiniuException e) {
            e.printStackTrace();
            throw e;
        }
        if (res.isOK()) {
            FileStorageInfo fileStorageInfo = saveFile(key, userid);
            return fileStorageInfo.getFileId();
        }
        return null;
    }

    public String getFileUrl(String fileId, boolean autoOrient) throws Exception {
        FileStorageInfo fileStorageInfo = fileStorageInfoMapper.selectByPrimaryKey(fileId);

        if (fileStorageInfo == null) {
            throw new Exception("文件不存在");
        }
        String fileUrl = fileStorageInfo.getFileDomain() + fileStorageInfo.getFileKey();
        if (autoOrient) {
            fileUrl += "?imageMogr2/auto-orient";
        }
        return fileUrl;
    }

    public String getFileUrl(String fileId) throws Exception {
        return getFileUrl(fileId, false);
    }

    public FileStorageInfo saveFile(String key, Integer userId) throws Exception {
        return null;
    }

    public List<FileStorageInfo> saveFiles(String keys, Integer userId) throws Exception {
        List<FileStorageInfo> fileStorageInfoList = new ArrayList();
        Auth auth = Auth.create(qiniuSetting.getAccessKey(), qiniuSetting.getSecretKey());
        Configuration cfg = new Configuration(Zone.zone0());
        BucketManager bucketManager = new BucketManager(auth,cfg);
        try {
            String[] keyList = keys.split(",");
            for (String key : keyList) {
                FileStorageInfo fileStorageInfo = new FileStorageInfo();

                FileStorageInfoExample example = new FileStorageInfoExample();
                example.createCriteria().andFileKeyEqualTo(key);
                List<FileStorageInfo> list = fileStorageInfoMapper.selectByExample(example);
                if (list.size() > 0) {
                    fileStorageInfo = list.get(0);
                } else {
                    FileInfo info = bucketManager.stat(qiniuSetting.getBucket(), key);

                    fileStorageInfo.setAccountId(userId);
                    fileStorageInfo.setFileId(genFileId(false));
                    fileStorageInfo.setFileKey(key);
                    fileStorageInfo.setFileBucket(qiniuSetting.getBucket());
                    fileStorageInfo.setFileDomain(qiniuSetting.getDomain());
                    fileStorageInfo.setFileSize(info.fsize);
                    fileStorageInfo.setFileMimeType(info.mimeType);
                    fileStorageInfo.setCreateTime(new Timestamp(new Date().getTime()));
                    fileStorageInfoMapper.insert(fileStorageInfo);
                }

                fileStorageInfoList.add(fileStorageInfo);
            }

            return fileStorageInfoList;
        } catch (QiniuException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 生产FileId,长度为20； U/R+17位时间戳+2位序列
     *
     * @param needValidate 是否需要权限验证
     * @return
     */
    private String genFileId(boolean needValidate) {
        StringBuffer fileid = new StringBuffer(20);

        if (needValidate) {
            fileid.append("R");
        } else {
            fileid.append("U");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        fileid.append(formatter.format(new Date()));

		/*
		 * 为了防止在一毫秒内有并发，还需要带上两位序列号
		 */
        double idx = ad.getAndAdd(1);

        idx = idx % 99;
        DecimalFormat df = new DecimalFormat("00");
        fileid.append(df.format(idx));
        return fileid.toString();
    }
}
