package org.example;
import java.util.HashMap;
import java.util.Map;

interface ICpu {
    void execute(Command command);
}

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

class Command {
    private String operation;
    private String[] args;

    public Command(String command) {
        String[] parts = command.split(" ");
        this.operation = parts[0];
        this.args = new String[parts.length - 1];
        System.arraycopy(parts, 1, this.args, 0, parts.length - 1);
    }

    public String getOperation() {
        return operation;
    }

    public String[] getArgs() {
        return args;
    }
}

class Executer {
    private ICpu cpu;

    public Executer(ICpu cpu) {
        this.cpu = cpu;
    }

    public void run(Command[] commands) {
        for (Command command : commands) {
            cpu.execute(command);
        }
    }
}

class BCpu {
    public static ICpu build() {
        return new CPU();
    }
}

public class App {
    public static void main(String[] args) {
        Command[] prog = {
                new Command("init 10 10"),
                new Command("init 11 15"),
                new Command("init 12 5"),
                new Command("ld a 10"),
                new Command("ld b 11"),
                new Command("ld c 12"),
                new Command("add"),
                new Command("print"),
                new Command("mv a d"),
                new Command("mv b c"),
                new Command("print"),
                new Command("div"),
                new Command("print")
        };

        ICpu cpu = BCpu.build();
        Executer exec = new Executer(cpu);
        exec.run(prog);
    }
}


