fn execute_program(verb: i16, noun: i16, memory: &mut [i16]) {
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

fn execute_opcode_at(instruction_pointer: usize, memory: &mut [i16]) -> bool {
    let opcode: i16 = memory[instruction_pointer];
    let program_terminated = match opcode {
        1 => add(instruction_pointer, memory),
        2 => multiply(instruction_pointer, memory),
        99 => true,
        _ => true
    };
    program_terminated
}

fn add(instruction_pointer: usize, memory: &mut [i16]) -> bool {
    let arg1: usize = memory[instruction_pointer + 1] as usize;
    let arg2: usize = memory[instruction_pointer + 2] as usize;
    let result: usize = memory[instruction_pointer + 3] as usize;

    memory[result] = memory[arg1] + memory[arg2];
    false
}

fn multiply(instruction_pointer: usize, memory: &mut [i16]) -> bool {
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

    fn example(input: &mut [i16], expected_output: &[i16]) {
        execute_program(input[1], input[2], input);
        assert_eq!(input, expected_output);
    }

    #[test]
    fn solutions() {
//        println!("solution for first: {}", execute_program(12, 2, []));
//        println!("solution for second: {}", execute_program(12, 2, []));
    }
}
