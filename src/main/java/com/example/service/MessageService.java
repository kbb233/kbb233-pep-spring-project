package com.example.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getMessagesByAccountId(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }

    public Message getMessageByMessageId(Integer messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    public void updateMessage(Message message) {
        messageRepository.save(message);
    }
}
