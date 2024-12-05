package org.example.work4;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CPUAppController {

    @FXML
    private TextArea commandArea;

    @FXML
    private TextArea registerArea;

    @FXML
    private TableView<MemoryEntry> memoryTable;

    @FXML
    private TableColumn<MemoryEntry, Integer> indexColumn;

    @FXML
    private TableColumn<MemoryEntry, Integer> valueColumn;

    @FXML
    private ListView<String> instructionList;

    private ICpu cpu;
    private Program program;
    private ObservableList<String> instructions;
    private int currentInstructionIndex = -1;

    @FXML
    public void initialize() {
        cpu = BCpu.build();
        program = new Program();
        instructions = FXCollections.observableArrayList();
        instructionList.setItems(instructions);

        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        updateMemoryArea();
    }

    @FXML
    private void handleInitButton() {
        showCommandDialog("init", false, false, true);
    }

    @FXML
    private void handleLdButton() {
        showCommandDialog("ld", true, false, true);
    }

    @FXML
    private void handleStButton() {
        showCommandDialog("st", true, false, true);
    }

    @FXML
    private void handleMvButton() {
        showCommandDialog("mv", true, true, false);
    }

    @FXML
    private void handleAddButton() {
        executeCommand("add");
    }

    @FXML
    private void handleSubButton() {
        executeCommand("sub");
    }

    @FXML
    private void handleMultButton() {
        executeCommand("mult");
    }

    @FXML
    private void handleDivButton() {
        executeCommand("div");
    }

    @FXML
    private void handleAddInstructionButton() {
        showCommandDialog("add", false, false, false);
    }

    @FXML
    private void handleRemoveInstructionButton() {
        int selectedIndex = instructionList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            instructions.remove(selectedIndex);
            program.remove(selectedIndex);
        }
    }

    @FXML
    private void handleMoveUpButton() {
        int selectedIndex = instructionList.getSelectionModel().getSelectedIndex();
        if (selectedIndex > 0) {
            String instruction = instructions.get(selectedIndex);
            instructions.set(selectedIndex, instructions.get(selectedIndex - 1));
            instructions.set(selectedIndex - 1, instruction);
            program.swap(selectedIndex, selectedIndex - 1);
            instructionList.getSelectionModel().select(selectedIndex - 1);
        }
    }

    @FXML
    private void handleMoveDownButton() {
        int selectedIndex = instructionList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < instructions.size() - 1) {
            String instruction = instructions.get(selectedIndex);
            instructions.set(selectedIndex, instructions.get(selectedIndex + 1));
            instructions.set(selectedIndex + 1, instruction);
            program.swap(selectedIndex, selectedIndex + 1);
            instructionList.getSelectionModel().select(selectedIndex + 1);
        }
    }

    @FXML
    private void handleExecuteStepButton() {
        if (currentInstructionIndex < instructions.size() - 1) {
            currentInstructionIndex++;
            String command = instructions.get(currentInstructionIndex);
            executeCommand(command);
            highlightCurrentInstruction();
        }
    }

    @FXML
    private void handleResetButton() {
        currentInstructionIndex = -1;
        cpu = BCpu.build();
        updateRegisterArea();
        updateMemoryArea();
        highlightCurrentInstruction();
    }

    private void showCommandDialog(String command, boolean showRegister1, boolean showRegister2, boolean showMemoryAddress) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Enter Command Arguments");
        dialog.setHeaderText("Enter arguments for command: " + command);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        HBox hbox = new HBox();
        hbox.setSpacing(10);

        if (showRegister1) {
            ChoiceBox<String> arg1 = new ChoiceBox<>(FXCollections.observableArrayList("a", "b", "c", "d"));
            hbox.getChildren().add(arg1);
        } else {
            TextField arg1 = new TextField();
            arg1.setPromptText("Memory Address");
            hbox.getChildren().add(arg1);
        }

        if (showRegister2) {
            ChoiceBox<String> arg2 = new ChoiceBox<>(FXCollections.observableArrayList("a", "b", "c", "d"));
            hbox.getChildren().add(arg2);
        } else if (showMemoryAddress) {
            TextField arg2 = new TextField();
            arg2.setPromptText("Memory Address");
            hbox.getChildren().add(arg2);
        } else {
            TextField arg2 = new TextField();
            arg2.setPromptText("Argument 2");
            hbox.getChildren().add(arg2);
        }

        dialog.getDialogPane().setContent(hbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                String args = hbox.getChildren().stream()
                        .map(node -> node instanceof ChoiceBox ? ((ChoiceBox<String>) node).getValue() : ((TextField) node).getText())
                        .filter(arg -> arg != null && !arg.isEmpty())
                        .collect(Collectors.joining(" "));
                return command + " " + args.trim();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (result != null) {
                instructions.add(result);
                program.add(new Command(result));
            }
        });
    }

    private void executeCommand(String command) {
        Command cmd = new Command(command);
        cpu.execute(cmd);
        updateRegisterArea();
        updateMemoryArea();
    }

    private void updateRegisterArea() {
        registerArea.clear();
        registerArea.appendText("Registers: " + ((CPU) cpu).getRegisters().toString() + "\n");
    }

    private void updateMemoryArea() {
        ObservableList<MemoryEntry> memoryEntries = FXCollections.observableArrayList();
        int[] memory = ((CPU) cpu).getMemory();
        for (int i = 0; i < memory.length; i++) {
            memoryEntries.add(new MemoryEntry(i, memory[i]));
        }
        memoryTable.setItems(memoryEntries);
    }

    private void highlightCurrentInstruction() {
        instructionList.getSelectionModel().clearSelection();
        if (currentInstructionIndex >= 0) {
            instructionList.getSelectionModel().select(currentInstructionIndex);
        }
    }

    public static class MemoryEntry {
        private final IntegerProperty index;
        private final IntegerProperty value;

        public MemoryEntry(int index, int value) {
            this.index = new SimpleIntegerProperty(index);
            this.value = new SimpleIntegerProperty(value);
        }

        public int getIndex() {
            return index.get();
        }

        public IntegerProperty indexProperty() {
            return index;
        }

        public int getValue() {
            return value.get();
        }

        public IntegerProperty valueProperty() {
            return value;
        }
    }
}
