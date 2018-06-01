import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Servlet", urlPatterns = { "/servlet" })
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/html;carset=UTF-8");
        try {
            PrintWriter out = response.getWriter();

            out.println("<h1>No elo</h1>");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
