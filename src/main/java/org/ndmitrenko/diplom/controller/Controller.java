package org.ndmitrenko.diplom.controller;

import org.ndmitrenko.diplom.service.AtCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    private final AtCommandService sendAtCommand;

    @Autowired
    public Controller(AtCommandService sendAtCommand) {
        this.sendAtCommand = sendAtCommand;
    }

    @GetMapping("/")
    public List<String> getStarted(){
//        sendAtCommand.createData();
        return sendAtCommand.getATAnswer("CellId.py");
    }
}
