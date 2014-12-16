/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getcreditscore.main;

import java.io.IOException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import dk.getcreditscore.controller.CalculateCreditScore;
/**
 *
 * @author marekrigan
 */
public class GetCreditScore 
{

    

    public static void main(String[] args) throws IOException, InterruptedException
    {
        CalculateCreditScore.receiveMessages();
    }
    
}
