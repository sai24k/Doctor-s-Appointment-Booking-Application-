import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowAppointments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("uname") == null) {
            response.sendRedirect("DoctorLogin.html");
            return;
        }
        String name = (String) session.getAttribute("uname");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
                    "lbrce")) {
                String query = "select * from appointments where doctorname = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                ResultSet rs = preparedStatement.executeQuery();
                out.println("<html><head><style>");
                out.println("body { background: linear-gradient(to right, #007bff, #00b7ff); color: white; }");
                out.println("table { border-collapse: collapse; width: 100%; max-width: 800px; margin: 0 auto; }");
                out.println("th, td { text-align: left; padding: 8px; border: 1px solid #ddd; }");
                out.println("th { background-color: #007bff; }");
                out.println("tr:nth-child(even) { background-color: #f2f2f2; color: #333; }");
                out.println("</style></head><body>");
                out.println("<h2>Your Patients :-</h2><br>");
                out.println("<table>");
                out.println("<th><td>Name</td><td>Gender</td><td>Phone</td><td>Mail</td></th>");
                int i = 1;
                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + i + "</td><td>" + rs.getString("patientname") + "</td><td>"
                            + rs.getString("Gender") + "</td><td>" + rs.getString("patient_phone") + "</td><td>"
                            + rs.getString("patient_mail") + "</td>");
                    out.println("</tr>");
                    i++;
                }
                out.println("</table>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}