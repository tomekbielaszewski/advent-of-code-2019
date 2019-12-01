let _ = require('lodash');

function solution1(input) {
  return _.sum(input.map(i => i/3)
  .map(i => Math.floor(i))
  .map(i => i-2));
}

function solution2(input) {

}

module.exports = {
  solution1: solution1,
  solution2: solution2
};
