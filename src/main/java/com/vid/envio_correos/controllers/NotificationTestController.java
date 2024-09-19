package com.vid.envio_correos.controllers;

import com.vid.envio_correos.models.Empleado;
import com.vid.envio_correos.models.Jefe;
import com.vid.envio_correos.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class NotificationTestController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/date")
    public List<Empleado> getEmpleadoFilterDay() {
        return notificationService.filterDate();
    }


    @GetMapping("/empleado")
    public List<Empleado> getEmpleado() {
        return notificationService.getAll();
    }


    @GetMapping("/jefe")
    public List<Jefe> getJefe() {
        return notificationService.getAllJefe();
    }

    @GetMapping("/total")
    public String LonggetTotal() {
        return "La totalidad de empleados es: " +  notificationService.countEmpleados();
    }


    @GetMapping("/total-jefes")
    public String LonggetTotalJefes() {
        return "La totalidad de jefes es: " +  notificationService.countJefes();
    }

    @GetMapping("/notify")
    public String triggerNotification() {
        notificationService.notifyDueInvoices();
        return "Se han enviado los correos de manera correcta";
    }

    @GetMapping("/by-code-jefe")
    public Jefe byCodeJefe() {
        return notificationService.findByCodJefe("43607886");
    }

    @PutMapping("/update")
    public String createBoss() {
        notificationService.notifyDueInvoices();
        return "Notification triggered!";
    }
}
