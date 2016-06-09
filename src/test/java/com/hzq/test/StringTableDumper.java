package com.hzq.test;

import sun.jvm.hotspot.memory.StringTable;
import sun.jvm.hotspot.oops.Oop;
import sun.jvm.hotspot.tools.Tool;

/**
 * StringTableDumper
 * Created by hzq on 16/5/31.
 */

public class StringTableDumper extends Tool {

    public static void main(String[] args) {
        StringTableDumper printer = new StringTableDumper();
        printer.start();
        printer.stop();
    }

    @Override
    public void run() {
        StringTable stringTable = StringTable.getTheTable();
        stringTable.stringsDo(Oop::print);
    }

}