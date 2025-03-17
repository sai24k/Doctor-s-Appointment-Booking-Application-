import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/PatientProfile")
public class PatientProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);
        String uname = (String) session.getAttribute("uname");
        Connection connection = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "lbrce");

            String query = "select * from patients where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uname);
            ResultSet rs = preparedStatement.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Doctor Profile</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif;  background-color: #f2f2f2; }");
            out.println(".user-info {  background-color: #fff;  padding: 20px;  border-radius: 5px;  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);  max-width: 600px;  margin: 0 auto;  display: flex;  align-items: center; }");
            out.println(".user-info img { width: 100px;  height: 100px;  margin-right: 20px;}");
            out.println("h3 { color: #333;  margin-top: 0; }");
            out.println("p { color: #666;  margin: 10px 0; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            while (rs.next()) {
                out.println("<div class=\"user-info\">");
                out.println("<img src=\"https://www.kindpng.com/picc/m/495-4952535_create-digital-profile-icon-blue-user-profile-icon.png\" alt=\"Doctor Icon\">");
                out.println("<div>");
                out.println("<h3>" + rs.getString("name") + "</h3>");
                out.println("<p><strong>Email:</strong> " + rs.getString("email") + "</p>");
                out.println("<p><strong>Phone:</strong> " + rs.getString("phone_no") + "</p>");
                out.println("</div>");
                out.println("</div>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    out.println("<p></p>");
                } catch (SQLException e) {
                    out.println("<p>Error closing connection: " + e.getMessage() + "</p>");
                }
            }
        }

        out.println("</body></html>");
        out.close();
    }
}