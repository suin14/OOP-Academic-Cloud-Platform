package User;

import Sys.Feedback;

public class Student extends User{
    public Student(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
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
