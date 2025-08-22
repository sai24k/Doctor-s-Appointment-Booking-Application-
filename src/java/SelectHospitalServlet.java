
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/SelectHospitalServlet")
public class SelectHospitalServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "lbrce");
            String query = "SELECT name FROM hospitals";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            out.println("<form action='DisplayDoctors' method='get'>");
            out.println("<select name='hospital'>");
            while (rs.next()) {
                String name = rs.getString("name");
                out.println("<option>" + name + "</option>");
            }
            out.println("</select>");
            out.println("<input class='btn' type='submit' value='Select Hospital'>");
            out.println("</form>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        out.println("</body>");
        out.println("</html>");
    }
}
