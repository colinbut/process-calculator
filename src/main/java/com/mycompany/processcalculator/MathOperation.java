/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.processcalculator;

public enum MathOperation {
    ADD("add"),
    SUBTRACT("subtract"),
    MULTIPLY("multiply"),
    DIVIDE("divide");

    MathOperation(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public static MathOperation getMathOperationByName(String name) {
        for  (MathOperation mathOperation : values()) {
            if (mathOperation.getName().equals(name)) {
                return mathOperation;
            }
        }
        throw new IllegalArgumentException("Cannot find MathOperation with name: " + name);
    }
}
