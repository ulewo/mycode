package com.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class MessageServerPipelineFactory implements ChannelPipelineFactory
{

	public ChannelPipeline getPipeline() throws Exception
	{

		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("decoder", new MessageDecoder());
		pipeline.addLast("encoder", new MessageEncoder());
		pipeline.addLast("handler", new MessageServerHandler());

		return pipeline;
	}
}