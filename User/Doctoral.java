package User;

import Sys.Feedback;

public class Doctoral extends Student{
    public Doctoral(String id, String name, String password, String checkPwd, String identityType) {
        super(id, name, password, checkPwd, identityType);
    }

    @Override
    public boolean setId(String id) {
        if (id.length() != 9 || !id.substring(0, 2).matches("[A-Z]{2}") || !id.substring(2).matches("\\d{7}")) {
            System.out.println(Feedback.ILLEGAL_USER_ID);
            return false;
        }

        int year = Integer.parseInt(id.substring(2, 4));
        int college = Integer.parseInt(id.substring(4, 6));
        int classNumber = Integer.parseInt(id.substring(6, 7));
        int studentNumber = Integer.parseInt(id.substring(7, 9));

        if (year >= 19 && year <= 24 &&
                college >= 1 && college <= 43 &&
                classNumber >= 1 && classNumber <= 6 &&
                studentNumber >= 1 && studentNumber <= 99) {
            this.id = id;
            return true;
        }

        System.out.println(Feedback.ILLEGAL_USER_ID);
        return false;
    }
}
