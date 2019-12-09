package com.grizwold.aoc.intcode;

import com.grizwold.aoc.ProgramTerminated;

import java.io.InputStream;
import java.io.PrintStream;
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
    final PrintStream out;

    private Set<Opcode> opcodes = new HashSet<>();

    public VM(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
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
                        .orElseThrow(ProgramTerminated::new)
                        .execute(this);
            }
        } catch (RuntimeException e) {
            System.out.println("Program errored! " + e.toString());
        }
        return memory;
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
