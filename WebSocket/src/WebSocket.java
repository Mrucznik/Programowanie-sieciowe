import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint gives the relative name for the end point * This will be accessed via ws://localhost:8080/EchoChamber/echo * Where "localhost" is the address of the host, * "EchoChamber" is the name of the package * and "echo" is the address to access this class from the server
 */
@ServerEndpoint(value = "/chat/{roomID}")
public class WebSocket {
    /**
     * @OnOpen allows us to intercept the creation of a new session. * The session class allows us to send data to the user. * In the method onOpen, we'll let the user know that the handshake was * successful.
     */
    @OnOpen
    public void onOpen(Session session,  @PathParam("roomID") final String roomID) {
        System.out.println(session.getId() + " has opened a connection");
        try {
            session.getUserProperties().put("roomID", roomID);
            SessionHandler.addSession(session);
            SessionHandler.sendToSession(session, "Connection Established with " + roomID);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the message * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Message from " + session.getId() + ": " + message);
        try {
            String roomID = session.getUserProperties().get("roomID").toString();
            SessionHandler.sendToAllConnectedSessionsInRoom(roomID, "Room " + roomID + ": " + message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The user closes the connection. * * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session) {
        SessionHandler.removeSession(session);
        System.out.println("Session " + session.getId() + " has ended");
    }
}