package com.github.jtesfaye.sosgame.util;

import com.github.jtesfaye.sosgame.GameEvent.*;
import com.github.jtesfaye.sosgame.GameIO.InputRouter;
import com.github.jtesfaye.sosgame.GameLogic.GameLogic;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class GameEventProcessor {

    private final Queue<GameEvent> queue;
    boolean keep_going;

    private final Lock lock = new ReentrantLock();
    private final Condition cond = lock.newCondition();
    private Map<Class<? extends GameEvent>, List<Consumer<?>>> subscribers;
    private boolean endGame = false;

    public GameEventProcessor(ConcurrentLinkedQueue<GameEvent> queue) {

        subscribers = new HashMap<>();
        this.queue = queue;
        keep_going = true;

    }

    public <T extends GameEvent> void addSubscriber(Class<T> EventType, Consumer<T> callback) {

        if (!subscribers.containsKey(EventType)) {
            subscribers.put(EventType, new ArrayList<>());
            subscribers.get(EventType).add(callback);

        } else if (subscribers.containsKey(EventType) && !subscribers.get(EventType).contains(callback)) {
            subscribers.get(EventType).add(callback);
        }
    }

    public void addEvent(GameEvent e) {
        lock.lock();
        try {
            queue.add(e);
            cond.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public void run() throws InterruptedException {

        while (true) {

            try {

                lock.lock();
                while (keep_going && queue.isEmpty()) {
                    cond.await();
                }

                while (!queue.isEmpty()) {
                    GameEvent e = queue.remove();
                    processEvent(e);
                }

                if (!keep_going) {
                    break;
                }

            } finally {
                lock.unlock();
            }
        }
    }

    public void stopRunning() {
        lock.lock();
        keep_going = false;
        cond.signal();
        lock.unlock();
    }

    private void processEvent(GameEvent event) {

        if (event == null || endGame) {
            return;
        }

        List<Consumer<?>> consumers = subscribers.get(event.getClass());
        if (consumers != null) {
            for (Consumer<?> c : consumers) {

                if (c.getClass().isInstance(EndGameEvent.class)) {
                    endGame = true;
                }
                ((Consumer<GameEvent>) c).accept(event);
            }
        }
    }
}
