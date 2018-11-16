package com.aliyun.hitsdb.client.callback.http;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.util.concurrent.atomic.AtomicInteger;

public class BaseHttpFutrueCallback implements FutureCallback<HttpResponse> {

    private final AtomicInteger unCompletedTaskNum;
    private final FutureCallback<HttpResponse> futureCallback;

    public BaseHttpFutrueCallback(AtomicInteger unCompletedTaskNum, FutureCallback<HttpResponse> futureCallback) {
        super();
        this.unCompletedTaskNum = unCompletedTaskNum;
        this.futureCallback = futureCallback;
    }

    @Override
    public void completed(HttpResponse result) {
        futureCallback.completed(result);
        // 任务处理完毕，再减数
        unCompletedTaskNum.decrementAndGet();
    }

    @Override
    public void failed(Exception ex) {
        futureCallback.failed(ex);
        // 任务处理完毕，再减数
        unCompletedTaskNum.decrementAndGet();
    }

    @Override
    public void cancelled() {
        futureCallback.cancelled();
        // 任务处理完毕，再减数
        unCompletedTaskNum.decrementAndGet();
    }

}
