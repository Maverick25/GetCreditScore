/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getcreditscore.controller;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import dk.getcreditscore.client.CreditScore;
import dk.getcreditscore.client.CreditScoreResponse;
import dk.getcreditscore.client.CreditScoreService_Service;
import dk.getcreditscore.dto.LoanRequestDTO;
import dk.getcreditscore.messaging.Receive;
import dk.getcreditscore.messaging.Send;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author marekrigan
 */
public class CalculateCreditScore 
{
    private static Gson gson;
    
    public static void receiveMessages() throws IOException,InterruptedException
    {
        gson = new Gson();
        
        HashMap<String,Object> objects = Receive.setUpReceiver();
        
        QueueingConsumer consumer = (QueueingConsumer) objects.get("consumer");
        Channel channel = (Channel) objects.get("channel");
        
        LoanRequestDTO loanRequestDTO;
        
        while (true) 
        {
          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
          String message = new String(delivery.getBody());
          
          loanRequestDTO = gson.fromJson(message, LoanRequestDTO.class);
          
          CreditScoreService_Service service = new CreditScoreService_Service();
          int creditScore = service.getCreditScoreServicePort().creditScore(loanRequestDTO.getSsn());
          
          loanRequestDTO.setCreditScore(creditScore);
          
          System.out.println(loanRequestDTO.toString());
          
          sendMessage(loanRequestDTO);

          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        
    }
    
    public static void sendMessage(LoanRequestDTO dto) throws IOException
    {
        String message = gson.toJson(dto);
        
        Send.sendMessage(message);
    }
}
