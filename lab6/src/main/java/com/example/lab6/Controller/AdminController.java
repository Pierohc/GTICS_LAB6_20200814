package com.example.lab6.Controller;

import com.example.lab6.Entity.Dispositivo;
import com.example.lab6.Repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class AdminController {




    @Autowired
    DispositivoRepository dispositivoRepository;

    @GetMapping(value="/admin")
    public String admin(Model model){

        List<Dispositivo> lista = dispositivoRepository.findAll();
        model.addAttribute("lista", lista);

        return "admin";
    }

}
