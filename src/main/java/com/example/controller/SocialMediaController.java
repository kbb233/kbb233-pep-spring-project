package com.example.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("")
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    //register
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        if(accountService.findAccountByUsername(account.getUsername())!= null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if(account.getUsername().isBlank() || account.getPassword().length()<4){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Account registerAccount = accountService.register(account);
        return ResponseEntity.ok(registerAccount);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account loginAccount = accountService.findAccountByUsername(account.getUsername());
        if(loginAccount == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(!loginAccount.getPassword().equals(account.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(loginAccount);
    }

    //create message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (accountService.findAccountByAccountId(message.getPostedBy())==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Message createMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createMessage);
    }

    //get all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    //get message by message id
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageByMessageId(messageId);
        return ResponseEntity.ok(message);
    }

    //delete message
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        Message message = messageService.getMessageByMessageId(messageId);
        if(message!=null){
            messageService.deleteMessage(messageId);
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.status(200).build();
    }

    //update message
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody HashMap<String, String> updates) {
        String messageText = updates.get("messageText");
        Message existMessage = messageService.getMessageByMessageId(messageId);
        if (existMessage != null && !messageText.isBlank() && messageText.length() <= 255) {
            existMessage.setMessageText(messageText);
            messageService.updateMessage(existMessage);
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //get all messages by account id
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId) {
        return ResponseEntity.ok(messageService.getMessagesByAccountId(accountId));
    }

}
