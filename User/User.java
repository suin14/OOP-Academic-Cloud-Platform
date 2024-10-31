package User;

import Course.*;
import Sys.Feedback;
import Sys.Tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class User {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected String password;
    protected String identityType;
    private boolean login = false;

    protected ArrayList<Course> courses;
    Tools t = new Tools();

    public User(String id, String name, String password, String checkPwd, String identityType) {
        this.courses = new ArrayList<>();
        if (t.userData.hasUser(id)) {
            System.out.println(Feedback.USER_EXISTS);
            return;
        }
        if (!setId(id)) return;
        if (!setName(name)) return;
        if (!setPassword(password)) return;
        if (!password.equals(checkPwd)) {
            System.out.println(Feedback.PASSWORD_NOT_MATCH); // 密码与确认密码不一致
            return;
        }
        if (!setIdentityType(identityType)) return;

        t.userData.createUser(id, this);
    }


    public String getId() {
        return id;
    }

    public boolean setId(String id) {
        this.id = id;
        return true;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        // 首字符不能为下划线，其余部分只能是字母和下划线
        if (!name.matches("^[A-Za-z][A-Za-z_]{3,15}$")) {
            System.out.println(Feedback.ILLEGAL_USER_NAME);
            return false;
        }

        this.name = name;
        return true;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        if (password.length() < 6 || password.length() > 16) {
            System.out.println(Feedback.ILLEGAL_PASSWORD);
            return false;
        }

        boolean hasLetter = password.matches(".*[A-Za-z].*"); // 至少一个字母
        boolean hasDigit = password.matches(".*[0-9].*"); // 至少一个数字
        boolean hasSpecial = password.matches(".*[@_%$].*"); // 至少一个特殊字符

        // 只允许字母、数字和特殊字符
        boolean validCharacters = password.matches("[A-Za-z0-9@_%$]+");

        if (hasLetter && hasDigit && hasSpecial && validCharacters) {
            this.password = password;
            return true;
        } else {
            System.out.println(Feedback.ILLEGAL_PASSWORD);
            return false;
        }
    }

    public String getIdentityType() {
        return identityType;
    }

    public boolean setIdentityType(String identityType) {
        this.identityType = identityType;
        return true;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin() {
        this.login = true;
    }

    public void setLogout() {
        this.login = false;
    }

    @Override
    public String toString() {
        return "User id: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Type: " + getIdentityType() + "\n" +
                "Print information success";
    }

    public void createCourse(String courseName, String courseTime, String credit, String period) {
        System.out.println(Feedback.PERMISSION_DENIED);
    }

    public void listCourse() {
        if (t.courseList.isEmpty()) {
            System.out.println(Feedback.COURSE_NOT_EXIST);
            return;
        }
        t.courseList.printCourseList();
    }

    public void listTeacherCourse(String id){
        System.out.println(Feedback.PERMISSION_DENIED);
    }

    public void selectCourse(Course course) {
        System.out.println(Feedback.PERMISSION_DENIED);
    }

    public boolean removeCourse(Course course) {
        System.out.println(Feedback.PERMISSION_DENIED);
        return false;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Course> outputCourseInfo() {
        System.out.println(Feedback.PERMISSION_DENIED);
        return null;
    }

    public boolean hasCourse(String courseId) {
        System.out.println(Feedback.PERMISSION_DENIED);
        return false;
    }

    public void inputCourse(String courseName, String time, String credit, String period) {
        System.out.println(Feedback.PERMISSION_DENIED);
    }
}
