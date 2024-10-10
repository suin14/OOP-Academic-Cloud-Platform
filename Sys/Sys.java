package Sys;

import User.Master;
import User.Student;

public class Sys {

    public void quit() {
        // TODO：让用户下线
        System.out.print("----- Good Bye! -----");
    }

    public void register(String[] params) {
        String id = params[0];
        String type = id.substring(0, 2); // 取前两个字符判断类型
        switch (type) { // 学工号
            case "SY", "ZY":
                // 硕士生
                Master p = new Master(params[0], params[1], params[2], params[3],params[4]);
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
                }
                else
                {
                    System.out.println(Feedback.ILLEGAL_USER_ID);
                    return;
                }
        }
    }
}

