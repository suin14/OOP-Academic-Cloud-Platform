package Sys;

import User.*;

public class Sys {
    private final UserData userData; // 用户数据管理

    public Sys() {
        userData = UserData.getInstance(); // 初始化用户数据管理
    }

    public void quit() {
        // TODO：让用户下线
        System.out.print("----- Good Bye! -----");
    }

    public void register(String[] params) {
        String id = params[0];
        String type = id.substring(0, 2); // 取前两个字符判断类型

        switch (type) { // 学工号
            case "SY", "ZY" -> {
                Master m = new Master(params[0], params[1], params[2], params[3], params[4]);
            }
            case "BY" -> {
                Doctoral d = new Doctoral(params[0], params[1], params[2], params[3], params[4]);
            }
            case "AD" -> {
                Administrator a = new Administrator(params[0], params[1], params[2], params[3], params[4]);
            }
            default -> {
                if (id.matches("^[0-9].*")) {
                    if (id.length() == 8) {
                        Undergraduate u = new Undergraduate(params[0], params[1], params[2], params[3], params[4]);
                    } else if (id.length() == 5) {
                        Teacher t = new Teacher(params[0], params[1], params[2], params[3], params[4]);
                    } else {
                        System.out.println(Feedback.ILLEGAL_USER_ID);
                    }
                } else {
                    System.out.println(Feedback.ILLEGAL_USER_ID);
                }
            }
        }




    }
}

