package com.lefer.pdf.Processor;

import com.lefer.pdf.Entity.Patient;
import com.lefer.pdf.Entity.PatientDoc;

import java.util.List;

/**
 * 数据库相关处理类
 *
 * @author web
 * @version 1.0
 * @since 2017/08/25 13:30
 */
public abstract class AbstractDBProcessor {
    //获取在院患者列表
    public abstract List<Patient> getPatientList();
    //获取患者信息
    public abstract PatientDoc getPatientInfo(Patient patient);
}
