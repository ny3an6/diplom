package org.ndmitrenko.diplom.controller;

import org.ndmitrenko.diplom.dto.response.MainInfo;
import org.ndmitrenko.diplom.service.AtCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final AtCommandService sendAtCommand;

    @Autowired
    public Controller(AtCommandService sendAtCommand) {
        this.sendAtCommand = sendAtCommand;
    }

    @GetMapping("/")
    @CrossOrigin
    public MainInfo getStarted(){
//        sendAtCommand.createData();
        return sendAtCommand.getATAnswer("MainInfo.py");
    }
}
