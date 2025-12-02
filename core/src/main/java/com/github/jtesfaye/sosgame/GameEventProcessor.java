package com.github.jtesfaye.sosgame;

import com.github.jtesfaye.sosgame.GameEvent.*;
import com.github.jtesfaye.sosgame.Replay.EventReaderWriter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final boolean isReplay;

    @Setter
    private EventReaderWriter recorder;

    public GameEventProcessor(ConcurrentLinkedQueue<GameEvent> queue, boolean replayMode) {

        subscribers = new HashMap<>();
        this.queue = queue;
        keep_going = true;
        isReplay = replayMode;
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
            if (isReplay) {
                if (e.getClass() == StartReplayEvent.class) {
                    cond.signalAll();
                }
                return;
            }

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

                    if (!isReplay) {
                        writeEvents();
                    }
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

                if (isReplay && event.getClass() == PieceSetEvent.class) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Problem");
                    }
                } else {
                    recordEvent(event);
                }

                ((Consumer<GameEvent>) c).accept(event);

            }
        }
    }

    public void addReplayEvents(List<GameEvent> events) {

        queue.addAll(events);
    }

    private void recordEvent(GameEvent e) {
        if (recorder == null) {
            return;
        }

        recorder.addEvent(e);
    }

    private void writeEvents() {

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter f= DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String fileName = time.format(f) + ".json";
        try {
            recorder.writeEvents(fileName);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
