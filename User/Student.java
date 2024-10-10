package User;

import Sys.Feedback;

public class Student extends User{
    public Student(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setIdentityType(String identityType) {
        if (identityType.equals("Student")) {
            this.identityType = identityType;
            return true;
        } else {
            System.out.println(Feedback.ILLEGAL_IDENTITY);
            return false;
        }
    }
}
