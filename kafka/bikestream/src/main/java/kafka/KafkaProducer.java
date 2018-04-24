package kafka;

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

    @Value( "${write.topic}")
    private String writeTopic;

    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessage(final String message) {
        ListenableFuture<SendResult<String,String>> future = this.template.send(writeTopic,message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Success sending message");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Failure to send message");
            }

        });
    }

}
