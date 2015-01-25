/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.model;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.util.Pair;

/**
 *
 * @author 嘉平
 * @param <T>
 */
public class QueryQueue <T> implements Closeable {
    private final BlockingQueue<Pair<Boolean, T>> queue = new LinkedBlockingQueue();
    public QueryQueue(Responder responder) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            try {
                while (true) {
                    Pair<Boolean, T> query = queue.take();
                    if (query.getKey()) {
                        return;
                    }
                    responder.respond(query.getValue());
                }
            } catch (Exception e) {
            } 
        });
        service.shutdown();
    }
    public boolean offer(T t) {
        return queue.offer(new Pair(false, t));
    }
    public boolean add(T t) {
        return queue.add(new Pair(false, t));
    }
    public void put(T t) throws InterruptedException {
        queue.put(new Pair(false, t));
    }
    @Override
    public void close() throws IOException {
        queue.clear();
        queue.offer(new Pair(true, null));
    }
    
    
    public interface Responder <T> {
        public void respond(T t) throws Exception;
    }
}
