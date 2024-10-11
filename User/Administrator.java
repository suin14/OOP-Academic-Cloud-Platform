package User;

import Sys.Feedback;

public class Administrator extends User {
    public Administrator(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setId(String id) {
        if (id.length() == 5) {
            if (Integer.parseInt(id.substring(2, 5)) >= 1 && Integer.parseInt(id.substring(2, 5)) <= 999) {
                this.id = id;
                return true;
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
}
