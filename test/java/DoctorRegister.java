
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DoctorRegister")
public class DoctorRegister extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String hname = request.getParameter("hname");
        String name = request.getParameter("dname");
        String specialty = request.getParameter("dspec");
        String phone = request.getParameter("dphone");
        String email = request.getParameter("dmail");
        String password = request.getParameter("password");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "lbrce");
            String sql = "INSERT INTO doctors (hospital_name, name, specialty, email, phone_no,password) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hname);
            pstmt.setString(2, name);
            pstmt.setString(3, specialty);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, password);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("");
                HttpSession session = request.getSession();
                session.setAttribute("uname", name);
                System.out.println("Session Id" + session.getId());
                response.sendRedirect(request.getContextPath() + "/DoctorHome");
            } else {
                out.println("Unexpected Error");
            }
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
