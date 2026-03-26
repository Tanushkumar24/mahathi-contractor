package com.mahathi.contractor.service;

import com.mahathi.contractor.entity.ContactSubmission;
import com.mahathi.contractor.model.ContactForm;
import com.mahathi.contractor.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@mahathicontractor.com}")
    private String fromEmail;

    @Value("${mahathi.admin.email:mahathicontractor@gmail.com}")
    private String adminEmail;

    @Value("${mahathi.email.enabled:false}")
    private boolean emailEnabled;

    @Transactional
    public ContactSubmission submitContactForm(ContactForm form) {
        ContactSubmission submission = new ContactSubmission();
        submission.setName(form.getName());
        submission.setEmail(form.getEmail());
        submission.setPhone(form.getPhone());
        submission.setService(form.getService());
        submission.setLocation(form.getLocation());
        submission.setMessage(form.getMessage());
        
        ContactSubmission saved = contactRepository.save(submission);
        
        System.out.println("\n==========================================");
        System.out.println("NEW CONTACT FORM SUBMISSION (SAVED TO DB)");
        System.out.println("==========================================");
        System.out.println("Name: " + saved.getName());
        System.out.println("Email: " + saved.getEmail());
        System.out.println("Phone: " + saved.getPhone());
        System.out.println("Service: " + (saved.getService() != null ? saved.getService() : "Not specified"));
        System.out.println("Location: " + (saved.getLocation() != null ? saved.getLocation() : "Not specified"));
        System.out.println("Message: " + saved.getMessage());
        System.out.println("Time: " + saved.getSubmittedAt());
        System.out.println("Database ID: " + saved.getId());
        System.out.println("==========================================\n");
        
        if (emailEnabled) {
            try {
                sendEmailToAdmin(saved);
                sendConfirmationEmail(saved);
                System.out.println("Email sent successfully!");
            } catch (Exception e) {
                System.out.println("Email Error: " + e.getMessage());
                System.out.println("Note: Form saved to database successfully.");
            }
        } else {
            System.out.println("Email sending is disabled. Form saved to database.");
        }
        
        return saved;
    }

    private void sendEmailToAdmin(ContactSubmission form) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(adminEmail);
        message.setSubject("New Contact - Mahathi Building Contractors [ID:" + form.getId() + "]");
        message.setText(buildAdminEmailBody(form));
        mailSender.send(message);
    }

    private void sendConfirmationEmail(ContactSubmission form) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(form.getEmail());
        message.setSubject("Thank You - Mahathi Building Contractors");
        message.setText(buildConfirmationEmailBody(form));
        mailSender.send(message);
    }

    private String buildAdminEmailBody(ContactSubmission form) {
        return "NEW CONTACT FORM SUBMISSION\n\n" +
               "ID: " + form.getId() + "\n" +
               "Name: " + form.getName() + "\n" +
               "Email: " + form.getEmail() + "\n" +
               "Phone: " + form.getPhone() + "\n" +
               "Service: " + (form.getService() != null ? form.getService() : "Not specified") + "\n" +
               "Location: " + (form.getLocation() != null ? form.getLocation() : "Not specified") + "\n" +
               "Message: " + form.getMessage() + "\n\n" +
               "Submitted: " + form.getSubmittedAt();
    }

    private String buildConfirmationEmailBody(ContactSubmission form) {
        return "Dear " + form.getName() + ",\n\n" +
               "Thank you for contacting Mahathi Building Contractors!\n\n" +
               "We have received your inquiry and will get back to you within 24 hours.\n\n" +
               "Contact: 8688074469 | mahathicontractor@gmail.com\n\n" +
               "Regards,\nMahathi Building Contractors\n" +
               "\"Under Construction Today. Your Dream Home Tomorrow.\"";
    }

    public List<ContactSubmission> getAllSubmissions() {
        return contactRepository.findAllByOrderBySubmittedAtDesc();
    }

    public List<ContactSubmission> getUnreadSubmissions() {
        return contactRepository.findByIsReadFalseOrderBySubmittedAtDesc();
    }

    public long getSubmissionCount() {
        return contactRepository.count();
    }

    public long getUnreadCount() {
        return contactRepository.countByIsReadFalse();
    }

    @Transactional
    public void markAsRead(Long id) {
        contactRepository.findById(id).ifPresent(submission -> {
            submission.setRead(true);
            contactRepository.save(submission);
        });
    }

    @Transactional
    public void deleteSubmission(Long id) {
        contactRepository.deleteById(id);
    }

    @Transactional
    public void clearAllSubmissions() {
        contactRepository.deleteAll();
    }
}
