import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Sesja", urlPatterns = { "/login", "/logout" })
public class Sesja extends HttpServlet {
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
            PrintWriter out = response.getWriter();

            out.println("<body>");

            HttpSession session = request.getSession(true);

            String action = request.getParameter("akcja");
            if(action != null)
            {
                if(action.equals("wyloguj"))
                {
                    session.setAttribute("zalogowany", false);
                }
            }

            Boolean loggedIn = (Boolean)session.getAttribute("zalogowany");

            if(loggedIn == null)
                loggedIn = false;

            if(!loggedIn)
            {
                String user = request.getParameter("user");
                String pass = request.getParameter("pass");
                if(user != null && pass != null)
                {
                    if(user.equals("Mrucznik") && pass.equals("kox")) {
                        session.setAttribute("zalogowany", true);
                        loggedIn = true;
                    }
                }
            }

            if(loggedIn) {
                out.println("<h1>Zalogowany</h1>");
                out.println("<form method=\"get\">");
                out.println("<input type=\"hidden\" name=\"akcja\" value=\"wyloguj\">");
                out.println("<input type=\"submit\" name=\"Wyloguj\">");
                out.println("</form>");
            } else {
                out.println("<form method=\"get\">");
                out.println("<input type=\"text\" name=\"user\">");
                out.println("<input type=\"password\" name=\"pass\">");
                out.println("<input type=\"submit\" name=\"zaloguj\">");
                out.println("</form>");
            }

            out.println("</body>");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
