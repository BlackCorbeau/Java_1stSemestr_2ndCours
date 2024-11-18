package org.example;


public class App
{
    public static void main(String[] args) {
        Program prog = new Program();
        prog.add(new Command("init 10 20"));
        prog.add(new Command("init 11 25"));
        prog.add(new Command("ld a 10"));
        prog.add(new Command("ld b 11"));
        prog.add(new Command("ld c 11"));
        prog.add(new Command("add"));
        prog.add(new Command("mv a d"));
        prog.add(new Command("add"));
        prog.add(new Command("print"));

        for (Command c : prog) {
            System.out.println(c);
        }

        System.out.println("Most popular instruction: " + prog.mostPopularInstruction());
        System.out.println("Used memory addresses: " + prog.usedMemoryAddresses());
        System.out.println("Instructions sorted by frequency: " + prog.instructionsSortedByFrequency());

        ICpu cpu = BCpu.build();
        Executer exec = new Executer(cpu);
        exec.run(prog);
    }
}
