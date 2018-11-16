package com.aliyun.hitsdb.client.callback;

import com.aliyun.hitsdb.client.value.request.Point;
import com.aliyun.hitsdb.client.value.response.batch.DetailsResult;

import java.util.List;

public abstract class BatchPutDetailsCallback extends AbstractBatchPutCallback<DetailsResult> {

    @Override
    public abstract void response(String address, List<Point> points, DetailsResult result);

}
