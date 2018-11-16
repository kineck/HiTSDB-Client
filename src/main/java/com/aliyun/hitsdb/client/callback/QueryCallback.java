package com.aliyun.hitsdb.client.callback;

import com.aliyun.hitsdb.client.value.request.Query;
import com.aliyun.hitsdb.client.value.response.QueryResult;

import java.util.List;

public abstract class QueryCallback extends AbstractCallback<Query, List<QueryResult>> {

}