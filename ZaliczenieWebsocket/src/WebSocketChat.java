import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

@ServerEndpoint(value = "/chat/{roomID}/{nick}")
public class WebSocketChat {
    private ScriptEngine engine;
    private ChatManager chatManager = ChatManager.getInstance();

    public WebSocketChat()
    {
        ScriptEngineManager sem = new ScriptEngineManager();
        this.engine = sem.getEngineByName("javascript");
    }

    @OnOpen
    public void onOpen(Session session,  @PathParam("roomID") final String roomID, @PathParam("nick") final String nick) {
        System.out.println(session.getId() + " has opened a connection");

        session.getUserProperties().put("roomID", roomID);
        session.getUserProperties().put("nick", nick);

        chatManager.joinRoom(session, nick, roomID);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, ScriptException {
        System.out.println("Message from " + session.getId() + ": " + message);

        String roomID = session.getUserProperties().get("roomID").toString();
        Map<String, Object> result = parseJson(message);

        String messageType = (String)result.get("type");
        if(messageType.equals("message"))
        {
            chatManager.sendMessage(roomID, session, (String) result.get("message"));
        }
        else if(messageType.equals("writing"))
        {
            boolean writing = (Boolean)result.get("writing");
            chatManager.userWriting(roomID, session, writing);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session " + session.getId() + " has ended");

        String roomID = session.getUserProperties().get("roomID").toString();
        chatManager.leftRoom(session, roomID);
    }

    private Map<String, Object> parseJson(String json) throws IOException, ScriptException {
        String script = "Java.asJSONCompatible(" + json + ")";
        Object result = this.engine.eval(script);
        return (Map<String, Object>) result;
    }
}