package User;

import Sys.Feedback;

public class Undergraduate extends Student {
    public Undergraduate(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setId(String id) {
        if (id.length() == 8) {
            if (id.matches("\\d+")) {
                if (Integer.parseInt(id.substring(0, 2)) >= 19 && Integer.parseInt(id.substring(0, 2)) <= 24) {
                    if (Integer.parseInt(id.substring(2, 4)) >= 1 && Integer.parseInt(id.substring(2, 4)) <= 43) {
                        if (Integer.parseInt(id.substring(4, 5)) >= 1 && Integer.parseInt(id.substring(4, 5)) <= 6) {
                            if (Integer.parseInt(id.substring(5, 8)) >= 1 && Integer.parseInt(id.substring(5, 8)) <= 99) {
                                this.id = id;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(Feedback.ILLEGAL_USER_ID);
        return false;
    }
}
