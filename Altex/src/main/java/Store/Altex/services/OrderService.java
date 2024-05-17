package Store.Altex.services;

import Store.Altex.email.EmailValidator;
import Store.Altex.models.Order;
import Store.Altex.models.Product;
import Store.Altex.models.User;
import Store.Altex.repositories.OrderRepository;
import Store.Altex.repositories.ProductRepository;
import Store.Altex.repositories.UserRepository;
import com.itextpdf.layout.element.AreaBreak;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;


@Service
@AllArgsConstructor
public class OrderService {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final EmailService emailService;
    private final EmailValidator emailValidator;
    private OrderRepository orderRepository;


    public Optional<User> getUser(Long userId)
    {
        return orderRepository.findUserByUserId(userId);
    }

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

                double productTotal = product.getPret() * quantity;

                // Add product details to the PDF
                document.add(new Paragraph("Product Details"));
                document.add(new Paragraph("Name: " + product.getName()));
                document.add(new Paragraph("Description: " + product.getShort_description()));
                document.add(new Paragraph("Price: " + product.getPret()));
                document.add(new Paragraph("Quantity: " + quantity));
                document.add(new Paragraph("Total: " + productTotal));
                document.add(new AreaBreak());


                totalCost += productTotal;
            }


            String formattedTotalCost = String.format("%.2f", totalCost);


            document.add(new Paragraph("Total Cost: " + formattedTotalCost));

            document.close();
            byte[] pdfContent = byteArrayOutputStream.toByteArray();


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