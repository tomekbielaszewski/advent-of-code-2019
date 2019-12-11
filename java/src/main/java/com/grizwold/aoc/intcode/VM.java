package com.grizwold.aoc.intcode;

import com.grizwold.aoc.ProgramTerminated;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class VM {
    int[] memory;
    int instructionPointer = 0;
    boolean isRunning = true;

    final BlockingQueue<Integer> in;
    final BlockingQueue<Integer> out;

    private Set<Opcode> opcodes = new HashSet<>();

    public VM(Integer... inputs) {
        this(new ArrayBlockingQueue<Integer>(inputs.length) {{
                 this.addAll(Arrays.asList(inputs));
             }},
                new ArrayBlockingQueue<Integer>(1));
    }

    public VM(BlockingQueue<Integer> in, BlockingQueue<Integer> out) {
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
                        .orElseThrow(() -> new ProgramTerminated("Illegal instruction code: " + memory[instructionPointer]))
                        .execute(this);
            }
        } catch (RuntimeException e) {
            System.out.println("Program errored! " + e.toString());
        }
        return memory;
    }

    public void write(Integer... ints) {
        for (Integer anInt : ints) {
            try {
                in.put(anInt);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public Integer read() {
        try {
            return out.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public BlockingQueue<Integer> getOutput() {
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
