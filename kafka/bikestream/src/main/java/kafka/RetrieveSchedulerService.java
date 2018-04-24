package kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class RetrieveSchedulerService {

    public static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private KafkaProducer kafkaProducer;

    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void ScheduledProducer() {
        String sentTimeStr = dateFormat.format(new Date());
        logger.info("INSIDE SCHEDULED TASK");
        kafkaProducer.sendMessage(String.format("sent at :%s", sentTimeStr));
    }
}
