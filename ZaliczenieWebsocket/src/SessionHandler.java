import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SessionHandler {
    private static final Set<Session> sessions = new HashSet<>();

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static boolean removeSession(Session session) {
        return sessions.remove(session);
    }

    public static void sendToSession(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public static void sendToAllConnectedSessions(String message) throws IOException {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    public static void sendToAllConnectedSessionsInRoom(String roomID, String message) throws IOException {
        for (Session session : sessions) {
            if(session.getUserProperties().get("roomID").equals(roomID)) {
                sendToSession(session, message);
            }
        }
    }
}
