package User;

import Sys.Feedback;

public class User {
    protected String id;
    protected String name;
    protected String password;
    protected String identityType;

    ; // 用户数据管理

    public User(String id, String name, String password, String checkPwd, String identityType){
        setId(id);
        UserData userData = UserData.getInstance();
        if (userData.hasUser(id)) {
            System.out.println(Feedback.USER_EXISTS);
            return;
        }
        setName(name);
        if (!password.equals(checkPwd)) {  // 确认密码与密码不一致
            System.out.println(Feedback.PASSWORD_NOT_MATCH);
            return;
        }
        setPassword(password);
        setIdentityType(identityType);
        System.out.println(Feedback.REGISTER_SUCCESS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // 首字符不能为下划线，其余部分只能是字母和下划线
        if (!name.matches("^[A-Za-z][A-Za-z_]{3,15}$")) {
            System.out.println(Feedback.ILLEGAL_USER_NAME);
            return;
        }

        this.name = name;
    }

    public String getPassword() {
        return password;
    }

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

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }
}
