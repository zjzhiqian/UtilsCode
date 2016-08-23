package com.hzq.nio.encodeDecode.msgPack;

import com.hzq.entity.User;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzq on 16/7/21.
 */
public class EchoServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        List users = (List) msg;
        users.forEach(System.out::println);


        List<User> users1 = new ArrayList<>();
        for (int i = 100; i < 1000; i++) {
            users1.add(new User(i));
            users1.add(new User(i+1));
        }
        ctx.write(users1);
        ctx.flush();



    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
