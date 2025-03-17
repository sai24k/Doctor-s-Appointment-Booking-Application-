
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DisplayDoctors")
public class DisplayDoctors extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("<html><head><title>Doctor Appointment</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; }");
        out.println("h2 { color: #007bff; }");
        out.println("p { margin: 10px 0; }");
        out.println(".doctor-info { background-color: #f2f2f2; padding: 20px; border-radius: 5px; }");
        out.println(
                ".book-btn { background-color: #007bff; color: #fff; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; }");
        out.println(".book-btn:hover { background-color: #0056b3; }");
        out.println(".limit{color:red;}");
        out.println("</style>");
        out.println("</head><body>");

        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "lbrce");
            String hospital = req.getParameter("hospital");
            String query = "select * from doctors where hospital_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, hospital);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                out.println("<form action=\"BookAppointmentServlet\" method=\"post\">");
                out.println("<div class=\"doctor-info\">");
                out.println("<h3>Dr." + rs.getString("name") + ", " + rs.getString("specialty")
                        + "<input type=\"hidden\" name=\"doctorName\" value=\"" + rs.getString("name") + "\"></h3>");
                out.println("<p><strong>Hospital:</strong> " + rs.getString("hospital_name")
                        + "<input type=\"hidden\" name=\"hospitalName\" value=\"" + rs.getString("hospital_name")
                        + "\"></p>");
                out.println("<p><strong>Email:</strong> " + rs.getString("email") + "</p>");
                out.println("<p><strong>Phone:</strong> " + rs.getString("phone_no") + "</p>");
                out.println("<p><strong>Status:</strong> " + rs.getString("Availability") + "</p>");
                String isAvailableNow = rs.getString("Availability");
                String countQuery = "SELECT COUNT(*) AS count FROM appointments WHERE doctorname = ?";
                PreparedStatement pstmt1 = connection.prepareStatement(countQuery);
                pstmt1.setString(1, rs.getString("name"));
                ResultSet countRs = pstmt1.executeQuery();
                countRs.next();
                int appointmentsCount = countRs.getInt("count");
                if ("Available".equals(isAvailableNow)) {
                    if (appointmentsCount < 10) {
                        out.println("<p>Doctor Appointment is available.</p>");
                        out.println("<button type=\"submit\" class=\"book-btn\">Book an Appointment </button>");
                        out.println("</form>");
                    } else {
                        out.println(
                                "<p class=\"limit\">Doctor is not Available <br> Doctor's Max appointments are booked <br> Try for Tommorrow.</p>");
                    }
                } else {
                    out.println("<p>Doctor is not available now.</p>");
                }
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
