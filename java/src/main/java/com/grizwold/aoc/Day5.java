package com.grizwold.aoc;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.grizwold.aoc.ParameterMode.IMMEDIATE;
import static com.grizwold.aoc.ParameterMode.POSITION;

public class Day5 {
    public static void main(String[] args) {
        new Day5().run("1,9,10,3,2,3,11,0,99,30,40,50");
    }

    public int[] run(String input) {
        VM vm = new VM();
        vm.memory = toArray(input);
        vm.execute();
        return vm.memory;
    }

    private int[] toArray(String str) {
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
}

class VM {
    int[] memory;
    int instructionPointer = 0;
    boolean isRunning = true;

    private Set<Opcode> opcodes = new HashSet<>();

    VM() {
        opcodes.add(new Opcode_01());
        opcodes.add(new Opcode_02());
        opcodes.add(new Opcode_99());
        opcodes.add(new Opcode_03());
        opcodes.add(new Opcode_04());
    }

    void execute() {
        while (isRunning) {
            opcodes.stream()
                    .filter(op -> op.matching(memory[instructionPointer]))
                    .findFirst()
                    .orElseThrow(ProgramTerminated::new)
                    .execute(this);
        }
        System.out.println("VM finished execution");
        System.out.println("Memory dump: " + Arrays.toString(memory));
    }
}

enum ParameterMode {
    IMMEDIATE, POSITION
}

interface Opcode {
    boolean matching(int opcodeDef);

    void execute(VM vm);

    default ParameterMode[] paramModes(String opcode) {
        ParameterMode[] paramModes = new ParameterMode[3];
        paramModes[0] = opcode.matches("..1..") ? IMMEDIATE : POSITION;
        paramModes[1] = opcode.matches(".1...") ? IMMEDIATE : POSITION;
        paramModes[2] = opcode.matches("1....") ? IMMEDIATE : POSITION;
        return paramModes;
    }

    default int getArg1(VM vm, ParameterMode[] paramModes) {
        return getValue(vm.instructionPointer + 1, paramModes[0], vm);
    }

    default int getArg2(VM vm, ParameterMode[] paramModes) {
        return getValue(vm.instructionPointer + 2, paramModes[1], vm);
    }

    default int getValue(int index, ParameterMode paramMode, VM vm) {
        int asImmediate = vm.memory[index];
        int asPointer = vm.memory[asImmediate];
        return paramMode == IMMEDIATE ? asImmediate : asPointer;
    }
}

class Opcode_01 implements Opcode {
    private ParameterMode[] paramModes;

    @Override
    public boolean matching(int opcodeDef) {
        String opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...01");
    }

    @Override
    public void execute(VM vm) {
        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int result = arg1 + arg2;
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;
    }
}

class Opcode_02 implements Opcode {
    private ParameterMode[] paramModes;

    @Override
    public boolean matching(int opcodeDef) {
        String opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...02");
    }

    @Override
    public void execute(VM vm) {
        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int result = arg1 * arg2;
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;
    }
}

class Opcode_99 implements Opcode {

    @Override
    public boolean matching(int opcodeDef) {
        String opcode = Utils.toString(opcodeDef);
        return opcode.matches("...99");
    }

    @Override
    public void execute(VM vm) {
        vm.isRunning = false;
    }
}

class Opcode_03 implements Opcode {

    @Override
    public boolean matching(int opcodeDef) {
        String opcode = Utils.toString(opcodeDef);
        return Utils.toString(opcodeDef).matches("...03");
    }

    @Override
    public void execute(VM vm) {

    }
}

class Opcode_04 implements Opcode {

    @Override
    public boolean matching(int opcodeDef) {
        String opcode = Utils.toString(opcodeDef);
        return Utils.toString(opcodeDef).matches("...04");
    }

    @Override
    public void execute(VM vm) {

    }
}

class Utils {
    static String toString(int opcode) {
        String strOpcode = String.valueOf(opcode);
        return StringUtils.leftPad(strOpcode, 5, '0');
    }
}
