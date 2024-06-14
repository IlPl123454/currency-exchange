package org.plenkovii.servlet;

import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.service.ExchangeRatesService;
import org.plenkovii.utils.JsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");

        System.out.println("exchangeRates");

        List<ExchangeRateResponseDTO> exchangeRates = null;
        try {
            exchangeRates = exchangeRatesService.getAllExchangeRates();
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertExchangeRatesToJsonArray(exchangeRates));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(JsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
        } finally {
            writer.flush();
        }
    }
}
