package com.rs.mb.dispatchers;


import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class MessageDispatcher {

    private static ConcurrentHashMap<String, LocalDateTime> timedOutUsers = new ConcurrentHashMap<>();

    public static Boolean isUserTimedOut(String username) {
        LocalDateTime time = timedOutUsers.get(username);

        if (LocalDateTime.now().isAfter(time.plusSeconds(3))) {

            timedOutUsers.put(username, LocalDateTime.now());
            return false;
        } else {
            return true;
        }
    }

    public static void addUser(String username)  {
        timedOutUsers.put(username, LocalDateTime.now());
    }

    public void removeUserByKey(String username) {
        this.timedOutUsers.remove(username);
    }

    public static boolean containsUser(String username) {
        return timedOutUsers.containsKey(username);
    }

    public static boolean validateTimeOut(String username) {
        if (containsUser(username)) {
            if (isUserTimedOut(username)) {
                return true;
            }
        } else {
            addUser(username);
        }
        return false;
    }
}
