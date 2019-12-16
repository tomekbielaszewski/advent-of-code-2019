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
    long[] memory;
    int instructionPointer = 0;
    int relativeBase = 0;
    boolean isRunning = true;

    final BlockingQueue<Long> in;
    final BlockingQueue<Long> out;

    private Set<Opcode> opcodes = new HashSet<>();

    public VM(Long... inputs) {
        this(new ArrayBlockingQueue<Long>(inputs.length) {{
                 this.addAll(Arrays.asList(inputs));
             }},
                new ArrayBlockingQueue<>(100));
    }

    public VM(BlockingQueue<Long> in, BlockingQueue<Long> out) {
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
        opcodes.add(new Opcode_09());
    }

    public long[] execute(String program) {
        return this.execute(program, 0);
    }

    public long[] execute(String program, int additionalMemory) {
        loadProgram(program, additionalMemory);
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

    public void write(Long... ints) {
        for (Long anInt : ints) {
            try {
                in.put(anInt);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public Long read() {
        try {
            return out.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public BlockingQueue<Long> getOutput() {
        return out;
    }

    public Long[] getOutputs() {
        return out.toArray(new Long[0]);
    }

    private void loadProgram(String str, int additionalMemory) {
        List<Long> ints = Arrays.stream(str.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        memory = new long[ints.size() + additionalMemory];
        for (int i = 0; i < ints.size(); i++) {
            long i1 = ints.get(i);
            memory[i] = i1;
        }
    }
}
