package demo;

import demo.model.*;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Component
@ComponentScan(basePackages = "demo")
public class Main {
    private final Set<String> validFactValue = new HashSet<>(Arrays.asList("y", "n"));

    @Autowired
    private InternalKnowledgeBase knowledgeBase;

    public static void main(String[] args) throws Exception {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(Main.class);

        Main p = context.getBean(Main.class);
        p.start(args);
    }

    public void start(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("answer with [y] or [n], write exit to close program");

        String nextOp = "n";
        while(!nextOp.equals("y")) {
            System.out.println("\nnew session...");

            KieSession kieSession = knowledgeBase.newKieSession();
            Result result = new Result();
            kieSession.setGlobal("resultObj", result);
            kieSession.fireAllRules();

            System.out.print("is water level high?: ");
            String isWaterLevelHigh = scanner.next();

            try {
                WaterLevel waterLevel = new WaterLevel(parseFactValue(isWaterLevelHigh));
                kieSession.insert(waterLevel);
                kieSession.fireAllRules();
                if (result.isInit()) {
                    showDecision(result.getDecision());
                    nextOp = readNextOperation(scanner);
                    continue;
                }
            } catch (Exception e) {
                nextOp = readNextOperation(scanner);
                continue;
            }

            System.out.print("is rain heavy?: ");
            String isRainHeavy = scanner.next();

            try {
                Rain rain = new Rain(parseFactValue(isRainHeavy));
                kieSession.insert(rain);
                kieSession.fireAllRules();
                if (result.isInit()) {
                    showDecision(result.getDecision());
                    nextOp = readNextOperation(scanner);
                    continue;
                }
            } catch (Exception e) {
                nextOp = readNextOperation(scanner);
                continue;
            }

            System.out.print("is a lot of snow?: ");
            String isALotOfSnow = scanner.next();

            try {
                Snow snow = new Snow(parseFactValue(isALotOfSnow));
                kieSession.insert(snow);
                kieSession.fireAllRules();
                if (result.isInit()) {
                    showDecision(result.getDecision());
                    nextOp = readNextOperation(scanner);
                    continue;
                }
            } catch (Exception e) {
                nextOp = readNextOperation(scanner);
                continue;
            }

            System.out.print("is temperature high?: ");
            String isTemperatureHigh = scanner.next();

            try {
                Temperature temperature = new Temperature(parseFactValue(isTemperatureHigh));
                kieSession.insert(temperature);
                kieSession.fireAllRules();
                if (result.isInit()) {
                    showDecision(result.getDecision());
                    nextOp = readNextOperation(scanner);
                    continue;
                }
            } catch (Exception e) {
                nextOp = readNextOperation(scanner);
                continue;
            }
        }
    }

    private boolean parseFactValue(String value) {
        if (value.equals("exit"))
            System.exit(0);
        if (!validFactValue.contains(value)) {
            System.out.println("value input: y, n");
            throw new IllegalArgumentException();
        }
        return value.equals("y");
    }

    private void showDecision(Decision decision) {
        System.out.println("Decision: " + decision);
    }

    private String readNextOperation(Scanner scanner) {
        System.out.println("exit? [y]/[n]: ");
        return scanner.next();
    }
}
