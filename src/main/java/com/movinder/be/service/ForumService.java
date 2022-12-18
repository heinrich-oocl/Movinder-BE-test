package com.movinder.be.service;

import com.movinder.be.controller.dto.AddChatRequest;
import com.movinder.be.entity.Message;
import com.movinder.be.entity.Room;
import com.movinder.be.exception.IdNotFoundException;
import com.movinder.be.repository.MessageRepository;
import com.movinder.be.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final CustomerService customerService;

    public ForumService(MessageRepository messageRepository, RoomRepository roomRepository, CustomerService customerService){
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.customerService = customerService;
    }

    // add Chat room
    public Room addChatRoom(String movieId){
        return roomRepository.save(new Room(movieId));
    }

    public Room findRoomById(String roomId){
        Utility.validateID(roomId);
        return roomRepository.findById(roomId).orElseThrow(() -> new IdNotFoundException("Room"));
    }

    // add Chat
    public Message addMessage(AddChatRequest newMessage){
        customerService.findByCustomerId(newMessage.getCustomerId()); //validate ID
        Message savedMessage = messageRepository.save(new Message(newMessage.getCustomerId(), newMessage.getMessage()));

        Room room = findRoomById(newMessage.getRoomId());
        room.addMessageId(savedMessage.getMessageId());
        room.addCustomerId(newMessage.getCustomerId());
        roomRepository.save(room);

        return  savedMessage;
    }

    public Message findMessageById(String messageId){
        return messageRepository.findById(messageId).orElseThrow(() -> new IdNotFoundException("Message"));
    }

    public List<Message> getRoomMessageByMovieId(String movieId){
        Utility.validateID(movieId);
        Room room = roomRepository.findByMovieId(movieId).orElseThrow(() -> new IdNotFoundException("Movie"));
        return getRoomMessagesByRoomId(room.getRoomId());
    }

    public List<Message> getRoomMessagesByRoomId(String roomId){
        Utility.validateID(roomId);
        Room room = findRoomById(roomId);
        return room.getMessageIds()
                .stream()
                .map(this::findMessageById)
                .sorted(Comparator.comparing(Message::getEpochTime))
                .collect(Collectors.toList());
    }

    //find engaged room (discussions)
    public List<Room> getRoomByCustomerId(String customerId){
        return roomRepository.findByCustomerIdsContaining(customerId);
    }


}
