package com.grizwold.aoc;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.grizwold.aoc.ParameterMode.IMMEDIATE;
import static com.grizwold.aoc.ParameterMode.POSITION;

public class Day5 {
    public int[] run(String input, InputStream in, PrintStream out) {
        VM vm = new VM(in, out);
        vm.memory = toArray(input);
        vm.execute();
        return vm.memory;
    }

    int[] toArray(String str) {
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

    final InputStream in;
    final PrintStream out;

    private Set<Opcode> opcodes = new HashSet<>();

    VM(InputStream in, PrintStream out) {
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
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        Scanner sc = new Scanner(vm.in);
        int input = sc.nextInt();
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
        vm.out.println(result);

        System.out.printf(" print: \"%s\" | modes: %s\n", result, paramModes[0]);

    }
}

class Opcode_05 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...05");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int result = getArg1(vm, paramModes);
        int jump = getArg2(vm, paramModes);
        if (result != 0) {
            vm.instructionPointer = jump;
        } else {
            vm.instructionPointer += 3;
        }

        System.out.printf("jump to %s if %s != 0 | modes: %s\n", jump, result, paramModes[0]);
    }
}

class Opcode_06 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...06");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int result = getArg1(vm, paramModes);
        int jump = getArg2(vm, paramModes);
        if (result == 0) {
            vm.instructionPointer = jump;
        } else {
            vm.instructionPointer += 3;
        }

        System.out.printf("jump to %s if %s == 0 | modes: %s\n", jump, result, paramModes[0]);
    }
}

class Opcode_07 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...07");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);
        vm.memory[resultPointer] = (arg1 < arg2) ? 1 : 0;
        vm.instructionPointer += 4;

        System.out.printf("if %s < %s then 1 (@ %s); operation saved %s | modes: %s\n", arg1, arg2, resultPointer, vm.memory[resultPointer], paramModes[0]);
    }
}

class Opcode_08 implements Opcode {
    private ParameterMode[] paramModes;
    private String opcode;

    @Override
    public boolean matching(int opcodeDef) {
        opcode = Utils.toString(opcodeDef);
        paramModes = paramModes(opcode);
        return opcode.matches("...08");
    }

    @Override
    public void execute(VM vm) {
        System.out.printf("@%s {%s} ", vm.instructionPointer, opcode);

        int arg1 = getArg1(vm, paramModes);
        int arg2 = getArg2(vm, paramModes);
        int resultPointer = getValue(vm.instructionPointer + 3, IMMEDIATE, vm);
        vm.memory[resultPointer] = (arg1 == arg2) ? 1 : 0;
        vm.instructionPointer += 4;

        System.out.printf("if %s == %s then 1 (@ %s); operation saved %s | modes: %s\n", arg1, arg2, resultPointer, vm.memory[resultPointer], paramModes[0]);
    }
}

class Utils {
    static String toString(int opcode) {
        String strOpcode = String.valueOf(opcode);
        return StringUtils.leftPad(strOpcode, 5, '0');
    }
}
