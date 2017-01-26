package ch.frostnova.web.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Enumeration;

/**
 * Example servlet
 */
@WebServlet(
        description = "Example Servlet",
        urlPatterns = {"/example"}
)
public class ExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (PrintWriter writer = new PrintWriter(resp.getWriter())) {

            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>Example Servlet</title>");
            writer.write("</head>");
            writer.write("<style>");
            writer.write("@import url('https://fonts.googleapis.com/css?family=Lato:300,400,700,900');");
            writer.write("* { font-family:Lato, Calibri, sans-serif; color: #444455; }");
            writer.write("body { padding: 20px; }");
            writer.write("h1, h2, h3 { font-weight: 900; }");
            writer.write("b { font-weight: 900; }");
            writer.write("</style>");
            writer.write("<body>");
            writer.write("<h1>Example Servlet</h1>");

            Principal userPrincipal = req.getUserPrincipal();
            String userName = userPrincipal != null ? userPrincipal.getName() : "unknown visitor";
            writer.write("Welcome, " + userName + ".<br>");

            writer.write("<h3>Headers</h3>");
            writer.write("<ul>");
            Enumeration<String> headers = req.getHeaderNames();
            while (headers.hasMoreElements()) {
                String header = headers.nextElement();
                writer.write("<li><b>" + header + "</b>: " + req.getHeader(header) + "</li>");
            }
            writer.write("</ul>");

            writer.write("<h3>Request Parameters</h3>");
            writer.write("<ul>");
            Enumeration<String> parameters = req.getParameterNames();
            while (parameters.hasMoreElements()) {
                String parameter = parameters.nextElement();
                writer.write("<li><b>" + parameter + "</b>: " + req.getParameter(parameter) + "</li>");
            }
            writer.write("</ul>");
            writer.write("<h3>Example REST service</h3>");
            writer.write("<a href=\"api/example\">Example Web Service Endpoint (<code>GET /api/example</code>)</a>");

            writer.write("</body>");
            writer.write("</html>");

            writer.flush();
        }
    }
}
