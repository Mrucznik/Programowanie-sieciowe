import java.util.*;

public class Room {
    private String roomName;
    private List<Message> history = new LinkedList<>();
    private Map<String, User> users = new HashMap<>();

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public String getName() {
        return roomName;
    }

    public void addUser(String userName) {
        users.put(userName, new User(userName));
    }

    public void removeUser(String userName) {
        users.remove(userName);
        if(users.size() == 0)
            clearHistory();
    }

    public boolean addMessage(Message message) {
        return history.add(message);
    }

    public void clearHistory() {
        history.clear();
    }

    public List<Message> getHistory() {
        return history;
    }

    public User getUser(String name)
    {
        return users.get(name);
    }
}
