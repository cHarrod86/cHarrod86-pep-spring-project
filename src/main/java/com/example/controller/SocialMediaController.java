package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    
    /**
     * Handles the post request to create a new Account entry in the database
     * Returns a null Account if the password is too short or is empty with a 400 status code
     * Returns a null Account if the username already exists with a 409 status code
     * Returns the created Account if the creation of the entry in the database is successful with a 200 status code
     * @param account
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<Account> createAccountHandler(@RequestBody Account account){
        if(account.getPassword().isEmpty() || account.getPassword().length() < 4){
            return new ResponseEntity<>(null,null, 400);
        }
        
        else if(accountService.checkAccountUsernameExists(account.getUsername())){
            return new ResponseEntity<>(null, null, 409);
        }
        else return new ResponseEntity<>(accountService.createAccount(account),null , 200);
    }


    /**
     * Handles the post mapping for the login feature
     * Returns the Account record if the username and password match an existing record in the database with a status code of 200
     * Returns the Account record if the login was unsuccessful with a 401 status code
     * @param account
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<Account> loginHandler(@RequestBody Account account){
        Account checkedAccount = accountService.getAccountByUsername(account.getUsername());

        if(checkedAccount == null || (!checkedAccount.getUsername().equals(account.getUsername()) || !checkedAccount.getPassword().equals(account.getPassword()))){
            return new ResponseEntity<Account>(checkedAccount, HttpStatus.UNAUTHORIZED);
        }
        else return ResponseEntity.ok(checkedAccount); 
        
    }

    /**
     * Handles the post request to create a new Message record in the database
     * Returns the created record if successful with a status code of 200
     * Returns null if the record cannot be created with a status code of 400
     * @param message
     * @return
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessageHandler(@RequestBody Message message){
        if(message.getMessage_text().length() > 254 || message.getMessage_text().isBlank() || !accountService.checkAccountIdExists(message.getPosted_by())){
            return new ResponseEntity<>(null, null, 400);
        }
        else{
            return ResponseEntity.ok(messageService.creatMessage(message));
        }
    }

    /**
     * Handles the get request to return all messages returning a list of all entries in the message table and will be empty if no records exist
     * @return
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler(){
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /**
     * Handles the get request to return a Message entry in the database given the message id as a PathVariable
     * @param id
     * @return
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageByIdHandler(@PathVariable("message_id") int id){
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    /**
     * Handles the delete request for a Message in the database given the message id as a PathVariable
     * @param id
     * @return
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByIdhandler(@PathVariable("message_id") int id){
        return ResponseEntity.ok(messageService.deleteMessageById(id));
    }


    /**
     * handles the patch request when given the path variable for a message id and the RequestBody for the message text
     * Returns a 200 status code and the number of rows updated if successfully updated
     * Returns a 400 status code and a null body if the update was unsuccessful due to the text being blank, too long, the message not existing, etc
     * @param message_id
     * @param message_text
     * @return
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageTextHandler(@PathVariable("message_id")int message_id, @RequestBody String message_text){
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(message_text.toString());
                String message = jsonNode.get("message_text").asText();

                int rowsUpdated = messageService.updateMessageTextById(message_id, message);

                if(message.isBlank()) return ResponseEntity.badRequest().body(null);
                if(rowsUpdated == 1) return ResponseEntity.ok(rowsUpdated);
                else return ResponseEntity.badRequest().body(null);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(null);
            }
    }


    /**
     * handles the get request that returns a list of messages from a specified account id
     * @param accountId
     * @return 
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesFromAUser(@PathVariable("account_id") int accountId){
        return ResponseEntity.ok(messageService.getMessagesFromUserId(accountId));
    }
}
