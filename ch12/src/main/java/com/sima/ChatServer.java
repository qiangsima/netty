package com.sima;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * Created by qisima on 2/28/2019 5:45 PM
 */
public class ChatServer {
    private final ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup loopGroup = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address){
        ServerBootstrap bootStrap = new ServerBootstrap();
        bootStrap.group(loopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(group));
        ChannelFuture future = bootStrap.bind(address);
        channel = future.channel();
        return future;
    }

    private ChannelHandler createInitializer(ChannelGroup group) {
        return new ChatServerInitializer(group);
    }

    public void destroy(){
        if (channel != null){
            channel.close();
        }
        group.close();
        loopGroup.shutdownGracefully();
    }

    public static void main(String[] args){
        int port = Integer.parseInt(args[0]);
        final ChatServer server = new ChatServer();
        ChannelFuture future = server.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
