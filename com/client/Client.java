package com.client;

import java.io.IOException;
import java.net.Socket;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * receive msg from cmd and send it to server
 */
class ClientThreedWriter implements Runnable {

	private Socket socket;
	
	public ClientThreedWriter(Socket s)
	{
		socket = s;
	}
	
	@Override
	public void run()
	{
		try
		{
			Scanner chat = new Scanner(System.in);	
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			
			while (true)
			{						
                String input = chat.nextLine();
                System.out.println("send:"+input);
				out.println(input);	
				out.flush();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
    }
}

/**
 * receive msg from server and print it 
 */
class ClientThreedReader implements Runnable {

	private Socket socket;
	
	public ClientThreedReader(Socket s)
	{
		socket = s;
	}
	
	@Override
	public void run()
	{
		try
		{
			Scanner in = new Scanner(socket.getInputStream());	
			while (true)
			{
				if(in.hasNext()){
                    System.out.println("receive:"+in.nextLine());
                }
                // whatever,it sleep 1 second first
                Thread.sleep(1000);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
    }
}


/**
 * the chat client demo
 */
public class Client {

	private final static int PORT = 23333;
	private final static String HOST = "localhost";
	
	public static void main(String[] args) throws IOException
	{
		try 
		{
			Socket s = new Socket(HOST, PORT);	
			System.out.println("connected to " + s.toString());

            ClientThreedReader reader = new ClientThreedReader(s);
            new Thread(reader).start();

			ClientThreedWriter writer = new ClientThreedWriter(s);	
			new Thread(writer).start();
			
		} 
		catch (Exception noServer)
		{
			System.out.println("The server might not be up at this time.");
			System.out.println("Please try again later.");
		}
	}
}