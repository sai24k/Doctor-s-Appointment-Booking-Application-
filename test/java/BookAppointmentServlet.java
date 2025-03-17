
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BookAppointmentServlet")
public class BookAppointmentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String doctorName = request.getParameter("doctorName");
        String hname = request.getParameter("hospitalName");
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("PatientLogin.html");
            return;
        }
        String uname = (String) session.getAttribute("uname");
        if (uname == null) {
            response.sendRedirect("PatientLogin.html");
            return;
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Book Appointment</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; }");
        out.println("h2 { color: #333; text-align: center; }");
        out.println(".container { margin: 0 auto; padding: 20px; background-color: #f5f5f5; border: 1px solid #ddd; }");
        out.println(".success { color: blue; }");
        out.println(".error { color: red; }");
        out.println("a{ text-decoration: none ; color: blue;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "lbrce")) {
                if (con != null) {
                    String query = "select * from patients where name= ?";
                    try (PreparedStatement ps = con.prepareStatement(query)) {
                        ps.setString(1, uname);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                try (PreparedStatement pstmt = con.prepareStatement("insert into appointments (doctorname, patientname,patient_phone,patient_mail,gender) values(?,?,?,?,?)")) {
                                    pstmt.setString(1, doctorName);
                                    pstmt.setString(2, uname);
                                    pstmt.setString(3, rs.getString("phone_no"));
                                    pstmt.setString(4, rs.getString("email"));
                                    pstmt.setString(5, rs.getString("gender"));
                                    int x = pstmt.executeUpdate();
                                    if (x == 0) {
                                        out.println("<p class=\"error\">Booking is not success</p>");
                                    } else {
                                        out.println("<h1 class=\"success\">Your Appointment is Booked </h1>");
                                        out.println("<p><strong>Patient Name</strong> :" + uname + "</p>");
                                        out.println("<p><strong>Doctor Name</strong> :" + doctorName + "</p>");
                                        out.println("<p><strong>Hospital Name</strong> : " + hname + "</p>");
                                        out.println("Go back to <a href=\"PatientHome.html\">Homepage</a>");

                                    }
                                }
                            } else {
                                out.println("<p>Invalid patient details</p>");
                            }
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p class=\"error\">Error occurred: " + e.getMessage() + "</p>");
        } finally {
            out.close();
        }
    }
}
