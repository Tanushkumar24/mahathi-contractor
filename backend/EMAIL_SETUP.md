# Email Setup Guide for Mahathi Building Contractors

## Current Status
**Email is currently DISABLED** - Form submissions are saved in memory and logged to console.

To enable email notifications, follow the steps below.

---

## Option 1: Enable Email (Recommended)

### Step 1: Set Up Gmail App Password

1. **Enable 2-Factor Authentication on your Google Account**
   - Go to: https://myaccount.google.com/security
   - Find "2-Step Verification" and enable it

2. **Generate an App Password**
   - Go to: https://myaccount.google.com/apppasswords
   - If you don't see this option, make sure 2FA is enabled first
   - Select "Mail" as the app
   - Select "Other (Custom name)" and type "Mahathi Website"
   - Click Generate
   - Copy the **16-character password** (looks like: `abcd efgh ijkl mnop`)

3. **Update application.properties**
   ```
   spring.mail.username=mahathicontractor@gmail.com
   spring.mail.password=YOUR_APP_PASSWORD_HERE
   mahathi.email.enabled=true
   ```

4. **Restart the server**

### Step 2: Test the Setup

1. Open the website
2. Fill out the contact form
3. Submit - you should see:
   - A confirmation message on the website
   - An email in your Gmail inbox (mahathicontractor@gmail.com)
   - A confirmation email sent to the customer's email

---

## Option 2: Keep Email Disabled

If you don't want to set up email right now:

**Current Behavior:**
- Form submissions are saved in memory
- When someone submits the form, the details are printed to the console/terminal
- You can view all submissions by calling: `GET http://localhost:8080/api/submissions`

**To view submissions via API:**
```bash
curl http://localhost:8080/api/submissions
```

This will return all form submissions in JSON format.

---

## Troubleshooting

### Error: "Authentication failed"
- Your app password is incorrect
- Make sure you're using the **16-character app password**, not your actual Gmail password

### Error: "Less secure app access"
- Gmail no longer supports "less secure apps"
- You MUST use an App Password for Gmail to work

### Not receiving confirmation emails to customers
- The customer's email might be incorrect
- Check the server logs for email sending errors

---

## Alternative: Use Your Own Email Provider

If you want to use a different email provider:

### Outlook/Office 365
```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=your-email@outlook.com
spring.mail.password=your-password
```

### Yahoo Mail
```properties
spring.mail.host=smtp.mail.yahoo.com
spring.mail.port=587
spring.mail.username=your-email@yahoo.com
spring.mail.password=your-app-password
```

### Custom SMTP Server
```properties
spring.mail.host=your-smtp-server.com
spring.mail.port=587
spring.mail.username=your-username
spring.mail.password=your-password
```

---

## Production Recommendations

For a production website, consider:

1. **Use a professional email service:**
   - SendGrid (free tier available)
   - Amazon SES
   - Mailgun
   - SendinBlue

2. **Add these services to your Spring Boot project:**
```xml
<!-- SendGrid Example -->
<dependency>
    <groupId>com.sendgrid</groupId>
    <artifactId>sendgrid-java</artifactId>
    <version>4.7.1</version>
</dependency>
```

3. **Never commit real passwords to Git** - use environment variables:
```properties
spring.mail.password=${MAIL_PASSWORD}
```
