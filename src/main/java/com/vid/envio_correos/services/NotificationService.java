package com.vid.envio_correos.services;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.vid.envio_correos.models.Empleado;
import com.vid.envio_correos.models.Jefe;
import com.vid.envio_correos.repositories.EmpleadoRepository;
import com.vid.envio_correos.repositories2.JefeRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import java.io.ByteArrayOutputStream;

import org.springframework.mail.javamail.MimeMessageHelper;


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

        System.out.println(LocalDate.now());

        List<Empleado> empleadoList = empleadoRepository.findEmpleadoByFechaFinContrato(LocalDate.now());


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

                    ByteArrayOutputStream pdfContent = generatePdf("src/main/resources/prueba.pdf", nameEmpleado, date);

                    System.out.println(text);

                    sendEmailWithAttachment(emailJefe, subject, text, pdfContent, "Contrato_" + nameEmpleado + ".pdf");

                    //sendEmail(emailJefe, subject, text);
                } else {
                    // Opcional: Manejar el caso en que el jefe no existe
                    System.out.println("El jefe con código " + codJefe + " no se encontró.");
                }
            }
        }
    }

    private ByteArrayOutputStream generatePdf(String templatePath, String name, LocalDate expiryDate) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfReader reader = new PdfReader(templatePath);
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);

            PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(1));
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            canvas.beginText();
            canvas.setFontAndSize(font, 12);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
            String formattedDate = LocalDate.now().format(formatter);

            // Establecer la posición del texto (ajusta las coordenadas según sea necesario)
            canvas.moveText(100, 700); // (x, y) en puntos
            canvas.showText(" Fecha actual: Medellín, " + formattedDate);
            canvas.endText(); // Termina el primer bloque de texto

            // Reiniciar la posición del texto para el siguiente texto
            canvas.beginText();

            // Establecer la posición del texto (ajusta las coordenadas según sea necesario)
            canvas.moveText(100, 500); // (x, y) en puntos
            canvas.showText("Usuario: " + name);
            canvas.endText();

            // Reiniciar la posición del texto para el siguiente texto
            canvas.beginText();

            // Establecer la posición del texto (ajusta las coordenadas según sea necesario)
            canvas.moveText(100, 400); // (x, y) en puntos
            canvas.showText("Fecha de vencimiento: " + expiryDate);
            canvas.endText();

            pdfDoc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    private void sendEmailWithAttachment(String to, String subject, String text, ByteArrayOutputStream pdfContent, String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            // Adjuntar el PDF
            helper.addAttachment(attachmentName, new ByteArrayDataSource(pdfContent.toByteArray(), "application/pdf"));

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
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
