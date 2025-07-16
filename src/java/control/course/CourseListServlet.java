package control.course;

import DAO.CourseDAO.CourseListItem;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.CourseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/courses")
public class CourseListServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(CourseListServlet.class.getName());
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String searchKeyword = request.getParameter("search");
            String category = request.getParameter("category");
            String sortBy = request.getParameter("sortBy"); // Lấy từ URL
            String rowsPerPageStr = request.getParameter("rowsPerPage");
            String currentPageStr = request.getParameter("page");
            String tag = request.getParameter("tag");

            if (rowsPerPageStr == null || rowsPerPageStr.trim().isEmpty()) {
                rowsPerPageStr = "2";
            }
            int[] paginationParams = courseService.validatePaginationParams(rowsPerPageStr, currentPageStr);
            int rowsPerPage = paginationParams[0];
            int currentPage = paginationParams[1];

            String showThumbnail = courseService.validateDisplayOption(request.getParameter("showThumbnail"), "on");
            String showTitle = courseService.validateDisplayOption(request.getParameter("showTitle"), "off");
            String showPrice = courseService.validateDisplayOption(request.getParameter("showPrice"), "off");
            String showTagline = courseService.validateDisplayOption(request.getParameter("showTagline"), "off");
            String showPublicDate = courseService.validateDisplayOption(request.getParameter("showPublicDate"), "off");

            if ("off".equals(showThumbnail) && "off".equals(showTitle) && "off".equals(showPrice) && "off".equals(showTagline) && "off".equals(showPublicDate)) {
                showThumbnail = "on";
            }
            Integer taglineId = tag != null && !tag.trim().isEmpty() ? Integer.parseInt(tag) : null;
            List<CourseListItem> courses = courseService.getCourseList(searchKeyword, category, sortBy, taglineId, rowsPerPage, currentPage);
            int totalCourses = courseService.getTotalCourses(searchKeyword, category, taglineId);
            int totalPages = courseService.calculateTotalPages(totalCourses, rowsPerPage);
            List<String> categories = courseService.getCategories();
            List<model.Tagline> taglines = courseService.getAllTaglines();

            setRequestAttributes(request, courses, categories, taglines, searchKeyword, category, sortBy, tag,
                    rowsPerPage, currentPage, totalPages, totalCourses,
                    showThumbnail, showTitle, showPrice, showTagline, showPublicDate);

            request.getRequestDispatcher("/courseList.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in CourseListServlet", e);
            request.setAttribute("errorMessage", "Unable to load courses. Please try again later.");
            request.setAttribute("errorDetails", e.getMessage());
            request.getRequestDispatcher("/courseList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void setRequestAttributes(HttpServletRequest request, List<CourseListItem> courses,
            List<String> categories, List<model.Tagline> taglines,
            String searchKeyword, String category, String sortBy, String tag,
            int rowsPerPage, int currentPage, int totalPages,
            int totalCourses, String showThumbnail, String showTitle,
            String showPrice, String showTagline, String showPublicDate) {
        request.setAttribute("courses", courses);
        request.setAttribute("categories", categories);
        request.setAttribute("taglines", taglines);
        request.setAttribute("searchKeyword", searchKeyword != null ? searchKeyword : "");
        request.setAttribute("selectedCategory", category != null ? category : "");
        request.setAttribute("sortBy", sortBy != null ? sortBy : "title");
        request.setAttribute("selectedTag", tag != null ? tag : "");
        request.setAttribute("rowsPerPage", String.valueOf(rowsPerPage));
        request.setAttribute("currentPage", String.valueOf(currentPage));
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalCourses", totalCourses);
        request.setAttribute("showThumbnail", showThumbnail);
        request.setAttribute("showTitle", showTitle);
        request.setAttribute("showPrice", showPrice);
        request.setAttribute("showTagline", showTagline);
        request.setAttribute("showPublicDate", showPublicDate); // Thêm mới
        request.setAttribute("hasResults", courses != null && !courses.isEmpty());
        request.setAttribute("isFiltered",
                (searchKeyword != null && !searchKeyword.trim().isEmpty())
                || (category != null && !category.trim().isEmpty())
                || (tag != null && !tag.trim().isEmpty()));
    }
}
