package com.grizwold.aoc;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.grizwold.aoc.ParameterMode.IMMEDIATE;
import static com.grizwold.aoc.ParameterMode.POSITION;

public class Day5 {
    public static void main(String[] args) {
        Day5 day5 = new Day5();
//        day5.run("1,9,10,3,2,3,11,0,99,30,40,50");
//        day5.run("1,1,1,4,99,5,6,0,99");
//        day5.run("3,0,4,0,99");
//        day5.run("1002,4,3,4,33");
        day5.run("3,225,1,225,6,6,1100,1,238,225,104,0,101,14,135,224,101,-69,224,224,4,224,1002,223,8,223,101,3,224,224,1,224,223,223,102,90,169,224,1001,224,-4590,224,4,224,1002,223,8,223,1001,224,1,224,1,224,223,223,1102,90,45,224,1001,224,-4050,224,4,224,102,8,223,223,101,5,224,224,1,224,223,223,1001,144,32,224,101,-72,224,224,4,224,102,8,223,223,101,3,224,224,1,223,224,223,1102,36,93,225,1101,88,52,225,1002,102,38,224,101,-3534,224,224,4,224,102,8,223,223,101,4,224,224,1,223,224,223,1102,15,57,225,1102,55,49,225,1102,11,33,225,1101,56,40,225,1,131,105,224,101,-103,224,224,4,224,102,8,223,223,1001,224,2,224,1,224,223,223,1102,51,39,225,1101,45,90,225,2,173,139,224,101,-495,224,224,4,224,1002,223,8,223,1001,224,5,224,1,223,224,223,1101,68,86,224,1001,224,-154,224,4,224,102,8,223,223,1001,224,1,224,1,224,223,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,108,226,677,224,1002,223,2,223,1006,224,329,1001,223,1,223,1007,226,226,224,1002,223,2,223,1006,224,344,101,1,223,223,1008,226,226,224,102,2,223,223,1006,224,359,1001,223,1,223,107,226,677,224,1002,223,2,223,1005,224,374,101,1,223,223,1107,677,226,224,102,2,223,223,1006,224,389,101,1,223,223,108,677,677,224,102,2,223,223,1006,224,404,1001,223,1,223,1108,677,226,224,102,2,223,223,1005,224,419,101,1,223,223,1007,677,226,224,1002,223,2,223,1006,224,434,101,1,223,223,1107,226,226,224,1002,223,2,223,1006,224,449,101,1,223,223,8,677,226,224,102,2,223,223,1006,224,464,1001,223,1,223,1107,226,677,224,102,2,223,223,1005,224,479,1001,223,1,223,1007,677,677,224,102,2,223,223,1005,224,494,1001,223,1,223,1108,677,677,224,102,2,223,223,1006,224,509,101,1,223,223,1008,677,677,224,102,2,223,223,1005,224,524,1001,223,1,223,107,226,226,224,1002,223,2,223,1005,224,539,101,1,223,223,7,226,226,224,102,2,223,223,1005,224,554,101,1,223,223,1108,226,677,224,1002,223,2,223,1006,224,569,1001,223,1,223,107,677,677,224,102,2,223,223,1005,224,584,101,1,223,223,7,677,226,224,1002,223,2,223,1005,224,599,101,1,223,223,108,226,226,224,1002,223,2,223,1005,224,614,101,1,223,223,1008,677,226,224,1002,223,2,223,1005,224,629,1001,223,1,223,7,226,677,224,102,2,223,223,1005,224,644,101,1,223,223,8,677,677,224,102,2,223,223,1005,224,659,1001,223,1,223,8,226,677,224,102,2,223,223,1006,224,674,1001,223,1,223,4,223,99,226");
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
        System.out.printf("Memory dump: %s\n\n", Arrays.toString(memory));
    }
}

enum ParameterMode {
    IMMEDIATE,
    POSITION
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
        return paramMode == IMMEDIATE ?
                vm.memory[index] :
                vm.memory[vm.memory[index]];
    }
}

class Opcode_01 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...01");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int result = arg1 + arg2;
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;

        System.out.printf("%s + %s = %s (@ %s) | parameter modes: %s\n", arg1, arg2, result, resultPointer, Arrays.toString(paramModes));
    }
}

class Opcode_02 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...02");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int result = arg1 * arg2;
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);

        vm.memory[resultPointer] = result;
        vm.instructionPointer += 4;

        System.out.printf("%s * %s = %s (@ %s) | parameter modes: %s\n", arg1, arg2, result, resultPointer, Arrays.toString(paramModes));
    }
}

class Opcode_99 implements Opcode {
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        return opcode.matches("...99");
    }

    @Override
    public void execute(VM vm) {
        vm.isRunning = false;

        System.out.printf("@%s {%s} VM finished execution\n", vm.instructionPointer, opcode);
    }
}

class Opcode_03 implements Opcode {
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        return opcode.matches("...03");
    }

    @Override
    public void execute(VM vm) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter number: ");
        int input = sc.nextInt();

        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);
        int resultPointer = getValue(vm.instructionPointer + 1, IMMEDIATE, vm);

        vm.memory[resultPointer] = input;
        vm.instructionPointer += 2;

        System.out.printf("read to %s\n", resultPointer);
    }
}

class Opcode_04 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...04");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int result = getArg1(vm, paramModes);
        vm.instructionPointer += 2;

        System.out.printf("print: \"%s\" | modes: %s\n", result, paramModes[0]);
    }
}

class Utils {
    static String toString(int opcode) {
        String strOpcode = String.valueOf(opcode);
        return StringUtils.leftPad(strOpcode, 5, '0');
    }
}
