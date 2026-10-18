package jms;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.*;

@Stateless
public class JmsService {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    ConnectionFactory cf;
    
    @Resource(lookup = "novi1")
    Queue QueueIn1;
    
    @Resource(lookup = "novi2")
    Queue QueueOut1;
    
    @Resource(lookup = "novii3")
    Queue QueueIn2;
    
    @Resource(lookup = "novii4")
    Queue QueueOut2;
    
    @Resource(lookup = "novi5")
    Queue QueueIn3;
    
    @Resource(lookup = "novi6")
    Queue QueueOut3;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Serializable sendAndWait(int tip, String podaci) {
        try (JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(odaberOut(tip))) {
            
            //System.out.println("SALJEM PORUKU tip=" + tip);
            JMSProducer producer = context.createProducer();
            TextMessage msg = context.createTextMessage(String.valueOf(tip));
            if (podaci != null) msg.setStringProperty("podaci", podaci);
            producer.send(odaberIn(tip), msg);
            //System.out.println("PORUKA POSLATA, cekam odgovor...");
            Message m = consumer.receive(20000);
            //System.out.println("ODGOVOR STIGAO");
            return (Serializable) ((ObjectMessage) m).getObject();
        } catch (JMSException ex) {
            Logger.getLogger(JmsService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Queue odaberIn(int tip) {
        switch (tip) {
            case 1: case 2: case 3: case 4: case 5:
            case 15: case 16: case 29: case 30: case 31:
                return QueueIn1;
            case 6: case 7: case 8: case 9: case 10:
            case 11: case 12: case 13: case 17: case 18:
            case 19: case 20: case 24: case 25: case 28:
                return QueueIn2;
            case 14: case 21: case 22: case 23: case 26: case 27:
                return QueueIn3;
            default:
                return null;
        }
    }
    
    private Queue odaberOut(int tip) {
        switch (tip) {
            case 1: case 2: case 3: case 4: case 5:
            case 15: case 16: case 29: case 30: case 31:
                return QueueOut1;
            case 6: case 7: case 8: case 9: case 10:
            case 11: case 12: case 13: case 17: case 18:
            case 19: case 20: case 24: case 25: case 28:
                return QueueOut2;
            case 14: case 21: case 22: case 23: case 26: case 27:
                return QueueOut3;
            default:
                return null;
        }
    }
}