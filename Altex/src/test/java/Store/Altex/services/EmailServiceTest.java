//package Store.Altex.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.javamail.MimeMessagePreparator;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class EmailServiceTest {
//
//    @Mock
//    private JavaMailSender mailSender;
//    @Mock
//    private MimeMessage mimeMessage;
//    @Mock
//    private Logger logger;
//
//    @InjectMocks
//    private EmailService emailService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
//    }
//
//    @Test
//    void sendEmailSuccessfully() throws MessagingException {
//        // Given
//        String to = "test@example.com";
//        String emailContent = "<h1>Welcome</h1>";
//
//        doNothing().when(mailSender).send(any(MimeMessage.class));
//
//        // When
//        emailService.send(to, emailContent);
//
//        // Then
//        verify(mailSender, times(1)).send(any(MimeMessage.class));
//        verifyNoInteractions(logger);
//    }
//
//    @Test
//    void sendEmailFailure() throws MessagingException {
//        // Given
//        String to = "test@example.com";
//        String emailContent = "<h1>Welcome</h1>";
//
//        doThrow(new MessagingException("failed to send email")).when(mailSender).send(any(MimeMessage.class));
//
//        // When / Then
//        try {
//            emailService.send(to, emailContent);
//        } catch (IllegalStateException e) {
//            verify(logger).error("failed to send email", e.getCause());
//        }
//    }
//
//    @Test
//    void sendEmailWithPdfSuccessfully() throws MessagingException {
//        // Given
//        String to = "test@example.com";
//        String subject = "Your PDF";
//        String text = "Here's your document";
//        String pdfFileName = "document.pdf";
//        byte[] pdfContent = new byte[10]; // Simulate a PDF file
//
//        doNothing().when(mailSender).send(any(MimeMessage.class));
//
//        // When
//        emailService.sendEmailWithPdf(to, subject, text, pdfFileName, pdfContent);
//
//        // Then
//        verify(mailSender, times(1)).send(any(MimeMessage.class));
//        verify(logger).info("Sending email with PDF to: {}", to);
//        verify(logger).info("PDF file name: {}", pdfFileName);
//        verify(logger).info("PDF content size: {}", pdfContent.length);
//        verify(logger).info("Email sent successfully");
//    }
//
//    @Test
//    void sendEmailWithPdfFailure() throws MessagingException {
//        // Given
//        String to = "test@example.com";
//        String subject = "Your PDF";
//        String text = "Here's your document";
//        String pdfFileName = "document.pdf";
//        byte[] pdfContent = new byte[10]; // Simulate a PDF file
//
//        doThrow(new MessagingException("Failed to send email with PDF attachment")).when(mailSender).send(any(MimeMessage.class));
//
//        // When / Then
//        try {
//            emailService.sendEmailWithPdf(to, subject, text, pdfFileName, pdfContent);
//        } catch (IllegalStateException e) {
//            verify(logger).error("Failed to send email with PDF attachment", e.getCause());
//        }
//    }
//}
package Store.Altex.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;
    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<MimeMessagePreparator> mimeMessagePreparatorCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void sendEmailSuccessfully() throws MessagingException {
        // Given
        String to = "test@example.com";
        String emailContent = "<h1>Welcome</h1>";

        // When
        emailService.send(to, emailContent);

        // Then
        verify(mailSender, times(1)).send(mimeMessage);
    }


    @Test
    void sendEmailWithPdfSuccessfully() throws MessagingException {
        // Given
        String to = "test@example.com";
        String subject = "Your PDF";
        String text = "Here's your document";
        String pdfFileName = "document.pdf";
        byte[] pdfContent = new byte[10]; // Simulate a PDF file

        // When
        emailService.sendEmailWithPdf(to, subject, text, pdfFileName, pdfContent);

        // Then
        verify(mailSender).send(any(MimeMessage.class));
    }


}
