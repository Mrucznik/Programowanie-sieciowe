import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatManager {
    private static ChatManager instance;
    private Map<String, Room> rooms;

    private ChatManager() {
        rooms = new HashMap<>();
    }

    public void addToRoom(String roomName, String userName)
    {
        Room room = rooms.get(roomName);
        if(room == null) {
            room = new Room(roomName);
            rooms.put(roomName, room);
        }
        room.addUser(userName);
    }

    public void saveMessage(String roomName, Message message)
    {
        rooms.get(roomName).addMessage(message);
    }

    public void removeFromRoom(String roomName, String userName)
    {
        rooms.get(roomName).removeUser(userName);
    }

    public List<Message> getRoomHistory(String roomName)
    {
        return rooms.get(roomName).getHistory();
    }

    public static ChatManager getInstance()
    {
        if(instance == null)
        {
            instance = new ChatManager();
        }
        return instance;
    }
}
