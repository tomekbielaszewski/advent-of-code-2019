package com.grizwold.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    public int[] toArray(String str) {
        List<Integer> ints = Arrays.stream(str.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int[] arr = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            int i1 = ints.get(i);
            arr[i] = i1;
        }
        return arr;
    }

    public void solution1(int[] memory) {
        for (int i = 0; i < memory.length; i += 4) {
            executeOpcodeAt(i, memory);
        }
    }

    private void executeOpcodeAt(int i, int[] memory) {
        int opcode = memory[i];
        switch (opcode) {
            case 1:
                add(i, memory);
                break;
            case 2:
                multiply(i, memory);
                break;
            case 99:
                terminate();
                break;
            default:
                throw new IllegalArgumentException("illegal opcode: " + opcode);
        }
    }

    private void terminate() {
        throw new ProgramTerminated("stop");
    }

    private void add(int i, int[] memory) {
        int arg1 = memory[i + 1];
        int arg2 = memory[i + 2];
        int result = memory[i + 3];

        memory[result] = memory[arg1] + memory[arg2];
    }

    private void multiply(int i, int[] memory) {
        int arg1 = memory[i + 1];
        int arg2 = memory[i + 2];
        int result = memory[i + 3];

        memory[result] = memory[arg1] * memory[arg2];
    }
}
