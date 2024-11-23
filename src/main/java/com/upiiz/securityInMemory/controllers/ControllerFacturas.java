package com.upiiz.securityInMemory.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/")
public class ControllerFacturas {
    @GetMapping("/listar")
    public String listar(){
        return "Lista de facturas";
    }

    @GetMapping("/actualizar")
    public String actualizar(){
        return "actualizar facturas";
    }

    @GetMapping("/eliminar")
    public String eliminar(){
        return "eliminar facturas";
    }

    @GetMapping("/crear")
    public String crear(){
        return "Crear facturas";
    }
}
