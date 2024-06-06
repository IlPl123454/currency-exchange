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
    JsonBuilder jsonBuilder = new JsonBuilder();
    CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        String currencyCode = req.getPathInfo();
        if (currencyCode == null || currencyCode.length() != 4) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.print(jsonBuilder.buildJsonMessage("Вы ввели недопустимый код валюты. Код валюты должен содержать 3 символа"));
            return;
        } else {
            currencyCode = currencyCode.substring(1);
        }


        resp.setContentType("application/json");

        try {
            CurrencyResponseDTO currencyResponseDTO = currencyService.getCurrencyByCode(currencyCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(jsonBuilder.convertCurrencyToJson(currencyResponseDTO));
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(jsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.print(jsonBuilder.buildJsonMessage("Валюта не найдена"));
        } finally {
            writer.flush();
        }
    }
}