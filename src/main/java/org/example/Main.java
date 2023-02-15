package org.example;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Читаем файл
        String json = readString("data.json");

        // Получаем список сотрудников, еречисленных в файле
        List<Employee> list = jsonToList(json);

        // Выводим каждый элемент из списка в консоль
        for (Employee employee : list) {
            System.out.println(employee);
        }
    }


    private static String readString(String filePath) {
        // Будем читать построчно. Значит храним строку
        String s;
        // Множество строк будем собирать в одну через конструктор
        StringBuilder stringBuilder = new StringBuilder();
        // Будем буферизовать и читать иэ буфера
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath)))) {
            // Чтение построчно, пока строки не кончатся
            while ((s = br.readLine()) != null) {
                // Строку добавляем в конструктор
                stringBuilder.append(s);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return stringBuilder.toString();
    }

    private static List jsonToList (String jsonText) {
        // Инициируем парсер json
        JSONParser parser = new JSONParser();
        // Делаем список сотрудников, который будем наполнять
        List<Employee> employees = new ArrayList<>();
        try {
            // Делаем массив из данных json
            JSONArray array = (JSONArray) parser.parse(jsonText);
            // Пройдем по элементам этого массива
            for (Object jEl : array) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Employee employee = gson.fromJson(jEl.toString(), Employee.class);
                employees.add(employee);
            }
        } catch (ParseException ex) {
            ex.getMessage();
        }
        return employees;
    }
}