package com.netty;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

@ChannelPipelineCoverage("all")
public class MessageServerHandler extends SimpleChannelUpstreamHandler
{

	private static final Logger logger = Logger.getLogger(MessageServerHandler.class.getName());

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
	{

		/*if (!(e.getMessage() instanceof String))
		{
			return;//(1) 
		}
		String msg = (String) e.getMessage();
		e.getChannel().write(msg);//(2) 
		*/
		System.out.println("server 收到的消息：" + e.getMessage());
		e.getChannel().write("hellwo Client 收到你的时间为" + e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	{

		logger.log(Level.WARNING, "Unexpected exception from downstream.", e.getCause());
		e.getChannel().close();
	}
}