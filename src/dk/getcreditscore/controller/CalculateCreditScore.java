/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getcreditscore.controller;

import dk.getcreditscore.messaging.Receive;
import java.io.IOException;

/**
 *
 * @author marekrigan
 */
public class CalculateCreditScore 
{
    public static void receiveMessages() throws IOException,InterruptedException
    {
        Receive.receiveRequests();
        
        
    }
}
