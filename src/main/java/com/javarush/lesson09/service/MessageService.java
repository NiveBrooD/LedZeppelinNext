package com.javarush.lesson09.service;

import com.javarush.lesson09.model.Message;
import com.javarush.lesson09.repository.Repository;

import java.util.Map;

public class MessageService implements Rest<Message> {

    private final Repository<Message> repository;

    public MessageService(Repository<Message> repository) {
        this.repository = repository;
        this.repository.saveOrUpdate(new Message("Hello everybody!!!", "Ivan"));
        this.repository.saveOrUpdate(new Message("Hi!", "Joe"));
        this.repository.saveOrUpdate(new Message("How are you guys?", "Maria"));
        this.repository.saveOrUpdate(new Message("Thank you, we're all right", "Админ"));
    }


    @Override
    public Map<Long, Message> getAll() {
        return repository.getAll();
    }

    @Override
    public Message get(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean post(Message message) {
        message.setId(0L);
        Message update = repository.saveOrUpdate(message);
        return update != null;
    }

    @Override
    public boolean put(Message message) {
        Message update = repository.saveOrUpdate(message);
        return update != null;
    }

    @Override
    public boolean delete(Message message) {
        return repository.delete(message);
    }
}
