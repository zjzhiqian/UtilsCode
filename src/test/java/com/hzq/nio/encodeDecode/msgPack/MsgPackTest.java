package com.hzq.nio.encodeDecode.msgPack;

import org.junit.Test;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 第七章 msgPack编解码
 * Created by hzq on 16/7/21.
 */
public class MsgPackTest {

    @Test
    /**
     * msgPack编解码
     */
    public void test01() throws IOException {

        List<String> src = new ArrayList<>();
        src.add("1");
        src.add("two");
        src.add("three");
        MessagePack msgPack = new MessagePack();
        byte[] write = msgPack.write(src);
        List<String> data = msgPack.read(write, Templates.tList(Templates.TString));
        data.forEach(System.out::println);


    }

}
