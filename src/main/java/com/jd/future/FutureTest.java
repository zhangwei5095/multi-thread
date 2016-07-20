package com.jd.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by caozhifei on 2016/7/10.
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<AsyncResult> callable = new Callable() {
            @Override
            public AsyncResult call() throws Exception {
                return new AsyncResult(1,2);
            }
        };
        FutureTask<AsyncResult> future = new FutureTask<AsyncResult>(callable);
        future.run();
        System.out.println(future.get());
    }
    static class AsyncResult {
        private int id;
        private int result;

        public AsyncResult(int id, int result) {
            this.id = id;
            this.result = result;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("AsyncResult{");
            sb.append("id=").append(id);
            sb.append(", result=").append(result);
            sb.append('}');
            return sb.toString();
        }
    }
}
