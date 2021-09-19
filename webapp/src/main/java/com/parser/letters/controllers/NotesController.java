package com.parser.letters.controllers;

import java.util.*;
import java.util.stream.Collectors;

import com.parser.letters.models.Notes;
import com.parser.letters.models.User;
import com.parser.letters.models.UrlParser;
import com.parser.letters.models.classes4Hibernate.Email2;
import com.parser.letters.repositories.EmailRepository;
import com.parser.letters.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.parser.letters.repositories.NotesRepository;


@Controller
public class NotesController {


    private UrlParser parser = new UrlParser();

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private NotesRepository noteRepository;
    @Autowired
    private EmailRepository emailRepository;

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public ModelAndView notes() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("notes", noteRepository.findAll());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("notes");
        List<Email2> emails2 = parser.getEmails();
        List<String> result = emails2.stream().map(Email2::getAddress).collect(Collectors.toList());
        modelAndView.addObject("emails2", result);
        return modelAndView;
    }
    @RequestMapping("/notes/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("create");
        return modelAndView;
    }
    @RequestMapping("/notes/save")
    public String save(@RequestParam String title, @RequestParam String content) {
        Notes note = new Notes();
        note.setTitle(title);
        note.setContent(content);
        note.setUpdated(new Date());
        noteRepository.save(note);

        return "redirect:/notes/show/" + note.getId();
    }
    @RequestMapping("/notes/show/{id}")
    public ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("note", noteRepository.findById(id).orElse(null));
        modelAndView.setViewName("show");
        return modelAndView;
    }

    @RequestMapping("/notes/delete")
    public String delete(@RequestParam Long id) {
        Notes note = noteRepository.findById(id).orElse(null);
        noteRepository.delete(note);

        return "redirect:/notes";
    }
    @RequestMapping("/notes/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("note", noteRepository.findById(id).orElse(null));
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @RequestMapping("/notes/update")
    public String update(@RequestParam Long id, @RequestParam String title, @RequestParam String content) {
        Notes note = noteRepository.findById(id).orElse(null);
        note.setTitle(title);
        note.setContent(content);
        note.setUpdated(new Date());
        noteRepository.save(note);

        return "redirect:/notes/show/" + note.getId();

    }


    @PostMapping("/notes")
    public ModelAndView startParse(@RequestParam String url, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("notes", noteRepository.findAll());
        Email2 email2 = new Email2(url);
        parser.startParse(url);
        List<Email2> emails2 = parser.getEmails();
        List<String> result = emails2.stream().map(Email2::getAddress).collect(Collectors.toList());
        model.addAttribute("emails2", result);
        return modelAndView;

    }

    @PostMapping("/notes/sendemails")

    public String sendMail(@RequestParam Long id, @RequestParam String title, @RequestParam String content, String pass) {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("notes", noteRepository.findAll());

        List<Email2> emails2 = parser.getEmails();
        List<String> result = emails2.stream().map(Email2::getAddress).collect(Collectors.toList());
        modelAndView.addObject("emails2", result);


        Notes note = noteRepository.findById(id).orElse(null);
        note.setId(id);
        note.setTitle(title);
        note.setContent(content);
        pass = "PASSWORD_FOR_SMTP";
        parser.sendMail(content, title, pass);
        return "redirect:/notes";
    }

}

