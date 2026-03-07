package ma.ismagi.orderservice.util;

import java.io.ByteArrayOutputStream;
import java.util.List;
import ma.ismagi.orderservice.entity.Order;
import ma.ismagi.orderservice.entity.OrderLineItem;
import org.openpdf.text.Document;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

@Service
public class InvoiceGenerator {

  public byte[] generateInvoicePdf(Order order, List<OrderLineItem> items) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, out);

    document.open();

    Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    document.add(new Paragraph("SALESMASTER - INVOICE", fontTitle));
    document.add(new Paragraph("Order ID: " + order.getId()));
    document.add(new Paragraph("Date: " + order.getCreatedAt()));
    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(3);
    table.addCell("Product ID");
    table.addCell("Quantity");
    table.addCell("Unit Price");

    for (var item : items) {
      table.addCell(item.getProductId().toString());
      table.addCell(item.getQuantity().toString());
      table.addCell(item.getPrice().toString() + " MAD");
    }
    document.add(table);

    document.add(new Paragraph(" "));
    Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    document.add(new Paragraph("TOTAL AMOUNT: " + order.getTotal() + " MAD", fontTotal));

    document.close();
    return out.toByteArray();
  }
}
