package com.sima.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by qisima on 6/18/2019 8:36 PM
 */
public class AbsIntegerEncoderTest {
    @Test
    public void testAbsIntegerEncoder(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());

        assertTrue(channel.writeOutbound(input.retain()));
        assertTrue(channel.finish());

        for (int i = 0; i < 10; i++) {
            assertEquals(new Integer(i), channel.readOutbound());
        }
        assertNull(channel.readOutbound());
    }
}
