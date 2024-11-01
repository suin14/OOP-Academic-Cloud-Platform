package User;

import Course.Course;
import Sys.Feedback;

import java.util.List;


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

    @Override
    public boolean hasCourse(String courseId) {
        Course course = t.courseList.findCourse(courseId);
        return courses.contains(course);
    }

    public void printSchedule() {
        List<Course> courses = getCourses();

        // 按照星期日期和上课时间排序
        courses.sort((c1, c2) -> {
            String[] time1 = c1.getTime().split("[_\\-]");
            String[] time2 = c2.getTime().split("[_\\-]");

            int day1 = Integer.parseInt(time1[0]);
            int day2 = Integer.parseInt(time2[0]);
            int start1 = Integer.parseInt(time1[1]);
            int start2 = Integer.parseInt(time2[1]);

            // 按星期排序，日期小的在前
            if (day1 != day2) return Integer.compare(day1, day2);
            // 日期相同时，按上课时间排序，时间更早的在前
            return Integer.compare(start1, start2);
        });

        for (Course course : courses) {
            System.out.println(course.getTime() + " "
                    + course.getCourseName() + " "
                    + course.getCredit() + " "
                    + course.getPeriod()+ " "
                    + course.getTeacherName());
        }
        System.out.println(Feedback.LIST_SCHEDULE_SUCCESS);
    }
}
