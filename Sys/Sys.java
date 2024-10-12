package Sys;

import Course.*;
import User.*;

import java.util.ArrayList;
import java.util.List;

public class Sys {
    private User currentUser;
    Tools t = new Tools();

    public Sys() {
        this.currentUser = null;
    }

    public void quit() {
        List<User> onlineUsers = new ArrayList<>(t.onlineUser.getOnlineUserList()); // 创建副本

        for (User user : onlineUsers) {
            t.userData.logoutUser(user.getId());
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

        if (!t.checkId(id)) {
            System.out.println(Feedback.ILLEGAL_USER_ID);
            return;
        }

        if (!t.userData.hasUser(id)) {
            System.out.println(Feedback.USER_NOT_EXISTS);
            return;
        }

        User user = t.userData.findUser(id);

        if (!user.getPassword().equals(pwd)) {
            System.out.println(Feedback.WRONG_PASSWORD);
            return;
        }

        if (t.userData.loginUser(id)) {
            this.currentUser = user;
            t.onlineUser.addUser(user);
            System.out.println("Welcome to ACP, " + currentUser.getId());
        }
    }

    public void logout(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);  //当前无用户在线
            return;
        }
        if (params.length == 0) {
            t.userData.logoutUser(currentUser.getId());
            currentUser = null;
        } else if (params.length == 1) {
            String id = params[0];
            if (!checkPermission("Administrator")) {  //只能管理员操作
                System.out.println(Feedback.PERMISSION_DENIED);
                return;
            }
            if (!t.checkId(id)) {
                System.out.println(Feedback.ILLEGAL_USER_ID);
                return;
            }
            if (!t.userData.hasUser(id)) {
                System.out.println(Feedback.USER_NOT_EXISTS);
                return;
            }
            if (!t.userData.findUser(id).isLogin()) {
                System.out.println(id + " is not online");  // 学工号对应的用户不在线
                return;
            }
            t.userData.logoutUser(id);
            t.onlineUser.removeUser(t.userData.findUser(id));
            if(currentUser.getId().equals(id)) {
                currentUser = null;
            }
        }
    }

    public void printInfo(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);  //当前无用户在线
            return;
        }
        if (params.length == 0) {  // 打印当前用户的个人信息
            User user = t.userData.findUser(currentUser.getId());
            System.out.println(user.toString());
        } else if (params.length == 1) {  //打印他人信息
            String id = params[0];
            User user = t.userData.findUser(id);
            if (!checkPermission("Administrator")) {  //只能管理员操作
                System.out.println(Feedback.PERMISSION_DENIED);
                return;
            }
            if (!t.checkId(id)) {
                System.out.println(Feedback.ILLEGAL_USER_ID);
                return;
            }
            if (!t.userData.hasUser(id)) {
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
        currentUser.createCourse(params[0], params[1], params[2], params[3]);
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
        if (!t.isValidCourseId(CourseID)) {
            System.out.println(Feedback.ILLEGAL_COURSE_ID);
            return;
        }

        if (!t.courseList.hasCourse(CourseID)) {
            System.out.println(Feedback.COURSE_NOT_EXIST);
            return;
        }
        Course c = t.courseList.findCourse(CourseID);

        // 课程是否已经满 TODO
//        if (c.isFull()) {
//            return;
//        }

        currentUser.selectCourse(c);
    }

    public void cancelCourse(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }
        String CourseID = params[0];
        if (!t.isValidCourseId(CourseID)) {
            System.out.println(Feedback.ILLEGAL_COURSE_ID);
            return;
        }
        Course c = t.courseList.findCourse(CourseID);
        // 对于管理员
        if (checkPermission("Administrator")) {
            User teacher = t.userData.findUser(c.getTeacherID());
            if (!teacher.removeCourse(c)) {
                System.out.println(Feedback.COURSE_NOT_EXIST);
                return;
            }
            for (Student student : t.userData.getAllStudents()) {
                student.removeCourse(c);
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

    public boolean checkPermission(String Identity) {
        return currentUser.getIdentityType().equals(Identity);
    }

    public boolean notLogin() {
        return this.currentUser == null;
    }
}

