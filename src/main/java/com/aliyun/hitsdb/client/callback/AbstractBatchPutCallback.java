package com.aliyun.hitsdb.client.callback;

import com.aliyun.hitsdb.client.value.Result;
import com.aliyun.hitsdb.client.value.request.Point;

import java.util.List;

public abstract class AbstractBatchPutCallback<R extends Result> extends AbstractCallback<List<Point>, R> {
}