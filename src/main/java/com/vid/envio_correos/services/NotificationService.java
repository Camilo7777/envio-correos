package com.vid.envio_correos.services;

import com.vid.envio_correos.models.Empleado;
import com.vid.envio_correos.models.Jefe;
import com.vid.envio_correos.repositories.EmpleadoRepository;
import com.vid.envio_correos.repositories2.JefeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Autowired
    private JefeRepository jefeRepository;
    @Autowired
    private JavaMailSender mailSender;

    //@Scheduled(cron = "0 0 9 * * MON") // Ejecutar cada lunes a las 9 AM
    //@Scheduled(cron = "0 */2 * * * *")
    public void notifyDueInvoices() {

        System.out.println(LocalDate.now().plusDays(2));

        List<Empleado> empleadoList = empleadoRepository.findEmpleadoByFechaFinContrato(LocalDate.now().plusDays(2));


        if (!empleadoList.isEmpty()) {
            for (Empleado empleado : empleadoList) {
                String codJefe = empleado.getJefeCodigo();
                Jefe jefe = jefeRepository.findJefeByCodJefe(codJefe);

                // Validación para verificar si el jefe existe
                if (jefe != null) {
                    String nameJefe = jefe.getNombresJefe() + " " + jefe.getApellidosJefe();
                    String emailJefe = jefe.getCorreoJefe();
                    String nameEmpleado = empleado.getNombre() + " " + empleado.getApellido1();
                    LocalDate date = empleado.getFechaFinContrato();

                    String subject = "Notificación de contratos por vencer";
                    String text = "Señor " + nameJefe + ", el contrato de: " + nameEmpleado + " vence el " + date;

                    System.out.println(text);

                    sendEmail(emailJefe, subject, text);
                } else {
                    // Opcional: Manejar el caso en que el jefe no existe
                    System.out.println("El jefe con código " + codJefe + " no se encontró.");
                }
            }
        }
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public List<Empleado> getAll() {
        return empleadoRepository.findAll();
    }


    public List<Jefe> getAllJefe() {
        return jefeRepository.findAll();
    }

    public Long countEmpleados(){
        return   empleadoRepository.count();
    }

    public Jefe findByCodJefe (String codJefe){
        return   jefeRepository.findJefeByCodJefe(codJefe);
    }

    public Long countJefes(){
        return   jefeRepository.count();
    }


    public List<Empleado> filterDate(){
        return   empleadoRepository.findEmpleadoByFechaFinContrato(LocalDate.now().plusDays(1));
    }
}
