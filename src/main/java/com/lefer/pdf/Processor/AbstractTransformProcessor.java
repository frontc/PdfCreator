package com.lefer.pdf.Processor;

/**
 * ${DESCRIPTION}
 *
 * @author web
 * @version 1.0
 * @since 2017/08/25 13:40
 */
public abstract class AbstractTransformProcessor {
    //将文件按照一定规则分发到对应的客户机
    public abstract boolean transformFile(String filePath);
    //将目录下的所有文件按照一定规则分发到对应的客户机
    public abstract boolean transformDir(String dirPath);
}
