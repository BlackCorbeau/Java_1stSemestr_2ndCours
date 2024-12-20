package org.example;

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


