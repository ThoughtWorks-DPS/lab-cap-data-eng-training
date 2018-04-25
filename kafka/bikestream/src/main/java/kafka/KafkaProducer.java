package kafka;

import domain.StationStatus;
import org.apache.kafka.common.metrics.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {

    public static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Value( "${write.topic}")
    private String writeTopic;

    @Autowired
    private KafkaTemplate<String, StationStatus> template;

//    public void sendMessage(final String message) {
//        ListenableFuture<SendResult<String,String>> future = this.template.send(writeTopic,message);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
//                logger.info("Success sending message");
//            }
//
//            @Override
//            public void onFailure(Throwable ex) {
//                logger.error("Failure to send message");
//            }
//
//        });
//    }

    public void sendMessage(final StationStatus stationStatus) {

        ListenableFuture<SendResult<String, StationStatus>> future = this.template.send(writeTopic,stationStatus.getStationId(),stationStatus);
        future.addCallback(new ListenableFutureCallback<SendResult<String, StationStatus>>() {

            @Override
            public void onSuccess(SendResult<String, StationStatus> result) {
                logger.info("Success sending message for station id:{}",stationStatus.getStationId());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failure to send message");
            }

        });
    }

}
