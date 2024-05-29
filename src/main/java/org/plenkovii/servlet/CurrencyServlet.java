package org.plenkovii.servlet;

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

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    JsonBuilder jsonBuilder = new JsonBuilder();
    CurrencyService currencyService = new CurrencyService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();

        String currencyCode = req.getPathInfo();
        currencyCode = currencyCode.substring(1);

        resp.setContentType("application/json");

        try {
            CurrencyDTO currencyDTO = currencyService.getCurrencyByCode(currencyCode);
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(jsonBuilder.convertCurrencyDTOToJson(currencyDTO));
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