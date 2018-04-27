package com.brave.service;

import com.brave.dto.CollectInfoDTO;
import com.brave.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoService {

//    @HystrixCommand(fallbackMethod = "hcd")
    public String demo(){
        String parentSpanId = CommonUtils.getParentSpanId(CommonUtils.getMethodName());
        String traceId = CommonUtils.traceId.get();
        CollectInfoDTO collectInfoDTO = CollectInfoDTO.builder().clazz(this.getClass().getName()).method(
            CommonUtils.getMethodName()).sr(CommonUtils.getNowDateTimeString()).spanId(CommonUtils.generateUUID()).traceId(CommonUtils.traceId.get()).parentSpandId(parentSpanId).build();
        log.info("process.......");
        collectInfoDTO.setSs(CommonUtils.getNowDateTimeString());
        CommonUtils.setLocalCollects(collectInfoDTO);
        return "demo";
    }
    public String hcd(){
        return "hcd";
    }
}
