package service;



import DAO.CourseRegistrationDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.CourseRegistration;
import model.User;

public class CourseRegistrationService {
    private CourseRegistrationDAO dao;

    public CourseRegistrationService() {
        dao = new CourseRegistrationDAO();
    }

    public List<CourseRegistration> getAllRegistrations(String search, String category, int customerId) throws SQLException, Exception {
        List<CourseRegistration> registrations = dao.getAllRegistrations(search, category, customerId);
        // Apply additional filtering if needed (e.g., client-side filtering)
        if (search != null && !search.trim().isEmpty()) {
            String finalSearch = search.toLowerCase();
            registrations = registrations.stream()
                    .filter(reg -> reg.getSubject().toLowerCase().contains(finalSearch))
                    .collect(Collectors.toList());
        }
        if (category != null && !category.trim().isEmpty()) {
            registrations = registrations.stream()
                    .filter(reg -> reg.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        return registrations;
    }

    public void cancelRegistration(int id, User user) throws SQLException, Exception {
        dao.cancelRegistration(id, user);
    }

    public long getActiveCount(int customerId) throws SQLException, Exception {
        return dao.getActiveCount(customerId);
    }

    public long getSubmittedCount(int customerId) throws SQLException, Exception {
        return dao.getSubmittedCount(customerId);
    }
}
