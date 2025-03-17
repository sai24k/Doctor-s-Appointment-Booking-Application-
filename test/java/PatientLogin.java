import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/PatientLogin")
public class PatientLogin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        String uname = request.getParameter("t1");
        String upass = request.getParameter("t2");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "lbrce");
            if (con != null) {
                PreparedStatement pstmt = con.prepareStatement("select * from patients where name=? and password=?");
                pstmt.setString(1, uname);
                pstmt.setString(2, upass);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("uname", uname);
                    System.out.println("Session Id" + session.getId());
                    response.sendRedirect(request.getContextPath() + "/PatientHome");
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("./PatientLogin.html");
                    rd.include(request, response);
                    out.println("Login unsuccessful");
                    pstmt.close();
                }
            }
            out.close();
        } catch (Exception e) {
            out.println("Username doesn't exist");
            e.printStackTrace();
        }
    }
}
