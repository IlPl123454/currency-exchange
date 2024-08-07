package org.plenkovii.servlet;

import org.plenkovii.dto.CurrencyResponseDTO;
import org.plenkovii.service.CurrencyService;
import org.plenkovii.utils.JsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        String currencyCode = req.getPathInfo();
        if (currencyCode == null || currencyCode.length() != 4) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(JsonBuilder.buildJsonMessage("Вы ввели недопустимый код валюты. Код валюты должен содержать 3 символа"));
            return;
        } else {
            currencyCode = currencyCode.substring(1);
        }

        try {
            CurrencyResponseDTO currencyResponseDTO = currencyService.getCurrencyByCode(currencyCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(JsonBuilder.convertCurrencyToJson(currencyResponseDTO));
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(JsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.print(JsonBuilder.buildJsonMessage("Валюта не найдена"));
        } finally {
            writer.flush();
        }
    }
}