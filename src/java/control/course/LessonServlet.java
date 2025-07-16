/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package control.course;

import DAO.CourseDAO;
import DAO.LessonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Chapter;
import model.Course;
import model.Lesson;
import model.LessonContent;
import model.Module;
import model.SubChapter;

/**
 *
 * @author An_PC
 */
@WebServlet(name = "LessonServlet", urlPatterns = {"/lesson"})
public class LessonServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        LessonDAO dao = new LessonDAO();
        CourseDAO daoC = new CourseDAO();
        int courseId = Integer.parseInt(request.getParameter("courseId"));

        List<Module> listModule = dao.getModuleByCourse(courseId);
        List<Chapter> listChapter = new ArrayList<>();
        List<Lesson> listLesson = new ArrayList<>();
        List<LessonContent> listLessonContent = new ArrayList<>();
        List<SubChapter> listSubChapter = new ArrayList<>();

        Course course = daoC.getCourseById(courseId);

        for (Module module : listModule) {
            List<Chapter> chapters = dao.getChapterByModule(module.getId());
            listChapter.addAll(chapters);

            for (Chapter chapter : chapters) {

                // Lay lesson trong chapter
                List<Lesson> lessonsDirect = dao.getLessonByChapter(chapter.getId());
                listLesson.addAll(lessonsDirect);
                for (Lesson lesson : lessonsDirect) {
                    LessonContent content = dao.getLessonContentByLessonId(lesson.getId());
                    if (content != null) {
                        listLessonContent.add(content);
                    }
                }

                // Lay sub chapter
                List<SubChapter> subChapters = dao.getSubChapterByChapter(chapter.getId());
                listSubChapter.addAll(subChapters);

                // Lay lesson trong sub chapter
                for (SubChapter sub : subChapters) {
                    List<Lesson> lessonsFromSub = dao.getLessonBySubChapter(sub.getId());
                    listLesson.addAll(lessonsFromSub);
                    for (Lesson lesson : lessonsFromSub) {
                        LessonContent content = dao.getLessonContentByLessonId(lesson.getId());
                        if (content != null) {
                            listLessonContent.add(content);
                        }
                    }
                }
            }
        }

        request.setAttribute("course", course);
        request.setAttribute("listModule", listModule);
        request.setAttribute("listChapter", listChapter);
        request.setAttribute("listSubChapter", listSubChapter);
        request.setAttribute("listLesson", listLesson);
        request.setAttribute("listLessonContent", listLessonContent);

        request.getRequestDispatcher("Lesson.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
