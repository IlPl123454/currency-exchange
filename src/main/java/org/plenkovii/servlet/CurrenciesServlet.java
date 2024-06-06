package org.plenkovii.servlet;

import org.plenkovii.dao.CurrencyDAO;
import org.plenkovii.dto.CurrencyRequestDTO;
import org.plenkovii.dto.CurrencyResponseDTO;
import org.plenkovii.entity.Currency;
import org.plenkovii.exception.EntityExistException;
import org.plenkovii.exception.InvalidParameterexception;
import org.plenkovii.service.CurrencyService;
import org.plenkovii.utils.CurrencyValidator;
import org.plenkovii.utils.JsonBuilder;

import javax.servlet.ServletException;
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
    CurrencyValidator currencyValidator = new CurrencyValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");


        List<CurrencyResponseDTO> currencyResponseDTOList = new ArrayList<>();
        try {
            currencyResponseDTOList = currencyService.getAllCurrencies();
            String jsonAnswer = jsonBuilder.convertCurrencyDTOToJsonArray(currencyResponseDTOList);
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(jsonAnswer);
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(jsonBuilder.buildJsonMessage("Не удалось подключиться к базе данных"));
        } finally {
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String code = req.getParameter("code");
        String fullName = req.getParameter("name");
        String sign = req.getParameter("sign");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        CurrencyRequestDTO currencyRequestDTO = new CurrencyRequestDTO(code, fullName, sign);

        try {
            CurrencyValidator.currencyRequestValidation(currencyRequestDTO);

            Currency currency = currencyDAO.create(currencyRequestDTO);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            writer.write(jsonBuilder.convertCurrencyToJson(currency));
        } catch (InvalidParameterexception e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.print(jsonBuilder.buildJsonMessage(e.getMessage()));
        } catch (EntityExistException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.print(jsonBuilder.buildJsonMessage("Валюта с таким кодом уже существует"));
        } catch (ClassNotFoundException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print(jsonBuilder.buildJsonMessage("Не удалось вставить данные или  подключиться к базе данных"));
        } finally {
            writer.flush();
        }
    }
}
