package ru.course.b2b.service;

import ru.course.b2b.model.Product;
import ru.course.b2b.model.TechnicalTask;
import ru.course.b2b.model.Template;

public class TechnicalTaskService {

    public TechnicalTask generateTask(
            Template template,
            Product product,
            String region,
            String companyType
    ) {

        String content =
                "ТЕХНИЧЕСКОЕ ЗАДАНИЕ\n\n" +

                        "ШАБЛОН: "
                        + template.getName() + "\n\n" +

                        "1. Цель исследования\n" +
                        template.getGoal() + "\n\n" +

                        "2. Требования\n" +
                        template.getRequirements() + "\n\n" +

                        "3. Параметры анализа\n" +

                        "Продукция: "
                        + product.getName() + "\n" +

                        "Категория рынка: "
                        + product.getCategory() + "\n" +

                        "Регион исследования: "
                        + region + "\n" +

                        "Тип компаний: "
                        + companyType + "\n\n" +

                        "4. Коды ОКВЭД\n" +
                        product.getOkved() + "\n\n" +

                        "5. Ключевые слова\n" +
                        product.getKeywords() + "\n\n" +

                        "6. Ожидаемый результат\n" +
                        template.getExpectedResult();

        return new TechnicalTask(
                "ТЗ по продукту " + product.getName(),
                content
        );
    }
}