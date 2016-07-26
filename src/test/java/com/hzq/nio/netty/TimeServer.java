package com.hzq.nio.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;


/**
 * TimeServer
 * Created by hzq on 16/7/19.
 */
public class TimeServer {
    public void bind(Integer port) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();  //EventLoop的职责是处理所有注册到本线程多路由复用器Selector上的Channel,Selector的轮训操作是由绑定的EventLoop线程的run方法驱动,用户定义的Task和定时任务Task也同一由EventLoop负责处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    .bind(port)
                    .sync()  //绑定端口,同步等待成功
                    .channel()
                    .closeFuture()
                    .sync(); //进行链路阻塞

//            //绑定端口,同步等待成功
//            ChannelFuture future = serverBootstrap.bind(port).sync();
//            //进行链路阻塞
//            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TimeServer().bind(8080);
    }


}


