package br.com.gahantognoli.front_gestao_vagas.modules.company.controllers;

import br.com.gahantognoli.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.gahantognoli.front_gestao_vagas.modules.company.service.CreateCompanyService;
import br.com.gahantognoli.front_gestao_vagas.utils.FormatErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyService createCompanyService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("company", new CreateCompanyDTO());
        return "company/create";
    }

    @PostMapping("/create")
    public String save(CreateCompanyDTO createCompanyDTO, Model model) {
        try {
            this.createCompanyService.execute(createCompanyDTO);
        }
        catch (HttpClientErrorException e){
            model.addAttribute("error_message",
                FormatErrorMessage.formatErrorMessage(e.getResponseBodyAsString()));
        }

        model.addAttribute("company", createCompanyDTO);
        return "company/create";
    }
}
