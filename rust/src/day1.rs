fn calc_fuel(mass: i32) -> i32 {
    mass / 3 - 2
}

fn calc_extra_fuel(mass: i32) -> i32 {
    let fuel: i32 = calc_fuel(mass);
    if fuel > 0 {
        return fuel + calc_extra_fuel(fuel);
    }
    return 0;
}

fn multiple_modules(modules: &[i32], fuel_calculator: &dyn Fn(i32) -> i32) -> i32 {
    let mut total_fuel: i32 = 0;
    for mass in modules {
        total_fuel += fuel_calculator(*mass);
    }
    return total_fuel;
}

#[cfg(test)]
mod test {
    use super::*;

    #[test]
    fn examples1() {
        assert_eq!(calc_fuel(12), 2);
        assert_eq!(calc_fuel(14), 2);
        assert_eq!(calc_fuel(1969), 654);
        assert_eq!(calc_fuel(100756), 33583);
    }

    #[test]
    fn examples2() {
        assert_eq!(calc_extra_fuel(14), 2);
        assert_eq!(calc_extra_fuel(1969), 966);
        assert_eq!(calc_extra_fuel(100756), 50346);
    }

    #[test]
    fn solutions() {
        let input = [
            54296,
            106942,
            137389,
            116551,
            129293,
            60967,
            142448,
            101720,
            64463,
            142264,
            68673,
            144661,
            110426,
            59099,
            63711,
            120365,
            125233,
            126793,
            61990,
            122059,
            86768,
            134293,
            114985,
            61280,
            75325,
            103102,
            116332,
            112075,
            114895,
            98816,
            59389,
            124402,
            74995,
            135512,
            115619,
            59680,
            61421,
            141160,
            148880,
            70010,
            119379,
            92155,
            126698,
            138653,
            149004,
            142730,
            68658,
            73811,
            87064,
            62684,
            93335,
            140475,
            143377,
            98445,
            117960,
            80237,
            132483,
            108319,
            104154,
            99383,
            104685,
            114888,
            73376,
            58590,
            132759,
            114399,
            77796,
            119228,
            136282,
            84789,
            66511,
            51939,
            142313,
            117305,
            139543,
            92054,
            64606,
            139795,
            109051,
            97040,
            91850,
            107391,
            60200,
            75812,
            74898,
            64884,
            115210,
            85055,
            92256,
            67470,
            90286,
            129142,
            109235,
            117194,
            104028,
            127482,
            68502,
            92440,
            50369,
            84878,
        ];

        println!("solution for first: {}", multiple_modules(&input, &calc_fuel));
        println!("solution for second: {}", multiple_modules(&input, &calc_extra_fuel));
    }
}
