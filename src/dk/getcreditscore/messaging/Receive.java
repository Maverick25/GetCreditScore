/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getcreditscore.messaging;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import dk.getcreditscore.dto.LoanRequestDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author marekrigan
 */
public class Receive 
{
    private static final String TASK_QUEUE_NAME = "task_queue";
    
    
    private static List<LoanRequestDTO> requests;
    
    public static void receiveRequests() throws java.io.IOException,
                                               java.lang.InterruptedException 
    {
        requests = new ArrayList<LoanRequestDTO>();
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        factory.setUsername("student");
        factory.setPassword("cph");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

        Gson gson = new Gson();
        LoanRequestDTO loanRequestDTO;
        
        while (true) 
        {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          
          loanRequestDTO = gson.fromJson(message, LoanRequestDTO.class);
        
          requests.add(loanRequestDTO);

          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        
    }
    
    public static String getNextRequest() throws IndexOutOfBoundsException
    {
        LoanRequestDTO dto = requests.get(0);
        
        requests.remove(dto);
        
        return dto.toString();
    }
}
