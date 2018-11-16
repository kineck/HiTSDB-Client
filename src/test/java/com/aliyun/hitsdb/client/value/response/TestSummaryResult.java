package com.aliyun.hitsdb.client.value.response;

import com.alibaba.fastjson.JSON;
import com.aliyun.hitsdb.client.value.response.batch.SummaryResult;
import org.junit.Assert;
import org.junit.Test;


public class TestSummaryResult {

    @Test
    public void testDeserialize() {
        String jsonStr = "{\"failed\": 13, \"success\": 12}";
        SummaryResult summaryResult = JSON.parseObject(jsonStr, SummaryResult.class);
        int failed = summaryResult.getFailed();
        int success = summaryResult.getSuccess();
        Assert.assertEquals(success, 12);
        Assert.assertEquals(failed, 13);
    }
}
