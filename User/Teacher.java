package User;

import Sys.Feedback;

public class Teacher extends User {
    public Teacher(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setId(String id) {
        if (id.matches("\\d{5}") && Integer.parseInt(id) >= 1 && Integer.parseInt(id) <= 99999) {
            this.id = id;
            return true;
        }
        System.out.println(Feedback.ILLEGAL_USER_ID);
        return false;
    }

    @Override
    public boolean setIdentityType(String identityType) {
        if (identityType.equals("Teacher")) {
            this.identityType = identityType;
            return true;
        } else {
            System.out.println(Feedback.ILLEGAL_IDENTITY);
            return false;
        }
    }
}
