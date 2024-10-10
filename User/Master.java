package User;

import Sys.Feedback;

public class Master extends Student {
    public Master(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password,checkPwd, identityType);
    }

    @Override
    public void setId(String id) {
        if (id.length() == 9) {
            if (Integer.parseInt(id.substring(2, 4)) >= 21 && Integer.parseInt(id.substring(2, 4)) <= 24) {
                if (Integer.parseInt(id.substring(4, 6)) >= 1 && Integer.parseInt(id.substring(4, 6)) <= 43) {
                    if (Integer.parseInt(id.substring(6, 7)) >= 1 && Integer.parseInt(id.substring(6, 7)) <= 6) {
                        if (Integer.parseInt(id.substring(7, 9)) >= 1 && Integer.parseInt(id.substring(7, 9)) <= 99) {
                            this.id = id;
                        }
                    }
                }
            }
        } else {
            System.out.println(Feedback.ILLEGAL_USER_ID);
        }
    }

    @Override
    public void setName(String name) {
        // 首字符不能为下划线，其余部分只能是字母和下划线
        if (!name.matches("^[A-Za-z][A-Za-z_]{3,15}$")) {
            System.out.println(Feedback.ILLEGAL_USER_NAME);
            return;
        }

        this.name = name;
    }

    @Override
    public void setPassword(String password) {
        // 检查长度是否在 6 到 16 之间
        if (password.length() < 6 || password.length() > 16) {
            System.out.println(Feedback.ILLEGAL_PASSWORD);
            return;
        }

        // 正则表达式检查
        boolean hasLetter = password.matches(".*[A-Za-z].*"); // 至少一个字母
        boolean hasDigit = password.matches(".*[0-9].*"); // 至少一个数字
        boolean hasSpecial = password.matches(".*[@_%$].*"); // 至少一个特殊字符

        // 检查三类字符是否至少各有一个
        if (hasLetter && hasDigit && hasSpecial) {

                this.password = password;


        } else {
            System.out.println(Feedback.ILLEGAL_PASSWORD);
        }
    }


    @Override
    public void setIdentityType(String identityType) {
        if (identityType.equals("Student")) {
            this.identityType = identityType;
        } else {
            System.out.println(Feedback.ILLEGAL_IDENTITY);
        }
    }


}