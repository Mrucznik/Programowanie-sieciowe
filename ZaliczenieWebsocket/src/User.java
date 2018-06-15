import javax.websocket.Session;

public class User {
    private String nick;

    public User(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }
}
