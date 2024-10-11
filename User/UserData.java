package User;

import Sys.Feedback;

import java.util.HashMap;

public class UserData {
    private static UserData instance;
    private final HashMap<String, User> userMap;

    private UserData() {
        userMap = new HashMap<>();
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public void createUser(String id, User user) {
        userMap.put(id, user);
        System.out.println(Feedback.REGISTER_SUCCESS);
    }

    public boolean hasUser(String id) {
        return userMap.containsKey(id);
    }

    public User findUser(String id) {
        return userMap.get(id);
    }

    public void removeUser(String id) {
        userMap.remove(id);
    }

    public boolean loginUser(String id) {
        if (userMap.get(id).isLogin()) {
            System.out.println(id + " is online");
            return false;
        }
        userMap.get(id).setLogin();
        return true;
    }
}
