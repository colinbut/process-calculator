/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.processcalculator;

import com.mycompany.processcalculator.math.AdditionFormula;
import com.mycompany.processcalculator.math.DivisionFormula;
import com.mycompany.processcalculator.math.MathFormula;
import com.mycompany.processcalculator.math.MultiplicationFormula;
import com.mycompany.processcalculator.math.SubtractionFormula;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ProcessCalculator {

    private static Map<MathOperation, MathFormula> mathFormulaMap = new EnumMap<>(MathOperation.class);

    static {
        mathFormulaMap.put(MathOperation.ADD, new AdditionFormula());
        mathFormulaMap.put(MathOperation.SUBTRACT, new SubtractionFormula());
        mathFormulaMap.put(MathOperation.MULTIPLY, new MultiplicationFormula());
        mathFormulaMap.put(MathOperation.DIVIDE, new DivisionFormula());
    }

    public static void main(String[] args) {

        // setup an example data set
        List<String> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/main/resources/example1.txt")))) {
            String line;
            while((line = reader.readLine()) != null) {
                instructions.add(line);
            }
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
            System.exit(-1);
        }

        Map<MathOperation, Integer> mathInstructions = new EnumMap<>(MathOperation.class);

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
        if (!applyInstructions.contains(Instruction.APPLY)) {
            throw new ProcessCalculationException("First instruction expected to be: " + Instruction.APPLY);
        }

        String[] applyInstructionsItems = applyInstructions.split(" ");
        String applyInstruction = applyInstructionsItems[0];
        if (!applyInstruction.equals(Instruction.APPLY)) {
            throw new IllegalStateException("Apply instruction should be apply");
        }

        int total = Integer.parseInt(applyInstructionsItems[1]);

        for (Map.Entry<MathOperation, Integer> mathInstruction : mathInstructions.entrySet()) {
            MathFormula mathFormula = mathFormulaMap.get(mathInstruction.getKey());
            total = mathFormula.calculate(total, mathInstruction.getValue());
        }

        log.info("{}", total);
    }
}
