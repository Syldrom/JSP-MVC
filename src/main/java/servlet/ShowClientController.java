package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplejdbc.CustomerEntity;
import simplejdbc.DAO;
import simplejdbc.DataSourceFactory;

/**
 * Show Client MVC controller
 */
@WebServlet(name = "ShowClientController", urlPatterns = {"/ShowClientController"})
public class ShowClientController extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		try {	// Trouver la valeur du paramètre HTTP customerID
			String val = request.getParameter("customerID");
			if (val == null)
				throw new Exception("Le paramètre customerID n'a pas été transmis");
			
			// on doit convertir cette valeur en entier (attention aux exceptions !)
			int customerID = Integer.valueOf(val);

			DAO dao = new DAO(DataSourceFactory.getDataSource());
			CustomerEntity customer = dao.findCustomer(customerID);
			
			request.setAttribute("customer", customer);
			// On redirige vers la vue
			request.getRequestDispatcher("clientView.jsp").forward(request, response);

		} catch (Exception e) {
			request.setAttribute("error", e.getMessage());			
			// On redirige vers la page d'erreur
			request.getRequestDispatcher("errorView.jsp").forward(request, response);
		}
		
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
		processRequest(request, response);
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
		processRequest(request, response);
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