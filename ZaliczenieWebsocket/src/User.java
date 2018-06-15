import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Session;

public class User {
    private Session session;
    private String nick;
    private boolean writing = false;

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

    public boolean isWriting() {
        return writing;
    }

    public void setWriting(boolean writing) {
        this.writing = writing;
    }

    public void sendMessage(Message message) {
        sendMessage(message.toString());
    }

    public void sendMessage(String message) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("messageType", "message")
                .add("message", message)
                .build();
        session.getAsyncRemote().sendText(jsonObject.toString());
    }
}
