package test_amqp;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;

public class MessageHelper {

    public static  Message buildMessage(Object object, MessageConverter messageConverter) {
        MessageProperties messageProperties = new MessageProperties();
        return messageConverter.toMessage(object, messageProperties);
    }
}
