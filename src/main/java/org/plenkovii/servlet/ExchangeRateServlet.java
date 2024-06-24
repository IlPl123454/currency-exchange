package org.plenkovii.servlet;

import org.plenkovii.dto.ExchangeRateRequestDTO;
import org.plenkovii.dto.ExchangeRateResponseDTO;
import org.plenkovii.entity.ExchangeRate;
import org.plenkovii.exception.EntityExistException;
import org.plenkovii.exception.InvalidParameterexception;
import org.plenkovii.mapper.ExchangeRatesMapper;
import org.plenkovii.service.ExchangeRatesService;
import org.plenkovii.utils.ExchangeRateRequestValidator;
import org.plenkovii.utils.JsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        String currencyCodes = req.getPathInfo();
        String baseCurrencyCode;
        String targetCurrencyCode;

        if (currencyCodes == null || currencyCodes.length() != 7) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(JsonBuilder.buildJsonMessage("Вы ввели недопустимый код валют. Запрос должен состоять из 2 подряд кодов валют."));
            return;
        } else {
            currencyCodes = currencyCodes.substring(1);
            baseCurrencyCode = currencyCodes.substring(0, 3);
            targetCurrencyCode = currencyCodes.substring(3);
        }

        resp.setContentType("application/json");

        try {
            Optional<ExchangeRate> exchangeRateByCodes = exchangeRatesService.getExchangeRateByCodes(baseCurrencyCode, targetCurrencyCode);
            ExchangeRateResponseDTO exchangeRateResponseDTO = ExchangeRatesMapper.entityToRespDTO(exchangeRateByCodes.get());
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertExchangeRateToJson(exchangeRateResponseDTO));
        } catch (EntityExistException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.print(JsonBuilder.buildJsonMessage(e.getMessage()));
        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(JsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        resp.setContentType("application/json");

        try {
            ExchangeRateRequestValidator.validate(baseCurrencyCode, targetCurrencyCode, rate);

            ExchangeRateRequestDTO exchangeRateRequestDTO = new ExchangeRateRequestDTO(
                    baseCurrencyCode,
                    targetCurrencyCode,
                    Double.parseDouble(rate)
            );

            ExchangeRate exchangeRate = exchangeRatesService.saveExchangeRate(exchangeRateRequestDTO);
            ExchangeRateResponseDTO exchangeRateResponseDTO = ExchangeRatesMapper.entityToRespDTO(exchangeRate);

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertExchangeRateToJson(exchangeRateResponseDTO));
        } catch (InvalidParameterexception e) {
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
