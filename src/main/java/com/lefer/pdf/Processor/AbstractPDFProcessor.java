package com.lefer.pdf.Processor;

import com.lefer.pdf.Entity.PatientDoc;

/**
 * ${DESCRIPTION}
 *
 * @author web
 * @version 1.0
 * @since 2017/08/25 13:36
 */
public abstract class AbstractPDFProcessor {
    //生成PDF文件,返回PDF的path
    public abstract String genPDF(PatientDoc patientDoc);
    //加密PDF
    public abstract String encyptPDF(String orinPDF);
}
