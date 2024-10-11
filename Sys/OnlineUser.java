package Sys;

import java.util.ArrayList;
import User.*;

public class OnlineUser {
    private static OnlineUser instance;

    private final ArrayList<User> onlineUserList;

    private OnlineUser() {
        onlineUserList = new ArrayList<>();
    }

    public static OnlineUser getInstance() {
        if (instance == null) {
            instance = new OnlineUser();
        }
        return instance;
    }

    public void addUser(User user) {
        onlineUserList.add(user);
    }

    // 获取在线用户列表
    public ArrayList<User> getOnlineUserList() {
        return onlineUserList;
    }

    public void removeUser(User user) {
        onlineUserList.remove(user);
    }
}
