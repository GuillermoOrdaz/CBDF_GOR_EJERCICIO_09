package com.upiiz.securityInMemory.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/")
public class ControllerFacturas {
    @GetMapping("/listar")
    public String listar(){
        return "Lista de facturas";
    }

    @PutMapping("/actualizar")
    public String actualizar(){
        return "actualizar facturas";
    }

    @DeleteMapping("/eliminar")
    public String eliminar(){
        return "eliminar facturas";
    }

    @PostMapping("/crear")
    public String crear(){
        return "Crear facturas";
    }
}
