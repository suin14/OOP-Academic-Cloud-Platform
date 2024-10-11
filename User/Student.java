package User;

import Course.Course;
import Sys.Feedback;
import Sys.Sys;

import java.util.ArrayList;

public class Student extends User{


    public Student(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setIdentityType(String identityType) {
        if (identityType.equals("Student")) {
            this.identityType = identityType;
            return true;
        } else {
            System.out.println(Feedback.ILLEGAL_IDENTITY);
            return false;
        }
    }

    @Override
    public void selectCourse(Course course) {
        if (!hasTimeConflict(course.getTime())) {
            courses.add(course);
            System.out.println("Select course success (courseId: " + course.getCourseID() + ")");
        }
    }

    // 检查是否与已选课程存在时间冲突
    public boolean hasTimeConflict(String newCourseTime) {
        for (Course existingCourse : courses) {
            if (isTimeConflict(existingCourse.getTime(), newCourseTime)) {
                System.out.println(Feedback.COURSE_TIME_CONFLICTS);
                return true;
            }
        }
        return false;
    }

    private boolean isTimeConflict(String existingCourseTime, String newCourseTime) {
        // 分离星期和上课时间段
        String[] existingParts = existingCourseTime.split("_");
        String[] newParts = newCourseTime.split("_");

        int existingDay = Integer.parseInt(existingParts[0]);
        String[] existingTimes = existingParts[1].split("-");
        int existingStart = Integer.parseInt(existingTimes[0]);
        int existingEnd = Integer.parseInt(existingTimes[1]);

        int newDay = Integer.parseInt(newParts[0]);
        String[] newTimes = newParts[1].split("-");
        int newStart = Integer.parseInt(newTimes[0]);
        int newEnd = Integer.parseInt(newTimes[1]);

        // 如果在同一天，则检查时间
        if (existingDay == newDay) {
            return (newStart <= existingEnd && newEnd >= existingStart);
        }
        return false;
    }

    @Override
    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }
}
