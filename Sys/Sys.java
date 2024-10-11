package Sys;

import Course.*;
import User.*;

import java.util.ArrayList;
import java.util.List;

public class Sys {
    private final UserData userData;  // 用户数据
    private User currentUser;
    private final OnlineUser onlineUser;  //在线用户列表
    private final CourseList courseList;

    public Sys() {
        this.currentUser = null;
        this.userData = UserData.getInstance();
        this.onlineUser = OnlineUser.getInstance();
        this.courseList = CourseList.getInstance();
    }

    public void quit() {
        List<User> onlineUsers = new ArrayList<>(onlineUser.getOnlineUserList()); // 创建副本

        for (User user : onlineUsers) {
            userData.logoutUser(user.getId());
        }
        System.out.println("----- Good Bye! -----");
    }


    public void register(String[] params) {
        if (params[0].length() < 5) {
            System.out.println(Feedback.ILLEGAL_USER_ID);
            return;
        }
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
            this.currentUser = user;
            this.onlineUser.addUser(user);
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
            return adminNumber >= 1 && adminNumber <= 999;
        }

        return false;
    }

    public void logout(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);  //当前无用户在线
            return;
        }
        if (params.length == 0) {
            userData.logoutUser(currentUser.getId());
            currentUser = null;
        } else if (params.length == 1) {
            String id = params[0];
            if (!checkPermission("Administrator")) {  //只能管理员操作
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

    public boolean checkPermission(String Identity) {
        return currentUser.getIdentityType().equals(Identity);
    }

    public void printInfo(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);  //当前无用户在线
            return;
        }
        if (params.length == 0) {  // 打印当前用户的个人信息
            User user = userData.findUser(currentUser.getId());
            System.out.println(user.toString());
        } else if (params.length == 1) {  //打印他人信息
            String id = params[0];
            User user = userData.findUser(id);
            if (!checkPermission("Administrator")) {  //只能管理员操作
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
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }
        currentUser.createCourse(params[0], params[1], Double.parseDouble(params[2]), Integer.parseInt(params[3]));
    }

    public void listCourse(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }
        if (params.length == 0) {
            currentUser.listCourse();
        } else if (params.length == 1) {
            String teacherId = params[0];
            currentUser.listTeacherCourse(teacherId);
        }
    }


    public void selectCourse(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }
        String CourseID = params[0];
        if (!isValidCourseId(CourseID)) {
            System.out.println(Feedback.ILLEGAL_COURSE_ID);
            return;
        }

        if (!courseList.hasCourse(CourseID)) {
            System.out.println(Feedback.COURSE_NOT_EXIST);
            return;
        }
        Course c = courseList.findCourse(CourseID);

        // 课程是否已经满 TODO
//        if (c.isFull()) {
//            return;
//        }

        currentUser.selectCourse(c);
    }

    public boolean isValidCourseId(String courseId) {
        // 检查格式是否为 C-数字
        if (!courseId.matches("C-\\d+")) {
            return false;
        }

        // 获取数字部分并转换为整数
        String numberPart = courseId.substring(2);
        int number = Integer.parseInt(numberPart);

        // 检查数字是否大于0
        return number > 0;
    }

    public void cancelCourse(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }
        String CourseID = params[0];
        if (!isValidCourseId(CourseID)) {
            System.out.println(Feedback.ILLEGAL_COURSE_ID);
            return;
        }
        Course c = courseList.findCourse(CourseID);
        // 对于管理员
        if (checkPermission("Administrator")) {
            User t = userData.findUser(c.getTeacherID());
            if (!t.removeCourse(c)) {
                return;
            }
            for (Student student : userData.getAllStudents()) {
                student.removeCourse(c);
            }
            if (!courseList.removeCourse(c)) {
                System.out.println(Feedback.COURSE_NOT_EXIST);
                return;
            }
            System.out.println("Cancel course success (courseId: " + c.getCourseID() + ")");
        } else {  // 对于老师或学生
            if (!currentUser.removeCourse(c)) {
                System.out.println(Feedback.COURSE_NOT_EXIST);
                return;
            }
            System.out.println("Cancel course success (courseId: " + c.getCourseID() + ")");
        }
    }

    public boolean notLogin() {
        return this.currentUser == null;
    }
}

