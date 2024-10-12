package Sys;

import Course.CourseList;
import User.UserData;


public class Tools {
    public final UserData userData;
    public final OnlineUser onlineUser;
    public final CourseList courseList;

    public Tools() {
        this.userData = UserData.getInstance();
        this.onlineUser = OnlineUser.getInstance();
        this.courseList = CourseList.getInstance();
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

    public boolean checkId(String id) {
        // 检查本科生 ID（8位数字）
        if (id.matches("^[0-9]{8}$")) {
            // 本科生 ID 解析
            int year = Integer.parseInt(id.substring(0, 2)); // 年份
            int college = Integer.parseInt(id.substring(2, 4)); // 学院代码
            int classNumber = Integer.parseInt(id.substring(4, 5)); // 班级
            int studentNumber = Integer.parseInt(id.substring(5, 8)); // 学号
            if (year >= 19 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1 && classNumber <= 6 && studentNumber >= 1 && studentNumber <= 999) {
                return true;
            }
        }

        // 检查硕士生 ID（9位数字，前缀SY或ZY）
        if (id.matches("^(SY|ZY)[0-9]{7}$")) {
            int year = Integer.parseInt(id.substring(2, 4)); // 年份
            int college = Integer.parseInt(id.substring(4, 6)); // 学院代码
            int classNumber = Integer.parseInt(id.substring(6, 7)); // 班级
            int studentNumber = Integer.parseInt(id.substring(7, 9)); // 学号
            if (year >= 21 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1 && classNumber <= 6 && studentNumber >= 1 && studentNumber <= 99) {
                return true;
            }
        }

        // 检查博士生 ID（9位数字，前缀BY）
        if (id.matches("^BY[0-9]{7}$")) {
            int year = Integer.parseInt(id.substring(2, 4)); // 年份
            int college = Integer.parseInt(id.substring(4, 6)); // 学院代码
            int classNumber = Integer.parseInt(id.substring(6, 7)); // 班级
            int studentNumber = Integer.parseInt(id.substring(7, 9)); // 学号
            if (year >= 19 && year <= 24 && college >= 1 && college <= 43 && classNumber >= 1 && classNumber <= 6 && studentNumber >= 1 && studentNumber <= 99) {
                return true;
            }
        }

        // 检查教职工 ID（5位数字）
        if (id.matches("^[0-9]{5}$")) {
            int employeeNumber = Integer.parseInt(id);
            if (employeeNumber >= 1 && employeeNumber <= 99999) {
                return true;
            }
        }

        // 检查管理员 ID（5位数字，前缀AD）
        if (id.matches("^AD[0-9]{3}$")) {
            int adminNumber = Integer.parseInt(id.substring(2, 5)); // 管理员编号
            return adminNumber >= 1 && adminNumber <= 999;
        }

        return false;
    }

    public boolean isValidCourseId(String courseId) {
        // 检查格式是否为 C-数字
        if (!courseId.matches("C-\\d+")) {
            return false;
        }

        // 获取数字部分并转换为整数
        String numberPart = courseId.substring(2);
        int number = Integer.parseInt(numberPart);

        // 检查数字是否大于0
        return number > 0;
    }
}
