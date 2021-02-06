package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.controller.TaskController;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Value("${info.company.name}")
    private String companyName;

    @Value("${info.company.email}")
    private String companyEmail;

    @Value("${info.company.phone}")
    private String companyPhone;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://maciek047.github.io");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", "Best regards");
        context.setVariable("company_details", companyName + "\nEmail: " + companyEmail + "\nPhone: " + companyPhone + "\n");
        context.setVariable("preview_message", "New Trello Card Created...");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String numberOfTasksDailyEmail(String message) {


        List<Task> tasks =taskRepository.findAll();

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://maciek047.github.io");
        context.setVariable("button", "Go to Tasks");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message", "Best regards");
        context.setVariable("company_details", companyName + "\nEmail: " + companyEmail + "\nPhone: " + companyPhone + "\n");
        context.setVariable("preview_message", "You've got "+taskRepository.count() + " tasks for today.");
        context.setVariable("list_tasks", tasks);
        return templateEngine.process("mail/daily-number-of-tasks", context);
    }



}