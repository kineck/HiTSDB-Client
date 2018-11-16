package com.aliyun.hitsdb.client.callback;

import com.aliyun.hitsdb.client.value.request.Point;
import com.aliyun.hitsdb.client.value.response.batch.SummaryResult;

import java.util.List;

public abstract class BatchPutSummaryCallback extends AbstractBatchPutCallback<SummaryResult> {

    @Override
    public abstract void response(String address, List<Point> points, SummaryResult result);
}
