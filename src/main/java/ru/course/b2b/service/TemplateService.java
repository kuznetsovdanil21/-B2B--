package ru.course.b2b.service;

import javafx.collections.ObservableList;
import ru.course.b2b.data.TemplateRepository;
import ru.course.b2b.model.Template;

public class TemplateService {

    public ObservableList<Template> getAllTemplates() {
        return TemplateRepository.getTemplates();
    }

    public void addTemplate(
            Template template
    ) {
        TemplateRepository.addTemplate(
                template
        );
    }

    public void updateTemplate(
            Template template
    ) {
        TemplateRepository.updateTemplate(
                template
        );
    }

    public void deleteTemplate(
            Template template
    ) {
        TemplateRepository.deleteTemplate(
                template
        );
    }
}