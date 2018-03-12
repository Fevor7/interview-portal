package by.interview.portal.controller;

import by.interview.portal.dto.DisciplineDTO;
import by.interview.portal.dto.DisciplineWithHeadsDTO;
import by.interview.portal.dto.JwtUserDTO;
import by.interview.portal.facade.DisciplineFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.interview.portal.dto.DisciplineDTO;
import by.interview.portal.dto.DisciplineWithHeadsDTO;
import by.interview.portal.facade.DisciplineFacade;

@CrossOrigin
@RestController
@RequestMapping(value = "/discipline")
public class DisciplineController {

    @Autowired
    private DisciplineFacade disciplineFacade;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public DisciplineWithHeadsDTO findById(@PathVariable Long id) {
        return disciplineFacade.findById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<DisciplineDTO> findAll() {
        return disciplineFacade.findByParentId(null);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/parents/{id}")
    public List<DisciplineDTO> findSubItems(@PathVariable Long id) {
        return disciplineFacade.findByParentId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping
    public void save(@RequestBody DisciplineWithHeadsDTO disciplineDTO) {
        disciplineFacade.save(disciplineDTO);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/user")
    public List<DisciplineDTO> findDisciplinesForUser() {
        return disciplineFacade.findDisciplinesByUser(getCurrentUsersUsername());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{id}")
    public void deleteDiscipline(@PathVariable Long id) {
        disciplineFacade.deleteDiscipline(id);
    }

    public String getCurrentUsersUsername() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
    }
}
