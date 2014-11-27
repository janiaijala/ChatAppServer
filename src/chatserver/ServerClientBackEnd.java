/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ServerClientBackEnd implements Runnable{

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    public ServerClientBackEnd(Socket sock){
        socket = sock;
    }
    
    @Override
    public void run() {
        try {
            output= new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        
            //Start to wait data
            while(true){
                
                Object o = input.readObject();
                if(o instanceof ChatMessage){
                    ChatMessage cm = (ChatMessage)input.readObject();
                    ChatServer.broadcastMessage(cm);
                }
         /*       if(o instanceof Participants){
                    Participants p = (Participants)input.readObject();
                    
                }
         */       

            }
        } catch (IOException | ClassNotFoundException ex) {
            
            ChatServer.clients.remove(this);
        }
    }
    
    public void sendMessage(ChatMessage data){
        try {
            output.writeObject(data);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
