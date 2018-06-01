import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CoopWitacz", urlPatterns = {"/witacz"})
public class CoopWitacz extends HttpServlet {
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

            out.println("Cześć " + request.getParameter("imie") + "<br/>");
            out.println("<a href=\"data\">powrót</a>");

            out.println("</body>");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
