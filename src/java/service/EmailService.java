package service;


public class EmailService {
    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        // In a real application, implement actual email sending here
        System.out.println("=== EMAIL SENT ===");
        System.out.println("To: " + toEmail);
        System.out.println("Subject: Password Reset Request");
        System.out.println("Content:");
        System.out.println("Please click the following link to reset your password:");
        System.out.println(resetLink);
        System.out.println("This link will expire in 24 hours.");
        System.out.println("==================");
    }
}