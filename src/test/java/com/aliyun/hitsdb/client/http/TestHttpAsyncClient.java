package com.aliyun.hitsdb.client.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;
import org.junit.*;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TestHttpAsyncClient {

    private CloseableHttpAsyncClient httpclient;

    @Before
    public void init() {
        httpclient = HttpAsyncClients.createDefault();
    }

    @BeforeClass
    public static void initClass() {
    }

    @AfterClass
    public static void afterClass() {
    }

    @After
    public void after() {
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHttpSyncClient() throws IOException {

        // Start the client
        httpclient.start();

        // Execute request
        final HttpGet request1 = new HttpGet("http://www.apache.org/");
        Future<HttpResponse> future = httpclient.execute(request1, null);
        try {
            HttpResponse response1 = future.get();
            System.out.println(request1.getRequestLine() + "->" + response1.getStatusLine());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHttpAsyncClient() throws InterruptedException, IOException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();

        final CountDownLatch latch1 = new CountDownLatch(1);
        final HttpGet request2 = new HttpGet("http://www.apache.org/");
        httpclient.execute(request2, new FutureCallback<HttpResponse>() {

            public void completed(final HttpResponse response2) {
                latch1.countDown();
                System.out.println(request2.getRequestLine() + "->" + response2.getStatusLine());
            }

            public void failed(final Exception ex) {
                latch1.countDown();
                System.out.println(request2.getRequestLine() + "->" + ex);
            }

            public void cancelled() {
                latch1.countDown();
                System.out.println(request2.getRequestLine() + " cancelled");
            }

        });

        latch1.await();
    }

    @Test
    public void testHttpAsyncClient2() throws InterruptedException, IOException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        // In real world one most likely would also want to stream
        // request and response body content
        final HttpGet request2 = new HttpGet("http://www.apache.org/");
        final CountDownLatch latch2 = new CountDownLatch(1);
        final HttpGet request3 = new HttpGet("http://www.apache.org/");
        HttpAsyncRequestProducer producer3 = HttpAsyncMethods.create(request3);
        AsyncCharConsumer<HttpResponse> consumer3 = new AsyncCharConsumer<HttpResponse>() {

            HttpResponse response;

            @Override
            protected void onResponseReceived(final HttpResponse response) {
                this.response = response;
            }

            @Override
            protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
            }

            @Override
            protected void releaseResources() {
            }

            @Override
            protected HttpResponse buildResult(final HttpContext context) {
                return this.response;
            }

        };

        httpclient.execute(producer3, consumer3, new FutureCallback<HttpResponse>() {

            public void completed(final HttpResponse response3) {
                latch2.countDown();
                System.out.println(request2.getRequestLine() + "->" + response3.getStatusLine());
            }

            public void failed(final Exception ex) {
                latch2.countDown();
                System.out.println(request2.getRequestLine() + "->" + ex);
            }

            public void cancelled() {
                latch2.countDown();
                System.out.println(request2.getRequestLine() + " cancelled");
            }

        });

        latch2.await();
    }

}
