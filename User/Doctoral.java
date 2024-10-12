package User;

import Sys.Feedback;

public class Doctoral extends Student{
    public Doctoral(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setId(String id) {
        if (id.length() == 9) {
            if (id.substring(1).matches("\\d+")) {
                if (Integer.parseInt(id.substring(2, 4)) >= 19 && Integer.parseInt(id.substring(2, 4)) <= 24) {
                    if (Integer.parseInt(id.substring(4, 6)) >= 1 && Integer.parseInt(id.substring(4, 6)) <= 43) {
                        if (Integer.parseInt(id.substring(6, 7)) >= 1 && Integer.parseInt(id.substring(6, 7)) <= 6) {
                            if (Integer.parseInt(id.substring(7, 9)) >= 1 && Integer.parseInt(id.substring(7, 9)) <= 99) {
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
