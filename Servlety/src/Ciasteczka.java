import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Ciasteczka", urlPatterns = { "/cookie" })
public class Ciasteczka extends HttpServlet {
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

            Cookie[] cookies = request.getCookies();
            Cookie licznik = null;

            if(cookies != null)
            {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("licznik")) {
                        licznik = cookie;
                        break;
                    }
                }
            }

            if(licznik == null)
            {
                licznik = new Cookie("licznik", "0");
            }
            else
            {
                Integer v = Integer.parseInt(licznik.getValue());
                v++;
                licznik.setValue(v+"");
            }

            licznik.setMaxAge(60*60*24); //1 dzień
            response.addCookie(licznik);

            out.println("Licznik=" + licznik.getValue());

            out.println("</body>");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
