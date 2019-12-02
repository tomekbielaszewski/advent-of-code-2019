fn execute_program(verb: i32, noun: i32, memory: &mut [i32]) {
    memory[1] = verb;
    memory[2] = noun;
    let mut instruction_pointer: usize = 0;

    loop {
        let finished: bool = execute_opcode_at(instruction_pointer, memory);
        instruction_pointer += 4;
        if finished {
            break;
        }
    }
}

fn execute_opcode_at(instruction_pointer: usize, memory: &mut [i32]) -> bool {
    let opcode: i32 = memory[instruction_pointer];
    let program_terminated = match opcode {
        1 => add(instruction_pointer, memory),
        2 => multiply(instruction_pointer, memory),
        99 => true,
        _ => true
    };
    program_terminated
}

fn add(instruction_pointer: usize, memory: &mut [i32]) -> bool {
    let arg1: usize = memory[instruction_pointer + 1] as usize;
    let arg2: usize = memory[instruction_pointer + 2] as usize;
    let result: usize = memory[instruction_pointer + 3] as usize;

    memory[result] = memory[arg1] + memory[arg2];
    false
}

fn multiply(instruction_pointer: usize, memory: &mut [i32]) -> bool {
    let arg1: usize = memory[instruction_pointer + 1] as usize;
    let arg2: usize = memory[instruction_pointer + 2] as usize;
    let result: usize = memory[instruction_pointer + 3] as usize;

    memory[result] = memory[arg1] * memory[arg2];
    false
}

#[cfg(test)]
mod test {
    use super::*;

    #[test]
    fn examples() {
        example(&mut [1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50], &[3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50]);
        example(&mut [1, 0, 0, 0, 99], &[2, 0, 0, 0, 99]);
        example(&mut [2, 3, 0, 3, 99], &[2, 3, 0, 6, 99]);
        example(&mut [2, 4, 4, 5, 99, 0], &[2, 4, 4, 5, 99, 9801]);
        example(&mut [1, 1, 1, 4, 99, 5, 6, 0, 99], &[30, 1, 1, 4, 2, 5, 6, 0, 99]);
    }

    fn example(input: &mut [i32], expected_output: &[i32]) {
        execute_program(input[1], input[2], input);
        assert_eq!(input, expected_output);
    }


    #[test]
    fn solutions() {
        let input: &mut [i32] = &mut [1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 1, 6, 19, 1, 9, 19, 23, 1, 6, 23, 27, 1, 10, 27, 31, 1, 5, 31, 35, 2, 6, 35, 39, 1, 5, 39, 43, 1, 5, 43, 47, 2, 47, 6, 51, 1, 51, 5, 55, 1, 13, 55, 59, 2, 9, 59, 63, 1, 5, 63, 67, 2, 67, 9, 71, 1, 5, 71, 75, 2, 10, 75, 79, 1, 6, 79, 83, 1, 13, 83, 87, 1, 10, 87, 91, 1, 91, 5, 95, 2, 95, 10, 99, 2, 9, 99, 103, 1, 103, 6, 107, 1, 107, 10, 111, 2, 111, 10, 115, 1, 115, 6, 119, 2, 119, 9, 123, 1, 123, 6, 127, 2, 127, 10, 131, 1, 131, 6, 135, 2, 6, 135, 139, 1, 139, 5, 143, 1, 9, 143, 147, 1, 13, 147, 151, 1, 2, 151, 155, 1, 10, 155, 0, 99, 2, 14, 0, 0];

        execute_program(12, 2, input);
        println!("solution for first: {}", input[0]);

        for i in 0..99 {
            for j in 0..99 {
                let temp = &mut [1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 1, 6, 19, 1, 9, 19, 23, 1, 6, 23, 27, 1, 10, 27, 31, 1, 5, 31, 35, 2, 6, 35, 39, 1, 5, 39, 43, 1, 5, 43, 47, 2, 47, 6, 51, 1, 51, 5, 55, 1, 13, 55, 59, 2, 9, 59, 63, 1, 5, 63, 67, 2, 67, 9, 71, 1, 5, 71, 75, 2, 10, 75, 79, 1, 6, 79, 83, 1, 13, 83, 87, 1, 10, 87, 91, 1, 91, 5, 95, 2, 95, 10, 99, 2, 9, 99, 103, 1, 103, 6, 107, 1, 107, 10, 111, 2, 111, 10, 115, 1, 115, 6, 119, 2, 119, 9, 123, 1, 123, 6, 127, 2, 127, 10, 131, 1, 131, 6, 135, 2, 6, 135, 139, 1, 139, 5, 143, 1, 9, 143, 147, 1, 13, 147, 151, 1, 2, 151, 155, 1, 10, 155, 0, 99, 2, 14, 0, 0];
                execute_program(i, j, temp);
                if temp[0] == 19690720 {
                    println!("Solution for second: {}", 100 * i + j);
                    break;
                }
            }
        }
    }
}
