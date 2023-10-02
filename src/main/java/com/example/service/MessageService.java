package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    // @Autowired
    private MessageRepository messageRepository;

    /**
     * Constructor with a MessageRepository param
     * 
     * @param messageRepository
     */
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Service method that returns a newly persisted Message object from the database
     * @param message
     * @return
     */
    public Message creatMessage(Message message) {
        return this.messageRepository.save(message);
    }

    /**
     * Service method that returns a list of all existing message entries from the database, list will be empty if no message entries exist
     * @return
     */
    public List<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }

    /**
     * Service method that returns a Message object if a message with the param id exists, if not returns null
     * @param id
     * @return
     */
    public Message getMessageById(int id) {
        Optional<Message> optMessage = this.messageRepository.findById(id);

        if (optMessage.isPresent())
            return optMessage.get();

        else
            return null;
    }

    /**
     * Service method that deletes an entry in the database with given id
     * Returns 1 if successful
     * Returns 0 if any exceptions caught
     * @param id
     * @return
     */
    public int deleteMessageById(int id) {
        try {
            messageRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Service method that returns the number of rows updated in the table for a message entry with a message_id that matches the id param
     * Returns 0 if no rows updated/update unsuccessful
     * Returns 1 if successful update
     * @param id
     * @param message_text
     * @return
     */
    public int updateMessageTextById(int id, String message_text) {
        return messageRepository.updateMessageTextByMessageId(message_text, id);
    }

    /**
     * Service method that returns a list of all message entries in the database that have a posted_by value that matches the param id
     * Returns an empty list if no messages have the posted_by value
     * @param id
     * @return
     */
    public List<Message> getMessagesFromUserId(int id) {
        return this.messageRepository.getMessagesFromUser(id);
    }

    /**
     * Service method to return true/false if a message entry exists in the database or not
     * @param id
     * @return
     */
    public boolean checkMessageExistsById(int id){
        return messageRepository.existsById(id);
    }


}
