package com.mahathi.contractor.controller;

import com.mahathi.contractor.entity.ContactSubmission;
import com.mahathi.contractor.model.ApiResponse;
import com.mahathi.contractor.model.ContactForm;
import com.mahathi.contractor.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse> submitContactForm(@Valid @RequestBody ContactForm form) {
        try {
            ContactSubmission submitted = contactService.submitContactForm(form);
            return ResponseEntity.ok(ApiResponse.success(
                    "Thank you for your inquiry! We will contact you within 24 hours.",
                    submitted));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to submit form. Please try again later."));
        }
    }

    @GetMapping("/submissions")
    public ResponseEntity<ApiResponse> getAllSubmissions() {
        List<ContactSubmission> submissions = contactService.getAllSubmissions();
        return ResponseEntity.ok(ApiResponse.success("Submissions retrieved successfully", submissions));
    }

    @GetMapping("/submissions/unread")
    public ResponseEntity<ApiResponse> getUnreadSubmissions() {
        List<ContactSubmission> submissions = contactService.getUnreadSubmissions();
        return ResponseEntity.ok(ApiResponse.success("Unread submissions retrieved", submissions));
    }

    @GetMapping("/submissions/count")
    public ResponseEntity<ApiResponse> getSubmissionCount() {
        Map<String, Object> counts = Map.of(
            "total", contactService.getSubmissionCount(),
            "unread", contactService.getUnreadCount()
        );
        return ResponseEntity.ok(ApiResponse.success("Count retrieved", counts));
    }

    @PutMapping("/submissions/{id}/read")
    public ResponseEntity<ApiResponse> markAsRead(@PathVariable Long id) {
        contactService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Marked as read"));
    }

    @DeleteMapping("/submissions/{id}")
    public ResponseEntity<ApiResponse> deleteSubmission(@PathVariable Long id) {
        contactService.deleteSubmission(id);
        return ResponseEntity.ok(ApiResponse.success("Submission deleted"));
    }

    @DeleteMapping("/submissions")
    public ResponseEntity<ApiResponse> clearAllSubmissions() {
        contactService.clearAllSubmissions();
        return ResponseEntity.ok(ApiResponse.success("All submissions cleared"));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Server is running",
                Map.of(
                        "service", "Mahathi Building Contractors API",
                        "version", "1.0.0",
                        "status", "active",
                        "database", "MySQL Connected"
                )));
    }
}
