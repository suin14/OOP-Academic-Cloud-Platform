package User;

import Sys.Feedback;

public class Administrator extends User {
    public Administrator(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setId(String id) {
        if (id.length() == 5 && id.startsWith("AD")) {
            String numericPart = id.substring(2);
            if (numericPart.matches("\\d{3}")) {  // 检查后三位是否为数字
                int number = Integer.parseInt(numericPart);
                if (number >= 1 && number <= 999) {
                    this.id = id;
                    return true;
                }
            }
        }
        System.out.println(Feedback.ILLEGAL_USER_ID);
        return false;
    }


    @Override
    public boolean setIdentityType(String identityType) {
        if (identityType.equals("Administrator")) {
            this.identityType = identityType;
            return true;
        } else {
            System.out.println(Feedback.ILLEGAL_IDENTITY);
            return false;
        }
    }

    @Override
    public void listTeacherCourse(String id){
        // 学工号不合法
        if (!(id.matches("\\d{5}") && Integer.parseInt(id) >= 1 && Integer.parseInt(id) <= 99999)) {
            System.out.println(Feedback.ILLEGAL_USER_ID);
            return;
        }

        // 用户未注册
        if (!userData.hasUser(id)) {
            System.out.println(Feedback.USER_NOT_EXISTS);
            return;
        }

        //工号对应的用户权限不为 Teacher
        User user = userData.findUser(id);

        if (!user.getIdentityType().equals("Teacher")) {
            System.out.println(Feedback.NOT_TEACHER);
            return;
        }
        user.listCourse();
    }
}
