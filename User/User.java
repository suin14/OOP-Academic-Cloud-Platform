package User;

import Course.*;
import Sys.Feedback;

public class User {
    protected String id;
    protected String name;
    protected String password;
    protected String identityType;
    protected boolean login = false;

    protected UserData userData;
    protected CourseList courseList;

    public User(String id, String name, String password, String checkPwd, String identityType) {
        this.userData = UserData.getInstance();
        this.courseList = CourseList.getInstance();

        if (!setId(id)) return;
        if (userData.hasUser(id)) {
            System.out.println(Feedback.USER_EXISTS);
            return;
        }
        if (!setName(name)) return;
        if (!setPassword(password)) return;
        if (!password.equals(checkPwd)) {
            System.out.println(Feedback.PASSWORD_NOT_MATCH); // 密码与确认密码不一致
            return;
        }
        if (!setIdentityType(identityType)) return;

        userData.createUser(id, this);
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
        // 检查长度是否在 6 到 16 之间
        if (password.length() < 6 || password.length() > 16) {
            System.out.println(Feedback.ILLEGAL_PASSWORD);
            return false;
        }

        // 正则表达式检查
        boolean hasLetter = password.matches(".*[A-Za-z].*"); // 至少一个字母
        boolean hasDigit = password.matches(".*[0-9].*"); // 至少一个数字
        boolean hasSpecial = password.matches(".*[@_%$].*"); // 至少一个特殊字符

        // 检查三类字符是否至少各有一个
        if (hasLetter && hasDigit && hasSpecial) {
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

    public void createCourse(String courseName, String courseTime, double credit, int period) {
        System.out.println(Feedback.PERMISSION_DENIED);
    }

    public void listCourse() {
        if (courseList.getCount() == 0) {
            System.out.println(Feedback.COURSE_NOT_EXIST);
            return;
        }
        courseList.printCourseList();
    }

    public void listTeacherCourse(String id){
        // 课程不存在
        if (courseList.getCount() == 0) {
            System.out.println(Feedback.COURSE_NOT_EXIST);
            return;
        }
        System.out.println(Feedback.PERMISSION_DENIED);
    }

}
