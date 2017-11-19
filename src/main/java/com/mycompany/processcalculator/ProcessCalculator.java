/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.processcalculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessCalculator {

    public static void main(String[] args) {

        // setup an example data set
        List<String> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/resources/example1.txt")))) {
            String line;
            while((line = reader.readLine()) != null) {
                instructions.add(line);
            }
            System.out.println(instructions);
        } catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
            System.exit(-1);
        }

        Map<MathOperation, Integer> mathInstructions = new HashMap<>();

        for (String instruction : instructions) {
            if (instruction.contains(Instruction.APPLY)) {
                continue;
            }

            String[] items = instruction.split(" ");
            MathOperation mathOperation = MathOperation.getMathOperationByName(items[0]);
            int number = Integer.parseInt(items[1]);
            mathInstructions.put(mathOperation, number);
        }

        String applyInstructions = instructions.remove(instructions.size() - 1);
        if (!applyInstructions.contains("apply")) {
            throw new ProcessCalculationException("First instruction expected to be: " + Instruction.APPLY);
        }

        String[] applyInstructionsItems = applyInstructions.split(" ");
        String applyInstruction = applyInstructionsItems[0];
        if (!applyInstruction.equals(Instruction.APPLY)) {
            throw new IllegalStateException("Apply instruction should be apply");
        }

        int total = Integer.parseInt(applyInstructionsItems[1]);

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

        System.out.println(total);
    }
}
