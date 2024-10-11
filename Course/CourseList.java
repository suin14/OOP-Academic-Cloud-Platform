package Course;

import Sys.Feedback;

import java.util.*;

public class CourseList {
    private static CourseList instance;
    private final HashMap<String, Course> courseList;
    
    private int count;

    private CourseList() {
        courseList = new HashMap<>();
        count = 0;
    }

    public static CourseList getInstance() {
        if (instance == null) {
            instance = new CourseList();
        }
        return instance;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        this.count += 1;
    }

    public void addCourse(Course course) {
        courseList.put(course.getCourseID(), course);
    }

    public boolean hasCourse(String courseID) {
        return courseList.containsKey(courseID);
    }

    public void printCourseList() {
        List<Course> courses = new ArrayList<>(courseList.values());

        // 按老师姓名和课程编号排序
        courses.sort(Comparator.comparing(Course::getTeacherName)
                .thenComparing(Course::getCourseID));

        // 打印输出
        for (Course course : courses) {
            System.out.println(course.getTeacherName() + " "
                    + course.getCourseID() + " "
                    + course.getCourseName() + " "
                    + course.getTime() + " "
                    + course.getCredit() + " "
                    + course.getPeriod());
        }

        System.out.println(Feedback.LIST_COURSE_SUCCESS);
    }

    public void removeCourse(Course course) {
        courseList.remove(course);
    }

    public Collection<Course> values() {
        return courseList.values();
    }

    public Course findCourse(String CourseID) {
        return courseList.get(CourseID);
    }

}
