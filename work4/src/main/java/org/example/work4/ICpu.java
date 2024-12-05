package org.example.work4;

import java.util.Map;

interface ICpu {
    void execute(Command command);
    Map<String, Integer> getRegisters();
    int[] getMemory();
}
