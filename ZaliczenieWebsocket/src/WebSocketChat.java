import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{roomID}/{nick}")
public class WebSocketChat {
    private ChatManager chatManager = ChatManager.getInstance();

    @OnOpen
    public void onOpen(Session session,  @PathParam("roomID") final String roomID, @PathParam("nick") final String nick) {
        System.out.println(session.getId() + " has opened a connection");

        session.getUserProperties().put("roomID", roomID);
        session.getUserProperties().put("nick", nick);

        chatManager.joinRoom(session, nick, roomID);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);

        String roomID = session.getUserProperties().get("roomID").toString();
        chatManager.sendMessage(roomID, session, message);
    }

    @OnClose
    public void onClose(Session session) {
        String roomID = session.getUserProperties().get("roomID").toString();
        chatManager.leftRoom(session, roomID);

        System.out.println("Session " + session.getId() + " has ended");
    }
}