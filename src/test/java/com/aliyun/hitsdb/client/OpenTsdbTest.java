package com.aliyun.hitsdb.client;

import com.aliyun.hitsdb.client.value.request.Query;
import com.aliyun.hitsdb.client.value.request.SubQuery;
import com.aliyun.hitsdb.client.value.response.QueryResult;
import com.aliyun.hitsdb.client.value.type.Aggregator;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

public class OpenTsdbTest {

    @Test
    public void testcount() throws UnknownHostException {
        HiTSDBConfig config = HiTSDBConfig.address("10.10.10.125", 4242).asyncPut(false).config();
        HiTSDB hiTSDB = HiTSDBClientFactory.connect(config);
//        Result result = hiTSDB.putSync(Point.metric("sys.opentsdb.rate").
//                tag("host", InetAddress.getLocalHost().getHostName())
//                .timestamp(System.currentTimeMillis())
//                .value("1")
//                .build());
        DateTime time = new DateTime();

        Query query = Query.timeRange(time.minus(1000L).toDate(), time.toDate()).sub(
                SubQuery
                        .metric("sys.cpu5.nice").aggregator(Aggregator.COUNT)
                        .build()
        ).build();


//        Query build = Query.timeRange(time.minusDays(10).toDate(), time.toDate())
//                .sub(
//                        SubQuery.metric("sys.cpu"+"0"+".nice").aggregator(Aggregator.COUNT).build()
//                )
//                .build();
//        List<QueryResult> results = hiTSDB.query(build);
//        System.out.println(result.toJSON());
        System.out.println("查询参数：" + query.toJSON());
        List<QueryResult> list = hiTSDB.query(query);
        int sum = 0;
        for (QueryResult queryResult : list) {
            for (Map.Entry<Long, Object> entry : queryResult.getDps().entrySet()) {
                sum += (int) entry.getValue();
            }
        }
        try {
            hiTSDB.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }
}
