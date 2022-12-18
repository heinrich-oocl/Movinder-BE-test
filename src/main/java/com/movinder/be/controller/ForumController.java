package com.movinder.be.controller;

import com.movinder.be.controller.dto.AddChatRequest;
import com.movinder.be.entity.Cinema;
import com.movinder.be.entity.Food;
import com.movinder.be.entity.Message;
import com.movinder.be.entity.Room;
import com.movinder.be.exception.MalformedRequestException;
import com.movinder.be.service.ForumService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;

    public ForumController(ForumService forumService){
        this.forumService = forumService;
    }

    //add message to room
    @PutMapping("/room")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Message addMessage(@RequestBody AddChatRequest newMessage){
        return forumService.addMessage(newMessage);
    }

    @GetMapping("/room/{customerID}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Room> getRoomByCustomerId(@PathVariable String customerID){
        return forumService.getRoomByCustomerId(customerID);
    }

    @GetMapping("/room")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Message> getMessageByMovieId(@RequestParam(defaultValue = "") String movieID,
                                             @RequestParam(defaultValue = "") String roomID){

        if (roomID.equals("") && movieID.equals("")){
            throw new MalformedRequestException("Message request should contain either movieId or roomId as param, none provided");
        }
        if (!roomID.equals("") && !movieID.equals("")){
            throw new MalformedRequestException("Message request should contain either movieId or roomId as param, both provided");
        }
        if (!roomID.equals("")){
            return forumService.getRoomMessagesByRoomId(roomID);
        } else{
            return forumService.getRoomMessageByMovieId(movieID);
        }

    }

}
