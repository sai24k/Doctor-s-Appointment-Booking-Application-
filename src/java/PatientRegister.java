import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PatientRegister")
public class PatientRegister extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String uname = request.getParameter("t1");
        String upass = request.getParameter("t2");
        String email = request.getParameter("t3");
        String phone = request.getParameter("t4");
        String gender = request.getParameter("gender");
        try {
            request.getRequestDispatcher("PatientRegister.html").include(request, response);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "lbrce");

            if (con != null) {
                PreparedStatement pstmt = con.prepareStatement("insert into patients (name, password, email, phone_no, gender) values(?,?,?,?,?)");
                pstmt.setString(1, uname);
                pstmt.setString(2, upass);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.setString(5, gender);
                int x = pstmt.executeUpdate();

                if (x == 0) {
                    out.println("Registration is not success");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("uname", uname);
                    System.out.println("Session Id" + session.getId());
                    response.sendRedirect(request.getContextPath() + "/PatientHome");
                }

                pstmt.close();
            } else {
                out.println("Unexcepted Error Occurred");
            }
        } catch (Exception e) {
            out.println("<h1>Error</h1>");
        }
        out.close();
    }
}