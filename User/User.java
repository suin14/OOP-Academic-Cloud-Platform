package User;

import Sys.Feedback;

public class User {
    protected String id;
    protected String name;
    protected String password;
    protected String identityType;

    public User(String id, String name, String password, String checkPwd, String identityType){
        setId(id);
        setName(name);
        if (!password.equals(checkPwd)) {  // 确认密码与密码不一致
            System.out.println(Feedback.PASSWORD_NOT_MATCH);
            return;
        }
        setPassword(password);
        setIdentityType(identityType);
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
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }
}
