package com.hzq.nio.encodeDecode.msgPack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * EchoServer
 * Created by hzq on 16/7/21.
 */
public class EchoServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("frame decoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
//                            ch.pipeline().addLast("frame encoder",new LengthFieldPrepender(2));
//
//
//                            ch.pipeline().addLast("msgPack encoder", new MsgPackEncoder());
//                            ch.pipeline().addLast("msgPack decoder", new MsgPackDecoder());

                            ch.pipeline().addLast("frame decoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            ch.pipeline().addLast("msgPack decoder", new MsgPackDecoder());
                            ch.pipeline().addLast("frame encoder", new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgPack encoder", new MsgPackEncoder());


                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //绑定端口,同步等待成功
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            //进行链路阻塞
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
