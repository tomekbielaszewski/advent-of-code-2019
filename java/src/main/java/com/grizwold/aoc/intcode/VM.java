package com.grizwold.aoc.intcode;

import com.grizwold.aoc.ProgramTerminated;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VM {
    int[] memory;
    int instructionPointer = 0;
    boolean isRunning = true;

    final InputStream in;
    final OutputStream out;
    final PrintStream printOut;

    private Set<Opcode> opcodes = new HashSet<>();

    public VM(String input) {
        this(new ByteArrayInputStream(input.getBytes()),
                new ByteArrayOutputStream());
    }

    public VM(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        this.printOut = new PrintStream(out);
        opcodes.add(new Opcode_01());
        opcodes.add(new Opcode_02());
        opcodes.add(new Opcode_99());
        opcodes.add(new Opcode_03());
        opcodes.add(new Opcode_04());
        opcodes.add(new Opcode_05());
        opcodes.add(new Opcode_06());
        opcodes.add(new Opcode_07());
        opcodes.add(new Opcode_08());
    }

    public int[] execute(String program) {
        loadProgram(program);
        try {
            while (isRunning) {
                opcodes.stream()
                        .filter(op -> op.matching(memory[instructionPointer]))
                        .findFirst()
                        .orElseThrow(() -> new ProgramTerminated("Illegal instruction code: " + memory[instructionPointer]))
                        .execute(this);
            }
        } catch (RuntimeException e) {
            System.out.println("Program errored! " + e.toString());
        }
        return memory;
    }

    public String getOutputString() {
        if (this.out instanceof ByteArrayOutputStream) {
            return new String(((ByteArrayOutputStream) out).toByteArray());
        }
        return "Unsupported output stream. Use VM::getOutput";
    }

    public OutputStream getOutput() {
        return out;
    }

    private void loadProgram(String str) {
        List<Integer> ints = Arrays.stream(str.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        memory = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            int i1 = ints.get(i);
            memory[i] = i1;
        }
    }
}
