/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.processcalculator.math;

public class DivisionFormula implements MathFormula {
    @Override
    public int calculate(int a, int b) {
        return a / b;
    }
}
