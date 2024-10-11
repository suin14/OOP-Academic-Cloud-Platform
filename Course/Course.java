package Course;

import Sys.Feedback;
import User.UserData;

public class Course {
    private String courseName;
    private String time;
    private double credit;
    private int period;

    private final CourseList courseList;

    public Course(String courseName, String time, double credit, int period) {
        setCourseName(courseName);
        setTime(time);
        setCredit(credit);
        setPeriod(period);
        this.courseList = CourseList.getInstance();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public boolean isValidCourseName(String courseName) {
        // 检查课程名称长度
        if (courseName.length() < 1 || courseName.length() > 20) {
            return false;
        }

        // 检查第一位是否为字母
        if (!Character.isLetter(courseName.charAt(0))) {
            return false;
        }

        // 检查是否符合指定格式：英文字母、数字、连接符-、下划线_
        if (!courseName.matches("[A-Za-z][A-Za-z0-9-_]*")) {
            return false;
        }

        return true;
    }

    public boolean isValidCourseTime(String courseTime) {
        // 分离星期和上课时间段
        String[] parts = courseTime.split("_");
        int day = Integer.parseInt(parts[0]);
        String[] times = parts[1].split("-");
        int start = Integer.parseInt(times[0]);
        int end = Integer.parseInt(times[1]);

        // 检查格式是否为 X_Y-Y，X 和 Y 为 1-14 之间的整数，且 X <= Y
        if (day < 1 || day > 7 || start < 1 || start > 14 || end < 1 || end > 14 || start > end) {
            return false;
        }

        return true;
    }

    // 检查上课时间冲突
    public boolean isTimeConflict(String existingCourseTime, String newCourseTime) {
        // 解析现有课程时间
        String[] existingParts = existingCourseTime.split("_");
        int existingDay = Integer.parseInt(existingParts[0]);
        String[] existingTimes = existingParts[1].split("-");
        int existingStart = Integer.parseInt(existingTimes[0]);
        int existingEnd = Integer.parseInt(existingTimes[1]);

        // 解析新课程时间
        String[] newParts = newCourseTime.split("_");
        int newDay = Integer.parseInt(newParts[0]);
        String[] newTimes = newParts[1].split("-");
        int newStart = Integer.parseInt(newTimes[0]);
        int newEnd = Integer.parseInt(newTimes[1]);

        if (existingDay == newDay) {
            return (newStart <= existingEnd && newEnd >= existingStart);
        }

        return false;
    }

    public boolean isValidCourseCredit(double credit) {
        if (credit <= 0 || credit > 12) {
            return false;
        }
        return true;
    }

    public boolean isValidCoursePeriod(int period) {
        if (period <= 0 || period > 1280) {
            return false;
        }
        return true;
    }
}
