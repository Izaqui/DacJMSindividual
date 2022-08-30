package Exempl;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.MessageListener;

import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.ActivationConfigProperty;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.annotation.Resource;
import java.util.logging.Logger;


@MessageDriven(mappedName = "jms/Queue")
public class SimpleMessageBean implements MessageListener {
    static final Logger logger = Logger.getLogger("SimpleMessageBean");
    @Resource
    private MessageDrivenContext mdc;

    public SimpleMessageBean() {
    }

    public void onMessage(Message inMessage) {
        TextMessage msg = null;

        try {
            if (inMessage instanceof TextMessage) {
                msg = (TextMessage) inMessage;
                logger.info("MESSAGE BEAN: Message received: " + msg.getText());
            } else {
                logger.warning("Message of wrong type: " + inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

    @Override
    public boolean processMessage(Message message) {
        return MessageListener.super.processMessage(message);
    }

    @Override
    public void error(Throwable ex) {

    }
}