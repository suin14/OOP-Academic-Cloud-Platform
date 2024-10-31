package Sys;

import Course.*;
import User.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        if (c.isFull()) {
            System.out.println(Feedback.COURSE_FULL);
            return;
        }

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

    public void switchUser(String[] params) {
        String id = params[0];

        if (!t.checkId(id)) {
            System.out.println(Feedback.ILLEGAL_USER_ID);
            return;
        }

        if (!t.userData.hasUser(id)) {
            System.out.println(Feedback.USER_NOT_EXISTS);
            return;
        }

        User user = t.userData.findUser(id);
        if (!t.onlineUser.checkLogin(user)) {
            System.out.println(user.getId() +" is not online");
            return;
        }

        currentUser = user;
        System.out.println("Switch to " + user.getId());
    }


    public void inputCourseBatch(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }

        if (!checkPermission("Teacher")) {
            System.out.println(Feedback.PERMISSION_DENIED);
            return;
        }

        String filePath = "./data/" + params[0];
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println(Feedback.FILE_NOT_EXIST);
            return;
        }

        if (file.isDirectory()) {
            System.out.println(Feedback.FILE_IS_DIRECTORY);
            return;
        }

        if (currentUser.getCourses().size() >= 10) {
            System.out.println(Feedback.COURSE_COUNT_LIMIT);
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Course> inputCourses = (List<Course>) ois.readObject();

            for (Course course : inputCourses) {
                currentUser.createCourse(course.getCourseName(), course.getTime(),
                        course.getCredit(), course.getPeriod());
            }
            System.out.println(Feedback.INPUT_SUCCESS);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void outputCourseBatch(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }

        String filePath = "./data/" + params[0];

        new File(filePath).getParentFile().mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(currentUser.outputCourseInfo()));
            System.out.println(Feedback.OUTPUT_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listStudent(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }
        if (checkPermission("Student")) {
            System.out.println(Feedback.PERMISSION_DENIED);
            return;
        }

        String courseId = params[0];

        if (!t.isValidCourseId(courseId)) {
            System.out.println(Feedback.ILLEGAL_COURSE_ID);
            return;
        }

        if (!currentUser.hasCourse(courseId)) {
            System.out.println(Feedback.COURSE_NOT_EXIST);
            return;
        }

        List<Student> enrolledStudents = new ArrayList<>();

        for (Student student : t.userData.getAllStudents()) {
            if (student.hasCourse(courseId)) {
                enrolledStudents.add(student);
            }
        }

        enrolledStudents.sort((s1, s2) -> {
            String id1 = s1.getId();
            String id2 = s2.getId();

            int degree1 = getDegreeRank(id1);
            int degree2 = getDegreeRank(id2);

            // 按学位排序，如果学位相同则按学号升序
            if (degree1 != degree2) {
                return Integer.compare(degree1, degree2);
            } else {
                return id1.compareTo(id2);
            }
        });

        if (enrolledStudents.isEmpty()) {
            System.out.println(Feedback.COURSE_NOT_STUDENT);
        } else {
            for (Student student : enrolledStudents) {
                System.out.println(student.getId() + ": " + student.getName());
            }
            System.out.println(Feedback.LIST_STUDENT_SUCCESS);
        }
    }

    private int getDegreeRank(String studentId) {
        if (studentId.startsWith("BY")) { // 博士
            return 1;
        } else if (studentId.startsWith("SY")) { // 学术硕士
            return 2;
        } else if (studentId.startsWith("ZY")) { // 专业硕士
            return 3;
        } else { // 本科生
            return 4;
        }
    }

    public void removeStudent(String[] params) {
        if (notLogin()) {
            System.out.println(Feedback.NOT_ONLINE);
            return;
        }

        boolean designated = false;
        boolean flag = false;
        String courseId = "";
        String studentId = params[0];
        if (params.length == 2) {
            courseId = params[1];
            designated = true;
        }

        if (!t.checkId(studentId)) {
            System.out.println(Feedback.ILLEGAL_USER_ID);
            return;
        }
        if (!t.userData.hasUser(studentId)) {
            System.out.println(Feedback.USER_NOT_EXISTS);
            return;
        }
        if (!t.userData.findUser(studentId).getIdentityType().equals("Student")) {
            System.out.println(Feedback.NOT_STUDENT);
            return;
        }

        Student student = t.userData.findStudent(studentId);
        if (checkPermission("Teacher")) {
            if (designated) {
                if (!t.isValidCourseId(courseId)) {
                    System.out.println(Feedback.ILLEGAL_COURSE_ID);
                    return;
                }
                if (!currentUser.hasCourse(courseId)) {
                    System.out.println(Feedback.COURSE_NOT_EXIST);
                    return;
                }

                if(!student.removeCourse(t.courseList.findCourse(courseId))) {
                    System.out.println(Feedback.STUDENT_NOT_IN_COURSE);
                    return;
                }
            } else {
                for (Course course : currentUser.getCourses()) {
                    if (student.removeCourse(course)) {
                        flag = true;
                    }
                }
                if (!flag) {
                    System.out.println(Feedback.STUDENT_NOT_IN_COURSE);
                    return;
                }
            }
            System.out.println(Feedback.REMOVE_STUDENT_SUCCESS);
        } else if (checkPermission("Administrator")) {
            if (designated) {
                if (!t.isValidCourseId(courseId)) {
                    System.out.println(Feedback.ILLEGAL_COURSE_ID);
                    return;
                }
                if (!t.courseList.hasCourse(courseId)) {
                    System.out.println(Feedback.COURSE_NOT_EXIST);
                    return;
                }

                if(!student.removeCourse(t.courseList.findCourse(courseId))) {
                    System.out.println(Feedback.STUDENT_NOT_IN_COURSE);
                    return;
                }
            } else {
                if (student.getCourses().size() == 0) {
                    System.out.println(Feedback.STUDENT_NOT_IN_COURSE);
                    return;
                }
                for (Course course : student.getCourses()) {
                    student.removeCourse(course);
                }
            }
            System.out.println(Feedback.REMOVE_STUDENT_SUCCESS);
        } else {
            System.out.println(Feedback.PERMISSION_DENIED);
            return;
        }
        System.out.println(Feedback.REMOVE_STUDENT_SUCCESS);
    }

    public void listCourseSchedule(String[] params) {
    }
}

