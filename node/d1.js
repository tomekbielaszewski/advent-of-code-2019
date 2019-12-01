let _ = require('lodash');

function calcFuel(mass) {
  return Math.floor(mass/3) -2;
}

function solution1(input) {
  return _.sum(input.map(calcFuel));
}

function solution2(input) {

}

module.exports = {
  solution1: solution1,
  solution2: solution2
};
