package org.ndmitrenko.diplom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ATServiceListener {
    private final Logger logger = LoggerFactory.getLogger(ATServiceListener.class);

    private final ThreadPoolTaskScheduler tpts;
    private final AtCommandService atCommandService;

    @Autowired
    public ATServiceListener(@Qualifier("dbScheduler") ThreadPoolTaskScheduler tpts, AtCommandService atCommandService) {
        this.tpts = tpts;
        this.atCommandService = atCommandService;
        startListen();
    }

    private void startListen(){
        logger.info("start listen ATService ");
        tpts.scheduleWithFixedDelay(()->{
            logger.info("polling ");

            atCommandService.getMainInfo("MainInfoAndNeighbors.py");
        } ,5000);
    }
}
