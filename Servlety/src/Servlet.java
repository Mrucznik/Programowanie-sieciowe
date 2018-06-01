import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "Servlet", urlPatterns = { "/kek" })
public class Servlet extends HttpServlet {
    private String msg;

    public void init(ServletConfig config) {
        msg = config.getInitParameter("message");
    }

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

            out.println("Parametr: " + msg + "<br/>");

            out.println("wynik getMethod: " + request.getMethod() + "<br/>");
            out.println("wynik getRemoteAddr: " + request.getRemoteAddr() + "<br/>");
            out.println("wynik getServerName: " + request.getServerName() + "<br/>");
            out.println("wynik getHeader(\"Accept\"): " + request.getHeader("Accept") + "<br/>");
            out.println("wynik getHeader(\"Accept-Language\"): " + request.getHeader("Accept-Language") + "<br/>");
            out.println("wynik getHeader(\"Accept-Encoding\"): " + request.getHeader("Accept-Encoding") + "<br/>");
            out.println("wynik getHeader(\"User-Agent\"): " + request.getHeader("User-Agent") + "<br/><br/>");

            try {

                int x = Integer.parseInt(request.getParameter("x"));
                int y = Integer.parseInt(request.getParameter("y"));
                out.println("Wynik=" + (x+y) + "<br/>");
            } catch (Exception e) {
                out.println("Wynik nie odnaleziony - złe parametry<br/>");
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
