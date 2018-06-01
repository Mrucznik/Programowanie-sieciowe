import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "CoopDate", urlPatterns = {"/data"})
public class CoopDate extends HttpServlet {
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

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String dataString = dateFormat.format(date);

            out.println("<h2>Aktualna data: " + dataString + "</h2><br/>");
            out.println("<form method=\"get\" action=\"witacz\">");
            out.println("<input type=\"text\" name=\"imie\">");
            out.println("<input type=\"submit\" name=\"przywitaj mnie\">");
            out.println("</form>");

            out.println("</body>");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
