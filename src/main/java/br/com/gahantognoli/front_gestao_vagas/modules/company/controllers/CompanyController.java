package br.com.gahantognoli.front_gestao_vagas.modules.company.controllers;

import br.com.gahantognoli.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.gahantognoli.front_gestao_vagas.modules.company.dto.CreateJobsDTO;
import br.com.gahantognoli.front_gestao_vagas.modules.company.service.CreateCompanyService;
import br.com.gahantognoli.front_gestao_vagas.modules.company.service.CreateJobService;
import br.com.gahantognoli.front_gestao_vagas.modules.company.service.ListAllJobsCompanyService;
import br.com.gahantognoli.front_gestao_vagas.modules.company.service.LoginCompanyService;
import br.com.gahantognoli.front_gestao_vagas.utils.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyService createCompanyService;

    @Autowired
    private LoginCompanyService loginCompanyService;

    @Autowired
    private CreateJobService createJobService;

    @Autowired
    private ListAllJobsCompanyService listAllJobsCompanyService;

    @GetMapping("/login")
    public String login() {
        return "company/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {
        try {
            var token = this.loginCompanyService.execute(username, password);

            var grants = token
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                null,
                null,
                new HashSet<GrantedAuthority>(grants)
            );
            auth.setDetails(token.getAccess_token());
            var securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", token);

            return "redirect:/company/jobs";
        } catch (HttpClientErrorException ex) {
            redirectAttributes.addFlashAttribute("error_message", "Usuário ou senha inválidos");
            return "redirect:/company/login";
        }
    }

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

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String jobs(Model model) {
        model.addAttribute("jobs", new CreateJobsDTO());
        return "company/jobs";
    }

    @PostMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String createJobs(CreateJobsDTO createJobsDTO) {
        var result = this.createJobService.execute(getToken(), createJobsDTO);
        return "redirect:/company/jobs/list";
    }

    @GetMapping("/jobs/list")
    @PreAuthorize("hasRole('COMPANY')")
    public String listJobs(Model model) {
        var result = this.listAllJobsCompanyService.execute(getToken());
        model.addAttribute("jobs", result);
        return "company/list";
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('COMPANY')")
    public String logout(HttpSession session) {
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        session.setAttribute("token", null);

        return "redirect:/company/login";
    }

    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }
}
