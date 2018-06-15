import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class Room {
    private String roomName;
    private List<Message> history = new LinkedList<>();
    private Map<Session, User> users = new HashMap<>();

    public Room(String roomName) {
        this.roomName = roomName;
    }

    void addUser(Session userSession, User user) {
        users.put(userSession, user);
    }

    void removeUser(Session userSession) {
        users.remove(userSession);
        if(users.size() == 0)
            history.clear();
    }

    User getUser(Session userSession)
    {
        return users.get(userSession);
    }

    List<Message> getHistory() {
        return history;
    }

    void sendToRoom(Message message) {
        history.add(message);
        for(User user : users.values()) {
            user.sendMessage(message);
        }
    }
}
