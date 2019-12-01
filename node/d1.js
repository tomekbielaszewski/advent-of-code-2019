let _ = require('lodash');

function calcFuel(mass) {
  return Math.floor(mass/3) -2;
}

function calcExtraFuel(mass) {
  let fuel = calcFuel(mass);
  if(fuel > 0) return fuel + calcExtraFuel(fuel);
  else return 0;
}

function solution1(input) {
  return _.sum(input.map(calcFuel));
}

function solution2(input) {
  return _.sum(input.map(calcExtraFuel));
}

module.exports = {
  solution1: solution1,
  solution2: solution2
};
