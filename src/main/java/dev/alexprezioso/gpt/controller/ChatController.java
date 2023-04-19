package dev.alexprezioso.gpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alexprezioso.gpt.data.*;
import dev.alexprezioso.gpt.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    @Autowired
    private ChatGPTService chatGPTService;

    @Autowired
    private ChatManager chatManager;

    @Autowired
    private UserManager userManager;


    @Value("${gpt.model}")
    private String gptmodel;



    @GetMapping("/chat")
    public String chat(Principal principal, Model model) {
        String userId = principal.getName();
        User user = userManager.getUser(userId);
        if(user.getId() != null) {
            Chat chat = chatManager.getChat(user.getName());
            List<MessageDTO> messageDTOs = chat.getMessages().stream()
                    .map(message -> new MessageDTO(message.getRole(), message.getContent()))
                    .collect(Collectors.toList());
            model.addAttribute("messages", messageDTOs);
        } else {
            model.addAttribute("messages", new ArrayList<>());
        }
        return "chat";
    }

    @PostMapping("/chat")
    public String sendMessage(@RequestParam("context") String context, @RequestParam("message") String messageText, Principal principal, Model model) {
        try {
            if(context == null) {
                context = "1";
            }
            String userName = principal.getName();
            ChatGPTRequest request = new ChatGPTRequest();
            request.setMessages(chatGPTService.getHistory(userName, messageText, Integer.parseInt(context)));
            request.setModel(gptmodel);
            ChatGPTResponse response = chatGPTService.chat(request);
            model.addAttribute("conversation", chatGPTService.saveConversation(userName, response));
        } catch (ResourceAccessException e) {
            model.addAttribute("error", "Unable to connect to the GPT API. Please try again later.");
        } catch (JsonProcessingException e) {
            model.addAttribute("error", "An error occurred while processing the API response. Please try again.");
        }
        return "chat";
    }


}
