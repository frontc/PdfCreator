package com.lefer.pdf;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * 主调度器
 *
 * @author web
 * @version 1.0
 * @since 2017/08/25 13:12
 */
@Component
public class MainSchedule {
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedDelay = 3000L)
    public void doSomething() {
        LoggerFactory.getLogger(this.getClass()).info(LocalTime.now().toString());
        //TODO:获取在院患者列表
        //TODO:根据在院患者，调用PROC，获取CDR资料
        //TODO:写入PDF
        //TODO:PDF加密
        //TODO:PDF分发
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
