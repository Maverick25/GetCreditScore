/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getcreditscore.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import dk.getcreditscore.dto.LoanRequestDTO;
import dk.getcreditscore.messaging.Receive;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author marekrigan
 */
public class CalculateCreditScore 
{
    public static void receiveMessages() throws IOException,InterruptedException
    {
        HashMap<String,Object> objects = Receive.setUpReceiver();
        
        QueueingConsumer consumer = (QueueingConsumer) objects.get("consumer");
        Channel channel = (Channel) objects.get("channel");
        
        Gson gson = new Gson();
        LoanRequestDTO loanRequestDTO;
        
        while (true) 
        {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          
          loanRequestDTO = gson.fromJson(message, LoanRequestDTO.class);
          System.out.println(loanRequestDTO.toString());

          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        
    }
}
