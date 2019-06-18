package com.sima.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by qisima on 6/18/2019 8:32 PM
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (byteBuf.readableBytes() >= 4){
            list.add(Math.abs(byteBuf.readInt()));
        }
    }
}
