/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.processcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessCalculator {

    enum MathOperation {
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

    class ProcessCalculationException extends RuntimeException {
        ProcessCalculationException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {

        List<String> instructions = new ArrayList<String>();
        instructions.add("add 2");
        instructions.add("multiply 3");
        instructions.add("apply 3");

        Map<MathOperation, Integer> mathInstructions = new HashMap<MathOperation, Integer>();

        for (String instruction : instructions) {
            if (instruction.contains("apply")) {
                continue;
            }

            String[] items = instruction.split(" ");
            MathOperation mathOperation = MathOperation.getMathOperationByName(items[0]);
            int number = Integer.parseInt(items[1]);
            mathInstructions.put(mathOperation, number);
        }

        String applyInstructions = instructions.remove(instructions.size() - 1);
        if (!applyInstructions.contains("apply")) {
            throw new IllegalStateException("Invalid format");
        }

        String[] applyInstructionsItems = applyInstructions.split(" ");
        String applyInstruction = applyInstructionsItems[0];
        int number = Integer.parseInt(applyInstructionsItems[1]);

        int total = number;
        for (Map.Entry<MathOperation, Integer> mathInstruction : mathInstructions.entrySet()) {
            switch (mathInstruction.getKey()) {
                case ADD:
                    total += mathInstruction.getValue();
                    break;
                case SUBTRACT:
                    total -= mathInstruction.getValue();
                    break;
                case MULTIPLY:
                    total *= mathInstruction.getValue();
                    break;
                case DIVIDE:
                    total /= mathInstruction.getValue();
                    break;
                    default:
                        throw new IllegalStateException("Invalid MathOperation to apply");
            }
        }
    }
}
