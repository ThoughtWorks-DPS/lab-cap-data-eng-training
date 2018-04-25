package kafka;

import domain.StationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {

    public static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Value("${write.topic}")
    private String writeTopic;

    @Autowired
    private KafkaTemplate<String, StationStatus> template;

    public void sendMessage(final StationStatus stationStatus) {

        ListenableFuture<SendResult<String, StationStatus>> future = this.template.send(writeTopic, stationStatus.getStationId(), stationStatus);
        future.addCallback(new ListenableFutureCallback<SendResult<String, StationStatus>>() {

            @Override
            public void onSuccess(SendResult<String, StationStatus> result) {
                logger.info("Success sending message for station id:{}", stationStatus.getStationId());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failure to send message");
            }

        });
    }

}
