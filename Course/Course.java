package Course;


import Sys.Feedback;

import java.text.DecimalFormat;

public class Course {
    private String courseID;
    private String courseName;
    private String time;
    private String credit;
    private String period;
    private String teacherID;

    private String teacherName;

    private int studentsCount; // 学生人数


    public Course(String teacherID, String teacherName, String id, String courseName, String time, String credit, String period, int students) {
        setTeacherID(teacherID);
        setTeacherName(teacherName);
        setCourseID(id);
        setCourseName(courseName);
        setTime(time);
        setCredit(credit);
        setPeriod(period);
        setStudentsCount(students);
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public String getCredit() {
        return credit;
    }

    public void setCredit(String creditStr) {
        if (creditStr != null && creditStr.matches("-?\\d+(\\.\\d+)?")) {
            double creditValue = Double.parseDouble(creditStr);
            DecimalFormat df = new DecimalFormat("#.0");
            this.credit = df.format(creditValue);
        } else {
            return;
        }
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void addStudent() {
        this.studentsCount += 1;
    }

    public boolean isFull() {
        // 每一门课的上限为30人
        return this.studentsCount == 30;
    }

    public boolean isValidCourseName(String courseName) {
        if (courseName.length() < 1 || courseName.length() > 20) {
            return false;
        }

        // 检查第一位是否为字母
        if (!Character.isLetter(courseName.charAt(0))) {
            return false;
        }

        // 检查是否符合指定格式 英文字母、数字、连接符-、下划线_
        return courseName.matches("[A-Za-z][A-Za-z0-9-_]*");
    }

    public boolean isValidCourseTime(String courseTime) {
        if (!courseTime.matches("\\d+_\\d+-\\d+")) {
            return false;
        }

        try {
            // 分离星期和上课时间段
            String[] parts = courseTime.split("_");
            int day = Integer.parseInt(parts[0]); // X 1-7
            String[] times = parts[1].split("-"); // Y-Y 1-14
            int start = Integer.parseInt(times[0]);
            int end = Integer.parseInt(times[1]);

            return day >= 1 && day <= 7 && start >= 1 && start <= 14 && end >= 1 && end <= 14 && start <= end;
        } catch (NumberFormatException e) {
            return false;
        }
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

    public boolean isValidCourseCredit(String creditStr) {
        // 是正整数或小数
        if (!creditStr.matches("^(0*[1-9]\\d*(\\.\\d)?|1[0-1](\\.0)?)$")) {
            return false;
        }

        double credit = Double.parseDouble(creditStr);

        return credit > 0 && credit <= 12;
    }

    public boolean isValidCoursePeriod(String periodStr) {
        try {
            int period = Integer.parseInt(periodStr);
            return period > 0 && period <= 1280;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
