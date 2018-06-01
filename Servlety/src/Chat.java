import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends HttpServlet {
    private Collection<String> chat = Collections.synchronizedCollection(new Vector<String>());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
    {

        response.setContentType("text/html;charset=UTF-8");
        try {
            String message = request.getParameter("message");
            if(message != null)
            {
                chat.add(message);
            }

            PrintWriter out = response.getWriter();
            out.println("<head>");
            out.println("<META HTTP-EQUIV=Refresh CONTENT='10'>");
            out.println("</head>");

            out.println("<body>");

            out.println("<div>");
            for (String msg : chat) {
                out.println(msg + "<br/>");
            }
            out.println("</div>");

            out.println("<div>");
            out.println("<form method=\"post\">");
            out.println("<input type=\"text\" name=\"message\">");
            out.println("<input type=\"submit\" name=\"Wyślij\">");
            out.println("</form>");
            out.println("</div>");

            out.println("</body>");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
