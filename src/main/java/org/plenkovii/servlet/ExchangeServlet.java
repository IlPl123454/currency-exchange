package org.plenkovii.servlet;

import org.plenkovii.dto.ExchangeRequestDTO;
import org.plenkovii.dto.ExchangeResponseDTO;
import org.plenkovii.exception.EntityExistException;
import org.plenkovii.exception.InvalidParameterexception;
import org.plenkovii.service.ExchangeService;
import org.plenkovii.utils.ExchangeValidator;
import org.plenkovii.utils.JsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    ExchangeService exchangeService = new ExchangeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amount = req.getParameter("amount");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            ExchangeValidator.validate(baseCurrencyCode, targetCurrencyCode, amount);

            ExchangeRequestDTO exchangeRequestDTO = new ExchangeRequestDTO(baseCurrencyCode, targetCurrencyCode, Double.parseDouble(amount));

            ExchangeResponseDTO exchangeResponseDTO = exchangeService.exchange(exchangeRequestDTO);

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertExchangeToJson(exchangeResponseDTO));
        } catch (InvalidParameterexception e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.print(JsonBuilder.buildJsonMessage(e.getMessage()));
        } catch (EntityExistException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.print(JsonBuilder.buildJsonMessage(e.getMessage()));
        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(JsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
            System.out.println(e.getMessage());
        }
    }
}
