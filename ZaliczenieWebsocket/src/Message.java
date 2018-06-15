import java.util.Calendar;
import java.util.Date;

public class Message {
    private Date date;
    private String nick;
    private String content;

    public Message(String content) {
        this.nick = null;
        this.content = content;
        date = Calendar.getInstance().getTime();
    }

    public Message(String nick, String content) {
        this.nick = nick;
        this.content = content;
        date = Calendar.getInstance().getTime();
    }

    public Date getDate() {
        return date;
    }

    public String getNick() {
        return nick;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString()
    {
        if(nick == null)
            return String.format("%s", content);
        else
            return String.format("%s: %s", nick, content);
    }
}
