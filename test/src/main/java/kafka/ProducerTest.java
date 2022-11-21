package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerTest {
    private KafkaProducer<String,String> producer;
    public ProducerTest(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("acks","all");
        properties.put("retries","0");
        properties.put("batch.size","16384");
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        this.producer = new KafkaProducer<String, String>(properties);
    }

    public static void main(String[] args) {
        ProducerTest producerTest = new ProducerTest();
        for(int i =0; i < 10 ; i++){
           producerTest.producer.send(new ProducerRecord<String,String>("test","hello-"+i));
        }
    }
}
