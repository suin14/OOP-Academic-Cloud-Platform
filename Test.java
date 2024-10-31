import Sys.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        File inputFile = new File("input.txt"); Scanner scan = new Scanner(inputFile);
//        Scanner scan = new Scanner(System.in);
        String input;
        Sys system = new Sys();

        while (true) {
            input = scan.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            String[] tokens = input.split("\\s+");
            String command = tokens[0];
            String[] params = getParameters(tokens);
            try {
                Command cmd;
                if (command.equals("switchUser")) {
                    cmd = Command.valueOf("none");
                } else if (command.equals("switch")) {
                    cmd = Command.switchUser; // 将 "switch" 映射到 switchUser
                } else {
                    cmd = Command.valueOf(command);
                }

                switch (cmd) {
                    case quit -> {
                        if (!validateParamCount(params, 0)) break;
                        system.quit();
                        return;
                    }
                    case register -> {
                        if (!validateParamCount(params, 5)) break;
                        system.register(params);
                    }
                    case login -> {
                        if (!validateParamCount(params, 2)) break;
                        system.login(params);
                    }
                    case logout -> {
                        if (params.length > 1) {
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        system.logout(params);
                    }
                    case printInfo -> {
                        if (params.length > 1) {
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        system.printInfo(params);
                    }
                    case createCourse -> {
                        if (!validateParamCount(params, 4)) break;
                        system.createCourse(params);
                    }
                    case listCourse -> {
                        if (params.length > 1) {
                            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
                            break;
                        }
                        system.listCourse(params);
                    }
                    case selectCourse -> {
                        if (!validateParamCount(params, 1)) break;
                        system.selectCourse(params);
                    }
                    case cancelCourse -> {
                        if (!validateParamCount(params, 1)) break;
                        system.cancelCourse(params);
                    }
                    case switchUser -> {
                        if (!validateParamCount(params, 1)) break;
                        system.switchUser(params);
                    }
                    case inputCourseBatch -> {
                        if (!validateParamCount(params, 1)) break;
                        system.inputCourseBatch(params);
                    }
                    case outputCourseBatch -> {
                        if (!validateParamCount(params, 1)) break;
                        system.outputCourseBatch(params);
                    }
                    case listStudent -> {
                        if (!validateParamCount(params, 1)) break;
                        system.listStudent(params);
                    }
                    case removeStudent -> {
                        //移除选课学生
                    }
                    case listCourseSchedule -> {
                        //查看课表
                    }
                    default -> System.out.println("Command '" + command + "' not found");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Command '" + command + "' not found");
            }
        }
    }

    private static String[] getParameters(String[] tokens) {
        return tokens.length > 1 ? Arrays.copyOfRange(tokens, 1, tokens.length) : new String[0];
    }

    private static boolean validateParamCount(String[] params, int expectedCount) {
        if (params.length != expectedCount) {
            System.out.println(Feedback.ILLEGAL_ARGUMENT_COUNT);
            return false;
        }
        return true;
    }

}
