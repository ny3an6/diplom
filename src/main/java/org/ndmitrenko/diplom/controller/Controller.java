package org.ndmitrenko.diplom.controller;

import org.ndmitrenko.diplom.dto.response.BaseStationInfoDto;
import org.ndmitrenko.diplom.service.AtCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    @CrossOrigin
    public BaseStationInfoDto getMainInfo(){
//        sendAtCommand.createData();
        return sendAtCommand.getMainInfo("MainInfoAndNeighbors.py");
    }

    @GetMapping("/neighbors")
    @CrossOrigin
    public List<BaseStationInfoDto> getNeighbors(){
//        sendAtCommand.createData();
        return sendAtCommand.getNeighborsInfo("MainInfoAndNeighbors.py");
    }
}
