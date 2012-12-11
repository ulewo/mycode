package com.netty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

@ChannelPipelineCoverage("all")
public class MessageClientHandler extends SimpleChannelUpstreamHandler
{

	private static final Logger logger = Logger.getLogger(MessageClientHandler.class.getName());

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
	{

		String message = "hello kafka0102";
		e.getChannel().write(message);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
	{

		// Send back the received message to the remote peer. 
		try
		{
			Thread.sleep(1000 * 3);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		System.out.print("Clent 收到消息" + e.getMessage());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		e.getChannel().write("server 现在是" + format.format(new Date()));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	{

		// Close the connection when an exception is raised. 
		logger.log(Level.WARNING, "Unexpected exception from downstream.", e.getCause());
		e.getChannel().close();
	}
}