package Store.Altex.controllers;

import Store.Altex.models.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@Controller
@CrossOrigin(origins = {"http://localhost:3000/","http://127.0.0.1:1080/"})
public class ChatController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(new Date()); // Set the current timestamp
        return chatMessage;
    }
}