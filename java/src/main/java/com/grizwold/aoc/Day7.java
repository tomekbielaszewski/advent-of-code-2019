package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

import java.util.concurrent.*;

public class Day7 {
    public int runSequence(String program, Integer[] sequence) {
        if (sequence == null || sequence.length != 5)
            throw new IllegalArgumentException("sequence has to be 5 digits long");

        int lastResult = 0;
        for (int i = 0; i < sequence.length; i++) {
            int input = sequence[i];
            lastResult = run(program, input, lastResult);
        }

        return lastResult;
    }

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public int runSequenceInFeedbackLoop(String program, Integer[] sequence) {
        BlockingQueue<Integer> aIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> bIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> cIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> dIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> eIn = new ArrayBlockingQueue<>(10);

        VM vmA = new VM(aIn, bIn);
        VM vmB = new VM(bIn, cIn);
        VM vmC = new VM(cIn, dIn);
        VM vmD = new VM(dIn, eIn);
        VM vmE = new VM(eIn, aIn);

        vmA.write(sequence[0], 0);
        vmB.write(sequence[1]);
        vmC.write(sequence[2]);
        vmD.write(sequence[3]);
        vmE.write(sequence[4]);

        executor.submit(() -> vmA.execute(program));
        executor.submit(() -> vmB.execute(program));
        executor.submit(() -> vmC.execute(program));
        executor.submit(() -> vmD.execute(program));
        Future<int[]> vmEFuture = executor.submit(() -> vmE.execute(program));

        try {
            vmEFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return vmE.read();
    }

    private Integer run(String program, int input1, int input2) {
        VM vm = new VM(input1, input2);
        vm.execute(program);
        return vm.getOutput().poll();
    }
}
