package com.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
/**
 * It's a simple tcp chat server demo
 */

class ServerThread implements Runnable {
    private Socket socket;
    private Server server;
    private PrintWriter pw;
    public ServerThread(Socket s, Server mainServer){
        socket = s;
        server = mainServer;
        try {
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send msg to client
     * @param msg
     * @return
     */
    public boolean sendToClient(String msg){
        if(socket.isClosed()){
            return false;
        }
        try{
            pw.println(msg);
            pw.flush();
            return true;
        }catch(Exception ex){
            System.out.println("send to client "+socket.toString() +" err:" + ex.getMessage());
            try {
                pw.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;           
        }
    }

    public void closeClient(){
        try {
            pw.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try
		{
			
			Scanner in = new Scanner(socket.getInputStream());	
			while (true)
			{						
				if(in.hasNext()){
                    String msg = in.nextLine();
                    System.out.println("receive:"+msg);
                    server.sendToAll(msg);
                }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
    }

}

class Server {
    private final static int PORT = 23333;
    HashSet<ServerThread> clients = new HashSet<ServerThread>();
    public void run(){
        try 
		{
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("server start and listen the port " + PORT);
			System.out.println("Waiting for clients...");
		
			while (true)
			{												
				Socket s = server.accept();				
				System.out.println("Client connected from " + s.toString());		
                ServerThread client = new ServerThread(s,this);
                clients.add(client);
                // one client one thread 
				Thread t = new Thread(client);
				t.start();
			}
		} 
		catch (Exception e) 
		{
			System.out.println("An error occured.");
			e.printStackTrace();
		}
    }

    /**
     * broadcast to all client
     * @param msg
     */
    public void sendToAll(String msg){
        Iterator<ServerThread> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ServerThread client = iterator.next();
            if(!client.sendToClient(msg)){ // if send faild, remove it 
                iterator.remove();
            }
        }
    }

    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }
}