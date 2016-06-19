package test_amqp;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.support.converter.MessageConverter;

public class MessageHelper {

    public static  Message buildMessage(Object object, MessageConverter messageConverter) {
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        return messageConverter.toMessage(object, messageProperties);
    }
}
