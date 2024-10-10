import Sys.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input.txt"); Scanner scan = new Scanner(inputFile);
//        Scanner scan = new Scanner(System.in);
        String input;
        Sys system = new Sys();

        while(true) {
            input = scan.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            String[] tokens = input.split("\\s+");
            String command = tokens[0];
            String[] params = getParameters(tokens);

            try {
                switch (Command.valueOf(command)) {
                    case quit: // 退出系统
                        if (params.length != 0){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        system.quit();
                        return;
                    case register:
                        if (params.length != 5){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("register params ok!");
                        break;
                    case login:
                        if (params.length != 2){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("login params ok!");
                        break;
                    case logout:
                        if (params.length != 1){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("logout params ok!");
                        break;
                    case printInfo:
                        if (params.length == 0){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("params ok!");
                        break;
                    case createCourse:
                        if (params.length != 4){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("params ok!");
                        break;
                    case listCourse:
                        if (params.length == 0){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("params ok!");
                        break;
                    case selectCourse:
                        if (params.length != 1){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("params ok!");
                        break;
                    case cancelCourse:
                        if (params.length != 1){
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        System.out.println("params ok!");
                        break;
                    default:
                        System.out.println("Command '" + command + "' not found");
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Command '" + command + "' not found");
            }

//            System.out.println(String.join(", ", params));
        }
    }

    private static String[] getParameters(String[] tokens) {
        if (tokens.length <= 1) {
            return new String[0];  // 没有参数
        }
        String[] params = new String[tokens.length - 1];
        System.arraycopy(tokens, 1, params, 0, tokens.length - 1);
        return params;
    }
}
