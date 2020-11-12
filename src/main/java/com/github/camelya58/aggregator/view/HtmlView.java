package com.github.camelya58.aggregator.view;


import com.github.camelya58.aggregator.Controller;
import com.github.camelya58.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class HtmlView
 *
 * @author Kamila Meshcheryakova
 * created by 10.11.2020
 */
public class HtmlView implements View {

    Controller controller;
    private final String filePath = "./src/main/java/" +
            this.getClass().getPackage().getName().replace(".", "/") +
            "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        updateFile(getUpdatedFileContent(vacancies));
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        try {
            Document doc = getDocument();
            Element template = doc.getElementsByClass("template").first();
            Element copyTemplate = template.clone();
            copyTemplate.removeAttr("style");
            copyTemplate.removeClass("template");
            doc.select("tr[class=vacancy]").remove().not("tr[class=vacancy template]");

            vacancies.forEach(vacancy -> {
                Element newTemplate = copyTemplate.clone();
                newTemplate.getElementsByClass("city").first().text(vacancy.getCity());
                newTemplate.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                newTemplate.getElementsByClass("salary").first().text(vacancy.getSalary());
                newTemplate.getElementsByTag("a").first().text(vacancy.getTitle())
                .attr("href", vacancy.getUrl());
                template.before(newTemplate.outerHtml());
            });
            return doc.html();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Some exception occurred";
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
    private void updateFile(String fileContent) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(fileContent);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }
}
