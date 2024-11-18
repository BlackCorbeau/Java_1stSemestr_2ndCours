package org.example;

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

    @Override
    public String toString() {
        return operation + " " + String.join(" ", args);
    }
}