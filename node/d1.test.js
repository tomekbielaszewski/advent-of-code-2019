const d1 = require('./d1');

test('example 1.1', () => {
  expect(d1.solution1([12])).toBe(2);
});

test('example 1.2', () => {
  expect(d1.solution1([14])).toBe(2);
});

test('example 1.3', () => {
  expect(d1.solution1([1969])).toBe(654);
});

test('example 1.4', () => {
  expect(d1.solution1([100756])).toBe(33583);
});

test('example 2.1', () => {
  expect(d1.solution2([14])).toBe(2);
});

test('example 2.2', () => {
  expect(d1.solution2([1969])).toBe(966);
});

test('example 2.3', () => {
  expect(d1.solution2([100756])).toBe(50346);
});

test('solution 1', () => {
  console.log('d1.s1 ' + d1.solution1([
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
    84878
  ]));
});

test('solution 2', () => {
  console.log('d1.s2 ' + d1.solution2([
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
    84878
  ]));
});