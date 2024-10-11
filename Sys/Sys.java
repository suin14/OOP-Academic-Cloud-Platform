package Sys;

import User.*;

public class Sys {
    private final UserData userData;  // 用户数据
    private User currentUser;
    private OnlineUser onlineUser;  //在线用户列表

    public Sys() {
        this.userData = UserData.getInstance();
        this.currentUser = null;
    }

    public void quit() {
        // TODO：让用户下线
        System.out.print("----- Good Bye! -----");
    }

    public void register(String[] params) {
        String id = params[0];
        String type = id.substring(0, 2); // 取前两个字符判断类型

        switch (type) {
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

    public void login(String[] params) {
        String id = params[0];
        String pwd = params[1];

        if (!checkId(id)) {
            System.out.println(Feedback.ILLEGAL_USER_ID);
            return;
        }

        if (!userData.hasUser(id)) {
            System.out.println(Feedback.USER_NOT_EXISTS);
            return;
        }

        User user = userData.findUser(id);

        if (!user.getPassword().equals(pwd)) {
            System.out.println(Feedback.WRONG_PASSWORD);
            return;
        }

        if (userData.loginUser(id)) {
            currentUser = user;
            onlineUser.addUser(user);
            System.out.println("Welcome to ACP, " + currentUser.getId());
        }
    }

    public boolean checkId(String id) {
        // 检查本科生 ID（8位数字）
        if (id.matches("^[0-9]{8}$")) {
            // 本科生 ID 解析
            int year = Integer.parseInt(id.substring(0, 2)); // 年份
            int college = Integer.parseInt(id.substring(2, 4)); // 学院代码
            int classNumber = Integer.parseInt(id.substring(4, 5)); // 班级
            int studentNumber = Integer.parseInt(id.substring(5, 8)); // 学号
            if (year >= 19 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1 && classNumber <= 6 && studentNumber >= 1 && studentNumber <= 999) {
                return true;
            }
        }

        // 检查硕士生 ID（9位数字，前缀SY或ZY）
        if (id.matches("^(SY|ZY)[0-9]{7}$")) {
            int year = Integer.parseInt(id.substring(2, 4)); // 年份
            int college = Integer.parseInt(id.substring(4, 6)); // 学院代码
            int classNumber = Integer.parseInt(id.substring(6, 7)); // 班级
            int studentNumber = Integer.parseInt(id.substring(7, 9)); // 学号
            if (year >= 21 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1 && classNumber <= 6 && studentNumber >= 1 && studentNumber <= 99) {
                return true;
            }
        }

        // 检查博士生 ID（9位数字，前缀BY）
        if (id.matches("^BY[0-9]{7}$")) {
            int year = Integer.parseInt(id.substring(2, 4)); // 年份
            int college = Integer.parseInt(id.substring(4, 6)); // 学院代码
            int classNumber = Integer.parseInt(id.substring(6, 7)); // 班级
            int studentNumber = Integer.parseInt(id.substring(7, 9)); // 学号
            if (year >= 19 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1 && classNumber <= 6 && studentNumber >= 1 && studentNumber <= 99) {
                return true;
            }
        }

        // 检查教职工 ID（5位数字）
        if (id.matches("^[0-9]{5}$")) {
            int employeeNumber = Integer.parseInt(id);
            if (employeeNumber >= 1 && employeeNumber <= 99999) {
                return true;
            }
        }

        // 检查管理员 ID（5位数字，前缀AD）
        if (id.matches("^AD[0-9]{3}$")) {
            int adminNumber = Integer.parseInt(id.substring(2, 5)); // 管理员编号
            if (adminNumber >= 1 && adminNumber <= 999) {
                return true;
            }
        }

        return false;
    }

    public void logout(String[] params) {
        if (currentUser == null) {
            System.out.println(Feedback.NOT_ONLINE);  //当前无用户在线
            return;
        }
        if (params.length == 0) {
            String id = currentUser.getId();
            userData.logoutUser(currentUser.getId());
            currentUser = null;
        } else if (params.length == 1) {
            String id = params[0];
            if (!isAdministrator()) {  //只能管理员操作
                System.out.println(Feedback.PERMISSION_DENIED);
                return;
            }
            if (!checkId(id)) {
                System.out.println(Feedback.ILLEGAL_USER_ID);
                return;
            }
            if (!userData.hasUser(id)) {
                System.out.println(Feedback.USER_NOT_EXISTS);
                return;
            }
            if (!userData.findUser(id).isLogin()) {
                System.out.println(id + " is not online");  // 学工号对应的用户不在线
                return;
            }
            userData.logoutUser(id);
            onlineUser.removeUser(userData.findUser(id));
            if(currentUser.getId().equals(id)) {
                currentUser = null;
            }
        }
    }

    public boolean isAdministrator() {
        return currentUser.getIdentityType().equals("Administrator");
    }

    public void printInfo(String[] params) {
        if (currentUser == null) {
            System.out.println(Feedback.NOT_ONLINE);  //当前无用户在线
            return;
        }
        if (params.length == 0) {  // 打印当前用户的个人信息
            User user = userData.findUser(currentUser.getId());
            System.out.println(user.toString());
        } else if (params.length == 1) {  //打印他人信息
            String id = params[0];
            User user = userData.findUser(id);
            if (!isAdministrator()) {  //只能管理员操作
                System.out.println(Feedback.PERMISSION_DENIED);
                return;
            }
            if (!checkId(id)) {
                System.out.println(Feedback.ILLEGAL_USER_ID);
                return;
            }
            if (!userData.hasUser(id)) {
                System.out.println(Feedback.USER_NOT_EXISTS);
                return;
            }
            System.out.println(user.toString());
        }
    }

    public void createCourse(String[] params) {
    }

    public void listCourse(String[] params) {
    }

    public void selectCourse(String[] params) {
    }

    public void cancelCourse(String[] params) {
    }
}

