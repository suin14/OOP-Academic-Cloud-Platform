package Sys;

public class Tools {
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
