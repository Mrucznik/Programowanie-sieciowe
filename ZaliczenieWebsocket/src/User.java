import javax.websocket.Session;
import java.io.IOException;

public class User {
    private Session session;
    private String nick;

    public User(Session session, String nick) {
        this.session = session;
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public Session getSession() {
        return session;
    }

    public void sendMessage(Message message) {
        sendMessage(message.toString());
    }

    public void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
