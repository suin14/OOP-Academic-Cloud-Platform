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
}