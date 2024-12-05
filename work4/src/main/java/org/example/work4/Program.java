package org.example.work4;

import java.util.*;
import java.util.stream.Collectors;

public class Program implements Iterable<Command> {
    private List<Command> commands;

    public Program() {
        commands = new ArrayList<>();
    }

    public void add(Command command) {
        commands.add(command);
    }

    public void remove(int index) {
        commands.remove(index);
    }

    public void swap(int index1, int index2) {
        Collections.swap(commands, index1, index2);
    }

    @Override
    public Iterator<Command> iterator() {
        return commands.iterator();
    }

    public Command mostPopularInstruction() {
        return commands.stream()
                .collect(Collectors.groupingBy(Command::getOperation, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> new Command(entry.getKey()))
                .orElse(null);
    }

    public List<Integer> usedMemoryAddresses() {
        return commands.stream()
                .filter(command -> command.getOperation().equals("init") || command.getOperation().equals("ld") || command.getOperation().equals("st"))
                .flatMap(command -> Arrays.stream(command.getArgs()))
                .filter(arg -> arg.matches("\\d+"))
                .map(Integer::parseInt)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Command> instructionsSortedByFrequency() {
        return commands.stream()
                .collect(Collectors.groupingBy(Command::getOperation, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> new Command(entry.getKey()))
                .collect(Collectors.toList());
    }
}
