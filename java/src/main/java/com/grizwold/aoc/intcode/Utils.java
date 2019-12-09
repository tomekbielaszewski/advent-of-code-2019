package com.grizwold.aoc.intcode;

import org.apache.commons.lang3.StringUtils;

class Utils {
    static String toString(int opcode) {
        String strOpcode = String.valueOf(opcode);
        return StringUtils.leftPad(strOpcode, 5, '0');
    }
}
