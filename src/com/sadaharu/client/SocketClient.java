package com.sadaharu.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient
{
	public static void main(String[] args)
	{
		SocketClient client = new SocketClient();
		client.start();
	}

	public void start()
	{
		BufferedReader inputReader = null;
		BufferedWriter writer = null;
		BufferedReader reader = null;
		Socket socket = null;
		try
		{
			socket = new Socket("127.0.0.1", 9898); // 地址(本机) ，端口(和服务端一致)
			writer = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			inputReader = new BufferedReader(new InputStreamReader(System.in));
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			startServerReplyListener(reader);

			String inputContent;
			while (!(inputContent = inputReader.readLine()).equals("bye"))
			{
				writer.write(inputContent + "\n");
				writer.flush();
				// String response = reader.readLine();
				// System.out.println(response);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{

			try
			{
				reader.close();
				socket.close();
				writer.close();
				inputReader.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void startServerReplyListener(final BufferedReader reader)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				String response;
				try
				{
					while ((response = reader.readLine()) != null)
					{
						System.out.println(response);
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}
