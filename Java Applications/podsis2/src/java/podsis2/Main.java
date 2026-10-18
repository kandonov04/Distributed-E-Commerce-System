package podsis2;

import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class Main {

    
    public static void main(String[] args) throws Exception {
        
        
        InitialContext ic = new InitialContext();

        ConnectionFactory myConnFactory = (ConnectionFactory) ic.lookup("jms/__defaultConnectionFactory");

        Queue queueIn = (Queue) ic.lookup("novii3");

        Queue queueOut = (Queue) ic.lookup("novii4");
        
        JMSContext context = myConnFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queueIn);
        
        //System.out.println("PS2 pokrenut...");
        
        consumer.setMessageListener((Message msg) -> {
            try {
                //System.out.println("STIGLA PORUKA!");
                TextMessage txtMsg = (TextMessage) msg;
                int operacija = Integer.parseInt(txtMsg.getText());
                String podaci = txtMsg.getStringProperty("podaci");
                Serializable rez = Handler2.handle(operacija, podaci);
                ObjectMessage odgovor = context.createObjectMessage(rez);
                producer.send(queueOut, odgovor);
                //ystem.out.println("ODGOVOR POSLAT!");
            } catch (Exception ex) {
                System.out.println("GRESKA: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        //System.out.println("Listener registrovan, cekam poruke...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
