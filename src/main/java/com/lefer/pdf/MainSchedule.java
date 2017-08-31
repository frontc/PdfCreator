package com.lefer.pdf;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
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
            Map<String, Object> inParamMap = new HashMap<String, Object>();
            inParamMap.put("jzlsh", userMap.get("就诊流水唯一号"));
            inParamMap.put("yljgdm", userMap.get("医疗机构信息"));
            inParamMap.put("ksrq", "2016-01-01 08:59:00.000");
            inParamMap.put("jsrq", "2016-10-01 08:59:00.000");
            SqlParameterSource in = new MapSqlParameterSource(inParamMap);
            Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
            System.out.println(simpleJdbcCallResult.size());

//            String sql_text = "exec USP_GET_PDF_BRZLXX :jzlsh,:yljgdm,:ksrq,:jsrq";
////            String parms = "exec USP_GET_PDF_BRZLXX \"" + userMap.get("就诊流水唯一号")  + "\",\""+userMap.get("医疗机构信息")
////                    +"\",\"2016-01-01 08:59:00.000\",\"2016-01-01 08:59:00.000\"";
////            List rw1 = jdbcTemplate.queryForList(parms);
//            MapSqlParameterSource parameters = new MapSqlParameterSource();
//            parameters.addValue("jzlsh", userMap.get("就诊流水唯一号"));
//            parameters.addValue("yljgdm", userMap.get("医疗机构信息"));
//            parameters.addValue("ksrq", "2016-01-01 08:59:00.000");
//            parameters.addValue("jsrq", "2016-10-01 08:59:00.000");
//
//            NamedParameterJdbcTemplate template =
//                    new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
//            List rw1 = template.queryForList(sql_text, parameters);
//            System.out.println(rw1.size());
//            for (int j = 0; j < rw1.size(); i++) {
//                Map userMap1 = (Map) rw1.get(i);
//                System.out.println(userMap1.get("xmlb"));
//                System.out.println(userMap1.get("xmdm"));
//                System.out.println(userMap1.get("xmmc"));
//            }
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
