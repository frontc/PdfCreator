package com.lefer.pdf;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Scheduled(fixedDelay = 300000L)
    public void doSomething() {
        LoggerFactory.getLogger(this.getClass()).info(LocalTime.now().toString());
        List rw = jdbcTemplate.queryForList("exec USP_GET_PDF_BRJBXX");
        System.out.println(rw.size());
        for (int i = 0; i < rw.size(); i++) {
            Map userMap = (Map) rw.get(i);

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("USP_GET_PDF_BRZLXX");
            Map<String, Object> inParamMap = new HashMap<>();
            inParamMap.put("jzlsh", userMap.get("就诊流水唯一号"));
            inParamMap.put("yljgdm", userMap.get("医疗机构信息"));
            inParamMap.put("ksrq", "2016-01-01 08:59:00.000");
            inParamMap.put("jsrq", "2016-10-01 08:59:00.000");
            SqlParameterSource in = new MapSqlParameterSource(inParamMap);
            Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
            System.out.println(simpleJdbcCallResult.size());
        }
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
