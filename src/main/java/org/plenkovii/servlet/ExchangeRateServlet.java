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
import java.util.Optional;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {

    ExchangeRatesService exchangeRatesService = new ExchangeRatesService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String currencyCodes = req.getPathInfo();
        String baseCurrencyCode;
        String targetCurrencyCode;

        if (currencyCodes == null || currencyCodes.length() != 7) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(JsonBuilder.buildJsonMessage("Вы ввели недопустимый код валют. Запрос должен состоять из 2 кодов валют. Пример: EURUSD"));
            return;
        } else {
            currencyCodes = currencyCodes.substring(1);
            baseCurrencyCode = currencyCodes.substring(0, 3);
            targetCurrencyCode = currencyCodes.substring(3);
        }

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

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String currencyCodes = req.getPathInfo();
        String baseCurrencyCode;
        String targetCurrencyCode;

        if (currencyCodes == null || currencyCodes.length() != 7) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(JsonBuilder.buildJsonMessage("Вы ввели недопустимый код валют. Запрос должен состоять из 2 кодов валют. Пример: EURUSD"));
            return;
        } else {
            currencyCodes = currencyCodes.substring(1);
            baseCurrencyCode = currencyCodes.substring(0, 3);
            targetCurrencyCode = currencyCodes.substring(3);
        }

        String rateS = req.getReader().readLine();

        if (rateS == null || !rateS.contains("rate")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(JsonBuilder.buildJsonMessage("Некорректный ввод курса"));
        }

        rateS = rateS.replace("rate=", "");

        try {
            ExchangeRateValidator.validate(baseCurrencyCode, targetCurrencyCode, rateS);
            BigDecimal rate = new BigDecimal(rateS);

            ExchangeRateRequestDTO exchangeRateRequestDTO = new ExchangeRateRequestDTO(baseCurrencyCode, targetCurrencyCode, rate);
            ExchangeRate exchangeRate = exchangeRatesService.update(exchangeRateRequestDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertExchangeRateToJson(ExchangeRatesMapper.entityToRespDTO(exchangeRate)));
        } catch (InvalidParameterexception | EntityExistException | NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(JsonBuilder.buildJsonMessage(e.getMessage()));
        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(JsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
            System.out.println(e.getMessage());
        }
    }
}
