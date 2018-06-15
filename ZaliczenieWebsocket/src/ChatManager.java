import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

class ChatManager {
    private static ChatManager instance;
    private Map<String, Room> rooms;

    private ChatManager() {
        rooms = new HashMap<>();
    }

    void joinRoom(Session userSession, String userName, String roomName) {
        Room room = rooms.computeIfAbsent(roomName, Room::new);
        User user = new User(userSession, userName);

        user.sendMessage("Połączono z kanałem " + roomName);
        room.sendToRoom(new Message(userName + " dołączył do pokoju."));
        room.addUser(userSession, user);
        sendHistory(room, user);
    }

    void leftRoom(Session userSession, String roomName)
    {
        Room room =  rooms.get(roomName);
        User user = room.getUser(userSession);
        Message message = new Message(user.getNick() + " odszedł z pokoju.");

        room.sendToRoom(message);
        room.removeUser(userSession);
    }

    void sendMessage(String roomName, Session userSession, String messageText)
    {
        Room room =  rooms.get(roomName);
        User user = room.getUser(userSession);

        room.sendToRoom(new Message(user.getNick(), messageText));
    }

    private void sendHistory(Room room, User user)
    {
        for(Message message : room.getHistory()) {
            user.sendMessage(message);
        }
    }

    static ChatManager getInstance()
    {
        if(instance == null)
        {
            instance = new ChatManager();
        }
        return instance;
    }
}
