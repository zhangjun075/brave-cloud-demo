package com.brave.controller;

import com.brave.dto.CollectInfoDTO;
import com.brave.service.DemoService;
import com.brave.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class Demo {

    @Autowired DemoService demoService;

    @GetMapping("/demos")
    public String demo(){
        String traceId = CommonUtils.generateUUID();
        CommonUtils.traceId.set(traceId);

        String spanId = CommonUtils.generateUUID();

        CollectInfoDTO collectInfoDTO = CollectInfoDTO.builder().clazz(this.getClass().getName()).method(
            CommonUtils.getMethodName()).sr(CommonUtils.getNowDateTimeString()).spanId(spanId).traceId(CommonUtils.traceId.get()).build();
        log.info("this is demo controller begin:{}",collectInfoDTO);
        CommonUtils.setParentSpanId("demo",spanId);

        demoService.demo();

        collectInfoDTO.setSs(CommonUtils.getNowDateTimeString());
        CommonUtils.setLocalCollects(collectInfoDTO);
        print();
        return "demo";
    }

    public void print() {
        CommonUtils.localCollects.get().stream().forEach(collectInfoDTO -> log.info("==>{}",collectInfoDTO));
    }
}
