package org.plenkovii.servlet;

import org.plenkovii.dto.ExchangeRateRequestDTO;
import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.entity.ExchangeRate;
import org.plenkovii.exception.EntityExistException;
import org.plenkovii.exception.InvalidParameterexception;
import org.plenkovii.mapper.ExchangeRatesMapper;
import org.plenkovii.service.ExchangeRatesService;
import org.plenkovii.utils.ExchangeRateValidator;
import org.plenkovii.utils.JsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        List<ExchangeRateResponseDTO> exchangeRates = null;
        try {
            exchangeRates = exchangeRatesService.getAllExchangeRates();
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertExchangeRatesToJsonArray(exchangeRates));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(JsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        try {
            ExchangeRateValidator.validate(baseCurrencyCode, targetCurrencyCode, rate);

            ExchangeRateRequestDTO exchangeRateRequestDTO = new ExchangeRateRequestDTO(
                    baseCurrencyCode,
                    targetCurrencyCode,
                    new BigDecimal(rate)
            );

            ExchangeRate exchangeRate = exchangeRatesService.saveExchangeRate(exchangeRateRequestDTO);
            ExchangeRateResponseDTO exchangeRateResponseDTO = ExchangeRatesMapper.entityToRespDTO(exchangeRate);

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertExchangeRateToJson(exchangeRateResponseDTO));
        } catch (InvalidParameterexception | NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(JsonBuilder.buildJsonMessage(e.getMessage()));
        } catch (EntityExistException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.print(JsonBuilder.buildJsonMessage(e.getMessage()));
        } catch (SQLException | ClassNotFoundException e) {
            if (e.getMessage().contains("UNIQUE")) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                writer.print(JsonBuilder.buildJsonMessage("Валютная пара с таким кодом уже существует"));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.print(JsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
            }
        }
    }
}
