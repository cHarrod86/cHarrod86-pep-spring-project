package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    /**
     * Custom JPQL query to return a list of accounts given an account id for the posted_by field
     * @param posted_by
     * @return
     */
    @Query("SELECT m FROM Message m WHERE m.posted_by = :posted_by")
    List<Message> getMessagesFromUser(@Param("posted_by")int posted_by);

    /**
     * Custom JPQL update command to update the message_text field of a specific message given a message_id
     * @param message_text
     * @param message_id
     * @return
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Message m SET message_text = :message_text WHERE message_id = :message_id")
    public int updateMessageTextByMessageId(@Param("message_text")String message_text, @Param("message_id")int message_id);
}