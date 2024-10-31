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
        return this.studentsCount == 30;
    }
}
