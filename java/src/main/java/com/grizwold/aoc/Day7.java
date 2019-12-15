package com.grizwold.aoc;

import com.grizwold.aoc.intcode.VM;

import java.util.concurrent.*;

public class Day7 {
    public long runSequence(String program, Integer[] sequence) {
        if (sequence == null || sequence.length != 5)
            throw new IllegalArgumentException("sequence has to be 5 digits long");

        long lastResult = 0;
        for (int i = 0; i < sequence.length; i++) {
            int input = sequence[i];
            lastResult = run(program, input, lastResult);
        }

        return lastResult;
    }

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public long runSequenceInFeedbackLoop(String program, Long[] sequence) {
        BlockingQueue<Long> aIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Long> bIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Long> cIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Long> dIn = new ArrayBlockingQueue<>(10);
        BlockingQueue<Long> eIn = new ArrayBlockingQueue<>(10);

        VM vmA = new VM(aIn, bIn);
        VM vmB = new VM(bIn, cIn);
        VM vmC = new VM(cIn, dIn);
        VM vmD = new VM(dIn, eIn);
        VM vmE = new VM(eIn, aIn);

        vmA.write(sequence[0], 0l);
        vmB.write(sequence[1]);
        vmC.write(sequence[2]);
        vmD.write(sequence[3]);
        vmE.write(sequence[4]);

        executor.submit(() -> vmA.execute(program));
        executor.submit(() -> vmB.execute(program));
        executor.submit(() -> vmC.execute(program));
        executor.submit(() -> vmD.execute(program));
        Future<long[]> vmEFuture = executor.submit(() -> vmE.execute(program));

        try {
            vmEFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return vmE.read();
    }

    private Long run(String program, long input1, long input2) {
        VM vm = new VM(input1, input2);
        vm.execute(program);
        return vm.getOutput().poll();
    }
}
