package org.example;
import java.util.HashMap;
import java.util.Map;

class CPU implements ICpu {
    private int[] memory;
    private Map<String, Integer> registers;

    public CPU() {
        memory = new int[1024];
        registers = new HashMap<>();
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", 0);
        registers.put("d", 0);
    }

    @Override
    public void execute(Command command) {
        switch (command.getOperation()) {
            case "init":
                init(command);
                break;
            case "ld":
                ld(command);
                break;
            case "st":
                st(command);
                break;
            case "mv":
                mv(command);
                break;
            case "print":
                print();
                break;
            case "add":
                add();
                break;
            case "sub":
                sub();
                break;
            case "mult":
                mult();
                break;
            case "div":
                div();
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + command.getOperation());
        }
    }

    private void init(Command command) {
        int address = Integer.parseInt(command.getArgs()[0]);
        int value = Integer.parseInt(command.getArgs()[1]);
        memory[address] = value;
    }

    private void ld(Command command) {
        String register = command.getArgs()[0];
        int address = Integer.parseInt(command.getArgs()[1]);
        registers.put(register, memory[address]);
    }

    private void st(Command command) {
        String register = command.getArgs()[0];
        int address = Integer.parseInt(command.getArgs()[1]);
        memory[address] = registers.get(register);
    }

    private void mv(Command command) {
        String r1 = command.getArgs()[0];
        String r2 = command.getArgs()[1];
        registers.put(r1, registers.get(r2));
    }

    private void print() {
        System.out.println("Registers: " + registers);
    }

    private void add() {
        int a = registers.get("a");
        int b = registers.get("b");
        registers.put("d", a + b);
    }

    private void sub() {
        int a = registers.get("a");
        int b = registers.get("b");
        registers.put("d", a - b);
    }

    private void mult() {
        int a = registers.get("a");
        int b = registers.get("b");
        registers.put("d", a * b);
    }

    private void div() {
        int a = registers.get("a");
        int b = registers.get("b");
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        registers.put("d", a / b);
    }
}
