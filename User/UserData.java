package User;

import Sys.Feedback;
import Sys.OnlineUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserData {
    private static UserData instance;
    private final HashMap<String, User> users;
    private final OnlineUser onlineUser;

    private UserData() {
        users = new HashMap<>();
        this.onlineUser = OnlineUser.getInstance();
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public void createUser(String id, User user) {
        users.put(id, user);
        System.out.println(Feedback.REGISTER_SUCCESS);
    }

    public boolean hasUser(String id) {
        return users.containsKey(id);
    }

    public User findUser(String id) {
        return users.get(id);
    }

    public void removeUser(String id) {
        users.remove(id);
    }

    public boolean loginUser(String id) {
        if (findUser(id).isLogin()) {
            System.out.println(id + " is online");
            return false;
        }
        findUser(id).setLogin();
        return true;
    }

    public void logoutUser(String id) {
        User user = findUser(id);
        user.setLogout();
        onlineUser.removeUser(user);
        System.out.println(id + " Bye~");
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getIdentityType().equals("Student")) {
                students.add((Student) user);
            }
        }
        return students;
    }

    public Student findStudent(String studentId) {
        for (Student student : getAllStudents()) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }
}
