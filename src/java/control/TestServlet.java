package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import context.DBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Database Connection Test</h2>");
        
        try {
            DBContext dbContext = new DBContext();
            Connection conn = dbContext.getConnection();
            
            if (conn != null) {
                out.println("<p style='color: green;'>✅ Database connection successful!</p>");
                
                // Test simple query
                String sql = "SELECT COUNT(*) as course_count FROM Course";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    int count = rs.getInt("course_count");
                    out.println("<p>📊 Total courses in database: " + count + "</p>");
                }
                
                rs.close();
                stmt.close();
                conn.close();
                
                // Test CourseDAO method
                out.println("<h3>Testing CourseDAO methods:</h3>");
                try {
                    DAO.CourseDAO courseDAO = new DAO.CourseDAO();
                    
                    // Test getCategories
                    java.util.List<String> categories = courseDAO.getCategories();
                    out.println("<p>✅ getCategories() - Found " + categories.size() + " categories</p>");
                    for (String cat : categories) {
                        out.println("<li>" + cat + "</li>");
                    }
                    
                    // Test getTotalCoursesForManagement
                    int total = courseDAO.getTotalCoursesForManagement(null, null, null);
                    out.println("<p>✅ getTotalCoursesForManagement() - Total: " + total + "</p>");
                    
                    // Test getCoursesForManagement
                    java.util.List<DAO.CourseDAO.CourseManagementItem> courses = 
                        courseDAO.getCoursesForManagement(null, null, null, 5, 0);
                    out.println("<p>✅ getCoursesForManagement() - Found " + courses.size() + " courses</p>");
                    
                } catch (Exception e) {
                    out.println("<p style='color: red;'>❌ CourseDAO Error: " + e.getMessage() + "</p>");
                    e.printStackTrace(out);
                }
                
            } else {
                out.println("<p style='color: red;'>❌ Database connection failed!</p>");
            }
            
        } catch (Exception e) {
            out.println("<p style='color: red;'>❌ Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
        
        out.println("</body></html>");
    }
}
