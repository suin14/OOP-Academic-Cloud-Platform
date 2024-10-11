package User;

import Course.Course;
import Sys.Feedback;

import java.util.Comparator;


public class Teacher extends User {


    public Teacher(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setId(String id) {
        if (isValidId(id)) {
            this.id = id;
            return true;
        }
        return false;
    }

    private boolean isValidId(String id) {
        if (id.matches("\\d{5}") && Integer.parseInt(id) >= 1 && Integer.parseInt(id) <= 99999) {
            return true;
        }
        System.out.println(Feedback.ILLEGAL_USER_ID);
        return false;
    }

    private int getCountCourses() {
        return courses.size();
    }

    @Override
    public boolean setIdentityType(String identityType) {
        if (identityType.equals("Teacher")) {
            this.identityType = identityType;
            return true;
        } else {
            System.out.println(Feedback.ILLEGAL_IDENTITY);
            return false;
        }
    }

    @Override
    public void createCourse(String courseName, String courseTime, double credit, int period){
        if (getCountCourses() >= 10) {
            System.out.println(Feedback.COURSE_COUNT_REACHES_LIMIT);  // 拥有课程已达最大数
            return;
        }

        String id = generateCourseId(courseList.getCount());

        Course c = new Course(getId(), getName(), id, courseName, courseTime, credit, period, 0);

        // 检查课程名
        if (!c.isValidCourseName(courseName)) {
            System.out.println(Feedback.ILLEGAL_COURSE_NAME);
            return;
        }

        for (Course existingCourse : courses) {
            if (existingCourse.getCourseName().equals(courseName)) {
                System.out.println(Feedback.COURSE_NAME_EXISTS); // 课程名称已存在
                return;
            }
        }

        // 课程时间不合法
        if (!c.isValidCourseTime(courseTime)) {
            System.out.println(Feedback.ILLEGAL_COURSE_TIME);
            return;
        }

        // 课程时间冲突
        for (Course existingCourse : courses) {
            if (c.isTimeConflict(existingCourse.getTime(), courseTime)) {
                System.out.println(Feedback.COURSE_TIME_CONFLICTS);
                return;
            }
        }

        // 学分不合法
        if (!c.isValidCourseCredit(credit)) {
            System.out.println(Feedback.ILLEGAL_COURSE_CREDIT);
            return;
        }

        // 学时不合法
        if (!c.isValidCoursePeriod(period)) {
            System.out.println(Feedback.ILLEGAL_COURSE_PERIOD);
            return;
        }

        courses.add(c);
        courseList.addCourse(c);
        courseList.addCount();
        System.out.println("Create course success (courseId: " + c.getCourseID() + ")");
    }

    public static String generateCourseId(int num) {
        num += 1;
        return "C-" + num;
    }

    @Override
    public void listCourse() {
        if (courses.size() == 0) {
            System.out.println(Feedback.COURSE_NOT_EXIST);
            return;
        }

        // 按老师姓名和课程编号排序
        this.courses.sort(Comparator.comparing(Course::getTeacherName)
                .thenComparing(Course::getCourseID));

        // 打印输出
        for (Course course : this.courses) {
            System.out.println(course.getCourseID() + " "
                    + course.getCourseName() + " "
                    + course.getTime() + " "
                    + course.getCredit() + " "
                    + course.getPeriod());
        }
        System.out.println(Feedback.LIST_COURSE_SUCCESS);
    }

    @Override
    public boolean removeCourse(Course course) {
        if (courses.remove(course)) {
            courseList.removeCourse(course);
            for (Student student : userData.getAllStudents()) {
                student.removeCourse(course);
            }
            return true;
        } else {
            return false;
        }
    }

}
