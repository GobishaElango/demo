package Shipment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/shipment")
public class ShipmentServlet extends HttpServlet {
    private ShipmentInoutDAO shipmentInoutDAO;

    @Override
    public void init() throws ServletException {
        shipmentInoutDAO = new ShipmentInoutDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));

        try {
            List<ShipmentInoutVO> shipments = shipmentInoutDAO.main(page, recordsPerPage);
            request.setAttribute("shipments", shipments);
            request.getRequestDispatcher("/shipment.jsp").forward(request, response); // Forward to a JSP for rendering
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                ShipmentInoutVO shipment = new ShipmentInoutVO();
                shipment.setDocumentNo(request.getParameter("document_no"));
                shipment.setDocumentDate(Date.valueOf(request.getParameter("document_date")));
                shipment.setCustomerId(request.getParameter("customer_id"));
                shipment.setCreatedby(request.getParameter("createdby")); // Ensure this is passed
                shipment.setUpdatedby(request.getParameter("updatedby")); // Ensure this is passed

                shipmentInoutDAO.addShipment(shipment);
                response.sendRedirect("shipment"); // Redirect to the GET method
            } else if ("edit".equals(action)) {
                ShipmentInoutVO shipment = new ShipmentInoutVO();
                shipment.setInoutId(request.getParameter("inout_id"));
                shipment.setDocumentNo(request.getParameter("document_no"));
                shipment.setDocumentDate(Date.valueOf(request.getParameter("document_date")));
                shipment.setCustomerId(request.getParameter("customer_id"));
                shipment.setUpdatedby(request.getParameter("updatedby")); // Ensure this is passed

                shipmentInoutDAO.updateShipment(shipment);
                response.sendRedirect("shipment"); // Redirect to the GET method
            } else if ("delete".equals(action)) {
                String inoutId = request.getParameter("inout_id");
                shipmentInoutDAO.deleteShipment(inoutId);
                response.sendRedirect("shipment"); // Redirect to the GET method
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
}
