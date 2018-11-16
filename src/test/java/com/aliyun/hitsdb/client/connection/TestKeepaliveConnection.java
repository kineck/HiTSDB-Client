package com.aliyun.hitsdb.client.connection;

import com.aliyun.hitsdb.client.HiTSDB;
import com.aliyun.hitsdb.client.HiTSDBClientFactory;
import com.aliyun.hitsdb.client.HiTSDBConfig;
import com.aliyun.hitsdb.client.callback.BatchPutCallback;
import com.aliyun.hitsdb.client.exception.http.HttpClientInitException;
import com.aliyun.hitsdb.client.util.UI;
import com.aliyun.hitsdb.client.value.Result;
import com.aliyun.hitsdb.client.value.request.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TestKeepaliveConnection {
    HiTSDB tsdb;


    @Before
    public void init() throws HttpClientInitException {
        UI.pauseStart();

        HiTSDBConfig config = HiTSDBConfig
                .address(".address(", 8242)
                .httpConnectionPool(1)
                .httpConnectTimeout(200)
                .batchPutSize(100).listenBatchPut(new BatchPutCallback() {
                    final AtomicLong num = new AtomicLong();

                    @Override
                    public void response(String address, List<Point> input, Result output) {
                        long afterNum = num.addAndGet(input.size());
                        System.out.println("成功处理" + input.size() + ",已处理" + afterNum);
                    }

                    @Override
                    public void failed(String address, List<Point> input, Exception ex) {
                        ex.printStackTrace();
                        long afterNum = num.addAndGet(input.size());
                        System.out.println("失败处理" + input.size() + ",已处理" + afterNum);
                    }
                }).config();

        tsdb = HiTSDBClientFactory.connect(config);
    }

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            Point point = createPoint(i % 4);
            tsdb.put(point);
        }
    }

    @After
    public void end() throws IOException {
        tsdb.close();
    }

    public Point createPoint(int tag) {
        int t = (int) (System.currentTimeMillis() / 1000);
        double random = Math.random();
        double value = Math.round(random * 1000) / 1000.0;
        return Point.metric("test-performance").tag("tag", String.valueOf(tag)).value(t, value).build();
    }
}
