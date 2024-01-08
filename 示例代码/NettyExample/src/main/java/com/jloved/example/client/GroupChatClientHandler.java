package com.jloved.example.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * @author crush
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    
    //当前Channel 已从对方读取消息时调用。
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
