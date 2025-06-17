package com.javarush.lesson09.repository;

import com.javarush.lesson09.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MessageRepository implements Repository<Message> {

    private final Map<Long, Message> map = new HashMap<>();
    public static final AtomicLong id = new AtomicLong();

    @Override
    public Message findById(Long id) {
        return map.get(id);
    }

    @Override
    public Message saveOrUpdate(Message message) {
        if (!map.containsKey(message.getId())) {
            message.setId(id.incrementAndGet());
        }
        map.put(message.getId(), message);
        return message;
    }

    @Override
    public boolean delete(Message message) {
        return map.remove(message.getId()) != null;
    }

    @Override
    public Map<Long, Message> getAll() {
        return map;
    }
}
