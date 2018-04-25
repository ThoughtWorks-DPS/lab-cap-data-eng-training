package kafka;


import domain.StationStatus;
import domain.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Component
public class RetrieveSchedulerService {

    public static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Scheduled(initialDelay = 10000, fixedRate = 5000)
    public void ScheduledProducer() {
        logger.info("INSIDE SCHEDULED TASK");
        Feed feed = restTemplate.getForObject("https://gbfs.citibikenyc.com/gbfs/fr/station_status.json", Feed.class);

        if(feed.getData() != null) {
            logger.info("got station status master with {} status messages",feed.getData().getStations().size());
            for(StationStatus stationStatus: feed.getData().getStations()) {
                kafkaProducer.sendMessage(stationStatus);
            }
        }
        else {
            logger.error("got no data?");
        }


//        String sentTimeStr = dateFormat.format(new Date());
//        logger.info("INSIDE SCHEDULED TASK");
//        kafkaProducer.sendMessage(String.format("sent at :%s", sentTimeStr));
//        kafkaProducer.
    }
}
