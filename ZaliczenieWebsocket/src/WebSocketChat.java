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
        try {
            session.getUserProperties().put("roomID", roomID);
            session.getUserProperties().put("nick", nick);
            SessionHandler.sendToSession(session, "Połączono z kanałem " + roomID);
            chatManager.addToRoom(roomID, nick);

            for(Message message : chatManager.getRoomHistory(roomID)) {
                SessionHandler.sendToSession(session, message.toString());
            }
            String connectionMessage = nick + " dołączył do pokoju.";
            chatManager.saveMessage(roomID, new Message(connectionMessage));
            SessionHandler.sendToAllConnectedSessionsInRoom(roomID, connectionMessage);

            SessionHandler.addSession(session);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);
        try {
            String roomID = session.getUserProperties().get("roomID").toString();
            String nick = session.getUserProperties().get("nick").toString();
            Message msg = new Message(nick, message);
            chatManager.saveMessage(roomID, msg);
            SessionHandler.sendToAllConnectedSessionsInRoom(roomID,  msg.toString());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        String roomID = session.getUserProperties().get("roomID").toString();
        String nick = session.getUserProperties().get("nick").toString();
        chatManager.removeFromRoom(roomID, nick);
        SessionHandler.removeSession(session);
        try {
            String disconnectionMessage = nick + " odszedł z pokoju.";
            chatManager.saveMessage(roomID, new Message(disconnectionMessage));
            SessionHandler.sendToAllConnectedSessionsInRoom(roomID, disconnectionMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Session " + session.getId() + " has ended");
    }
}