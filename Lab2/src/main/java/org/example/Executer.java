package org.example;

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
