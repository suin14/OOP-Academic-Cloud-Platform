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
        User person = null; // 用于存储创建的用户对象

        switch (type) { // 学工号
            case "SY", "ZY":
                // 硕士生
                person = new Master(params[0], params[1], params[2], params[3], params[4]);
                break;
            case "BY":
                // 博士生
                break;
            case "AD":
                // 教职工
                break;
            default:
                if (id.matches("^[0-9].*")) {
                    if (id.length() == 8) {
                        // 本科生
                    } else if (id.length() == 5) {
                        // 教职工
                    } else {
                        System.out.println(Feedback.ILLEGAL_USER_ID);
                        return;
                    }
                } else {
                    System.out.println(Feedback.ILLEGAL_USER_ID);
                    return;
                }
        }




    }
}

