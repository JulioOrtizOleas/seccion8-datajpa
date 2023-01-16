package com.cursoudemy.springboot.app.controller;

import com.cursoudemy.springboot.app.models.dao.IClienteDao;
import com.cursoudemy.springboot.app.models.entity.Cliente;
import com.cursoudemy.springboot.app.models.services.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Map;

@Controller
// para guardar el objeto del formulario de manera persistente.
@SessionAttributes(value = "cliente")
public class ClienteController {

    @Autowired
    // @Qualifier indica el nombre del componente para evitar ambiguedad y seleccionar el bean concreto, para ellos se informa el nombre de la interfaz, y además se indica en la etiqueta repository de la clase ClienteDaoImpl
    //@Qualifier ("clienteDaoJPA")
    private IClienteService clienteService;

    // Indicamos la ruta que vamos a acceder desde el host
    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clienteService.findAll());
        return "listar";
    }
    @RequestMapping(value = "/form")
    public String crear(Map<String,Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente",cliente);
        model.put("titulo","Formulario de Cliente");
        return "form";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable (value="id") Long id,Map<String,Object> model){

        Cliente cliente = null;
        if (id>0){
            cliente=clienteService.findOne(id);
        }else{
            return "redirect:/listar";
        }
        model.put("cliente",cliente);
        model.put("titulo","Editar Cliente");
        return "form";
    }
    // IMPORTANTE ==>
    @RequestMapping(value = "/form",method = RequestMethod.POST)
    // @Valid para validar el cliente que se recibe en el formulario , importante tener en cuenta que siempre tienen que ir en este orden (@Valid Cliente cliente, BindingResult result)
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status){
        //Mostrar al usuario el mensaje de error utilizando BindingResult, y lo validamos.
        if(result.hasErrors()){
            //retornamos la vista con el error es decir el /form del metodo crear, en este ejemplo no le pasamos el cliente ya que lo hace automático
            //en el caso que no sea automático y el nombre Cliente de la clase guardar sea diferente al cliente de la vista del metodo crear ".put" , utilizamos la anotación @ModelAttribute("NOMBRE DE LA VISTA, LO QUE ESTA en el model.put del metodo crear")
            model.addAttribute("titulo", "Formulario de Cliente");
            return"form";
        }

        clienteService.save(cliente);
        //en este punto se cierra el objeto que activamos de forma persistente en el @SessionAttributes(value = "cliente")
        status.setComplete();
        return "redirect:listar";
    }
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable (value="id") Long id){
        if(id>0){
            clienteService.delete(id);
        }
        return "redirect:/listar";
    }
}
