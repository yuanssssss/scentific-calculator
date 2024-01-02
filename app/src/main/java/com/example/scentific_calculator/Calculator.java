package com.example.scentific_calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static Map<String,Integer> priority=new HashMap<>();
    private static List<String> rawExpression=new ArrayList<>();
    private static List<String>postfixExpression=new ArrayList<>();
    public static void initPriority(){
        priority.put("(",0);priority.put(")",0);priority.put("+",1);priority.put("-",1);
        priority.put("*",2);priority.put("/",2);priority.put("mod",2);priority.put("%",3);
        priority.put("sin",3);priority.put("cos",3);priority.put("tan",3);priority.put("log",3);
        priority.put("ln",3);priority.put("\u221a",3);priority.put("^",3);priority.put("|x|",3);
        priority.put("1/x",3);priority.put("n!",3);
    }
    private static void getNifixExpression(String expression) {
        rawExpression.clear();
        Pattern pattern=Pattern.compile("-?\\d*\\.\\d+|-?\\d+|\\u03C0|\\u221a|[+\\-*/%^]|e|sin|cos|tan|1/x|\\|x\\||mod|log|ln|n!|[(]|[)]");
        Matcher matcher= pattern.matcher(expression);
        while (matcher.find()){
            rawExpression.add(matcher.group());
        }
    }
    public static void nifix2Postfix(String expression){
        getNifixExpression(expression);
        Stack<String> operatorStack=new Stack<>();
        for(String val:rawExpression){
            if(val.equals("\u03C0")||val.equals("e")||val.matches("-?\\d*\\.\\d+|-?\\d+")){
                postfixExpression.add(val);
            }
            else if(operatorStack.isEmpty()||val.equals("(")||priority.get(operatorStack.peek())<priority.get(val)) {
                operatorStack.push(val);
            }
            else if(val.equals(")")){
                while (!operatorStack.peek().equals("(")){
                    postfixExpression.add(operatorStack.pop());
                }
                operatorStack.pop();
            }
            else {
                while ( !(operatorStack.isEmpty())&&(priority.get(operatorStack.peek())>=priority.get(val))){
                    postfixExpression.add(operatorStack.pop());
                }
                operatorStack.push(val);
            }

        }
        while (!operatorStack.isEmpty())postfixExpression.add(operatorStack.pop());
    }
    public static double evaluatePostfixExpression(String expression){
        postfixExpression.clear();
        nifix2Postfix(expression);
        Stack<Double>numStack=new Stack<>();
        for(String val:postfixExpression){
            if(val.matches("-?\\d*\\.\\d+|-?\\d+")){
                numStack.push(Double.parseDouble(val));
            }
            else if(val.equals("e")){
                numStack.push(Math.E);
            }else if(val.equals("\u03c0")){
                numStack.push(Math.PI);
            }
            else if(val.matches("[+\\-/*^]|mod")){
                double tmp1=0,tmp2=0;
                if (!numStack.isEmpty()) tmp1=numStack.pop();
                if (!numStack.isEmpty())tmp2=numStack.pop();
                switch (val){
                    case "+":numStack.push(tmp2+tmp1);break;
                    case "-":numStack.push(tmp2-tmp1);break;
                    case "*":numStack.push(tmp2*tmp1);break;
                    case "/":numStack.push(tmp2/tmp1);break;
                    case "^":numStack.push(Math.pow(tmp2,tmp1));break;
                    case "mod":numStack.push(tmp2%tmp1);break;
                }
            }
            else if(val.matches("sin|cos|tan|log|ln|1/x|n!|\\|x\\||\\u221a|%")){
                double tmp=0;
                if (!numStack.isEmpty())tmp=numStack.pop();
                switch (val){
                    case "sin":numStack.push(Math.sin(tmp));break;
                    case "cos":numStack.push(Math.cos(tmp));break;
                    case "tan":numStack.push(Math.tan(tmp));break;
                    case "log":numStack.push(Math.log10(tmp));break;
                    case "ln" :numStack.push(Math.log(tmp));break;
                    case "\u221a":numStack.push(Math.sqrt(tmp));break;
                    case "%":numStack.push(tmp*0.01);break;
                }
            }
        }
        return  numStack.pop();
    }

}
