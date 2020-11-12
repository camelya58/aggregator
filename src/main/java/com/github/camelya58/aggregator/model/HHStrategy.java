package com.github.camelya58.aggregator.model;

import com.github.camelya58.aggregator.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class HHStrategy
 *
 * @author Kamila Meshcheryakova
 * created by 10.11.2020
 */
public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        int page = 0;
        while (true) {
            try {
                Document document = getDocument(searchString, page);
                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) {
                    break;
                }
                elements.forEach(element -> {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text().trim());
                    Elements el = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation");
                    vacancy.setSalary(el == null ? "" : el.text().trim());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text().trim());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text().trim());
                    vacancy.setSiteName(URL_FORMAT);
                    vacancy.setUrl(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-title").attr("href").trim());
                    vacancies.add(vacancy);
                });
                page++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        Document document = Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/84.0.4147.89 Safari/537.36")
                .referrer("no-referrer-when-downgrade").get();
        document.html();
        return document;
    }
}
