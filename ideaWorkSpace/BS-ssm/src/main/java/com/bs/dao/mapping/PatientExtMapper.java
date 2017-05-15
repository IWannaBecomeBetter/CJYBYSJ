package com.bs.dao.mapping;

import com.bs.dao.entity.PatientExt;
import com.bs.dao.entity.PatientExtExample;

import java.util.List;

/**
 * Created by 陈继赟 on 2016/6/10.
 */
public interface PatientExtMapper {
    /**
     * 查询根据关系查找患者信息
     * @return
     */
    int qryPatientExtNum(PatientExtExample extExample);

    /**
     * 查询根据关系查找患者信息
     * @return
     */
    List<PatientExt> qryPatientExtList(PatientExtExample extExample);
}
