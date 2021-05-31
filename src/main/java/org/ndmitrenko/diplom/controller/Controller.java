package org.ndmitrenko.diplom.controller;

import org.ndmitrenko.diplom.service.SendAtCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class Controller {

    private final SendAtCommand sendAtCommand;

    @Autowired
    public Controller(SendAtCommand sendAtCommand) {
        this.sendAtCommand = sendAtCommand;
    }

    @GetMapping("/")
    public List<String> getStarted(){
        return sendAtCommand.getATAnswer("Hello.py");
    }
}
