package Course;

import java.util.HashMap;

public class CourseList {
    private static CourseList instance;
    private final HashMap<String, Course> CourseList;

    private CourseList() {
        CourseList = new HashMap<>();
    }

    public static CourseList getInstance() {
        if (instance == null) {
            instance = new CourseList();
        }
        return instance;
    }

    public void addCourse(Course course) {
        CourseList.put(course.getCourseName(), course);
    }

    public boolean hasCourse(String courseName) {
        return CourseList.containsKey(courseName);
    }

    public HashMap<String, Course> getCourseList() {
        return CourseList;
    }

    public void removeCourse(Course course) {
        CourseList.remove(course);
    }

    public int getCountCourse() {
        return CourseList.size();
    }

}
