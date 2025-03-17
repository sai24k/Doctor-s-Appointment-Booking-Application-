import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            HttpSession session = req.getSession();
            if (session != null) {
                session.removeAttribute("uname");
                session.invalidate();
            }
            req.getRequestDispatcher("index.html").include(req, res);
        } catch (Exception e) {
            out.println("Error: " + e);
        } finally {
            out.close();
        }
    }
}