/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import message.ChatMessage;
import message.Participants;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ChatServer {

    /**
     * @param args the command line arguments
     */
    static ArrayList<ServerClientBackEnd> clients = new ArrayList();
    static ArrayList<String> clientList = new ArrayList();
    //static ArrayList<clientinnimet>
    
    public static void main(String[] args) {
        try {
            //Start the server to listen port 3010
            ServerSocket server = new ServerSocket(3010);
            
            //Start to listen and wait the connection
            while(true){
                //Wait here the client
                Socket temp = server.accept();
                ServerClientBackEnd backEnd = new ServerClientBackEnd(temp);
                clients.add(backEnd);
                Thread t = new Thread(backEnd);
                t.setDaemon(true);
                t.start();
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void broadcastMessage(ChatMessage cm){
        for(ServerClientBackEnd temp: clients){
            temp.sendMessage(cm);
            
        }
    }
    
    public static void addParticipant(Participants p){
        clientList.add(p.getParticipants());
    }
    
    public static void send(){
        for(String temp2:clientList){
       
        }
    }
   /* public static void sendClientList(){
        for(String temp: clientList){
            temp.sendClientList(clientList);
        }
    }*/
}
