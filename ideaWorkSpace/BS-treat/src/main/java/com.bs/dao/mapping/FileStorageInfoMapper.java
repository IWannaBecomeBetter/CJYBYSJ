package com.bs.dao.mapping;

import com.bs.dao.entity.FileStorageInfo;
import com.bs.dao.entity.FileStorageInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileStorageInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int countByExample(FileStorageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int deleteByExample(FileStorageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int insert(FileStorageInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int insertSelective(FileStorageInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    List<FileStorageInfo> selectByExample(FileStorageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    FileStorageInfo selectByPrimaryKey(String fileId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FileStorageInfo record, @Param("example") FileStorageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FileStorageInfo record, @Param("example") FileStorageInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FileStorageInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_storage_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FileStorageInfo record);
}