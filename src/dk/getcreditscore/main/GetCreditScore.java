/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.getcreditscore.main;

import java.io.IOException;
import dk.getcreditscore.controller.CalculateCreditScore;
/**
 *
 * @author marekrigan
 */
public class GetCreditScore 
{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException
    {
        CalculateCreditScore.receiveMessages();
    }
    
}
