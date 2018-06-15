import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.websocket.Session;
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
        sendUsers();
    }

    void removeUser(Session userSession) {
        users.remove(userSession);
        if(users.size() == 0)
            history.clear();
        else
            sendUsers();
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

    void setWriting(User user, boolean writing)
    {
        user.setWriting(writing);
        sendUsers();
    }

    private void sendUsers()
    {
        JsonArrayBuilder usersArray = Json.createArrayBuilder();
        for (User user : users.values())
        {
            usersArray.add(Json.createObjectBuilder()
                    .add("nick", user.getNick())
                    .add("writing", user.isWriting())
                    .build()
            );
        }
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("messageType", "users")
                .add("users", usersArray.build())
                .build();
        for(User user : users.values()) {
            user.getSession().getAsyncRemote().sendText(jsonObject.toString());
        }
    }
}
