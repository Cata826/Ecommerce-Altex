package Store.Altex.services;

import Store.Altex.email.EmailSender;
import Store.Altex.email.EmailValidator;
import Store.Altex.models.Order;
import Store.Altex.models.Product;
import Store.Altex.models.User;
import Store.Altex.repositories.OrderRepository;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.UserRepository;
import com.itextpdf.layout.element.AreaBreak;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import static javax.mail.Transport.send;


@Service
@AllArgsConstructor
public class OrderService {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;

    @Autowired
    private final EmailSender emailSender;
    @Autowired
    private final EmailValidator emailValidator;
    @Autowired
    private OrderRepository orderRepository;


    public Optional<User> getUser(Long userId)
    {
        return orderRepository.findUserByUserId(userId);
    }
    public byte[] createProductPdf(Product product, int quantity) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Product Details"));
            document.add(new Paragraph("Name: " + product.getName()));
            document.add(new Paragraph("Description: " + product.getShort_description()));
            document.add(new Paragraph("Price: " + product.getPret()));
            document.add(new Paragraph("Quantity: " + quantity));

            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create PDF for product", e);
        }
    }

//    public byte[] createProductPdf(Product product) {
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
//            PdfWriter writer = new PdfWriter(byteArrayOutputStream);
//            PdfDocument pdf = new PdfDocument(writer);
//            Document document = new Document(pdf);
//
//            document.add(new Paragraph("Product Details"));
//            document.add(new Paragraph("Name: " + product.getName()));
//            document.add(new Paragraph("Description: " + product.getShort_description()));
//            document.add(new Paragraph("Price: " + product.getPret()));
//
//            document.close();
//            return byteArrayOutputStream.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create PDF for product", e);
//        }
//    }

    public List<Order> getAllWishlists() {
        return orderRepository.findAllWithUserAndProduct();
    }

    public Order addToWishlist(Long userId, Long productId)  {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);

        return orderRepository.save(order);
    }

    public List<Product> getProductsForUserOrder(Long userId) {
        return orderRepository.findProductsByUserId(userId);
    }
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    public void removeFromOrder(Long order) {
        orderRepository.deleteById(order);
    }


//    private void sendOrderConfirmationEmail(String emailAddress, Product product) {
//        if (!emailValidator.test(emailAddress)) {
//            throw new IllegalStateException("Email is not valid");
//        }
//        byte[] pdfContent = createProductPdf(product);
//
//        emailService.sendEmailWithPdf(emailAddress, "Order Confirmation",
//                "Please find attached your order confirmation.",
//                "OrderConfirmation.pdf",
//                pdfContent);
//    }

//    public void sendOrderConfirmationEmail(String emailAddress, Map<Product, Integer> products) {
//        if (!emailValidator.test(emailAddress)) {
//            throw new IllegalStateException("Email is not valid");
//        }
//
//        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
//            Product product = entry.getKey();
//            Integer quantity = entry.getValue();
//
//            byte[] pdfContent = createProductPdf(product, quantity);
//
//            emailService.sendEmailWithPdf(emailAddress, "Order Confirmation",
//                    "Please find attached your order confirmation for " + product.getName() + " (Quantity: " + quantity + ")",
//                    "OrderConfirmation_" + product.getName() + ".pdf",
//                    pdfContent);
//        }
//    }
public void sendOrderConfirmationEmail(String emailAddress, Map<Product, Integer> products) {
    if (!emailValidator.test(emailAddress)) {
        throw new IllegalStateException("Email is not valid");
    }

    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        double totalCost = 0.0;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            // Calculate the total cost for this product
            double productTotal = product.getPret() * quantity;

            // Add product details to the PDF
            document.add(new Paragraph("Product Details"));
            document.add(new Paragraph("Name: " + product.getName()));
            document.add(new Paragraph("Description: " + product.getShort_description()));
            document.add(new Paragraph("Price: " + product.getPret()));
            document.add(new Paragraph("Quantity: " + quantity));
            document.add(new Paragraph("Total: " + productTotal));
            document.add(new AreaBreak());

            // Add the product total to the overall total cost
            totalCost += productTotal;
        }

        // Format total cost to two decimal places
        String formattedTotalCost = String.format("%.2f", totalCost);

        // Add total cost to the PDF
        document.add(new Paragraph("Total Cost: " + formattedTotalCost));
        // Add total cost to the PDF
//        document.add(new Paragraph("Total Cost: " + totalCost));

        document.close();
        byte[] pdfContent = byteArrayOutputStream.toByteArray();

        // Send email with the generated PDF containing details of all products
        emailService.sendEmailWithPdf(emailAddress, "Order Confirmation",
                "Please find attached your order confirmation.",
                "OrderConfirmation.pdf",
                pdfContent);

    } catch (Exception e) {
        throw new RuntimeException("Failed to create PDF for products", e);
    }
}


    public List<Order> getAllOrderItems() {
        return orderRepository.findAll();
    }




}