/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import static chatserver.ChatServer.clientList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ChatMessage;
import message.ClientList;
import message.Participants;

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
                    ChatMessage cm = (ChatMessage)o;
                    ChatServer.broadcastMessage(cm);
                }else if(o instanceof Participants){
                    Participants p = (Participants)o;
                    ChatServer.addParticipant(p);
                    
                }

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
    
    public void send(ClientList list){
        try {
            output.writeObject(list);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerClientBackEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}    
 
