package org.plenkovii.servlet;

import org.plenkovii.dao.CurrencyDAO;
import org.plenkovii.dto.CurrencyDTO;
import org.plenkovii.service.CurrencyService;
import org.plenkovii.utils.JsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    CurrencyDAO currencyDAO = new CurrencyDAO();
    JsonBuilder jsonBuilder = new JsonBuilder();
    CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        try {
            currencyDTOList = currencyService.getAllCurrencies();
            String jsonAnswer = jsonBuilder.convertCurrencyDTOToJsonArray(currencyDTOList);
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(jsonAnswer);
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(jsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
        } finally {
            writer.flush();
        }
    }


}
