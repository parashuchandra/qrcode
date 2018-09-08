package com.mkyong;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

@RestController
@RequestMapping("/qrgen")
public class SampleRestController {

    @GetMapping("/qrexmaple")
    public String qr(@RequestParam String value){
        try
        {
            Document document = new Document();
            OutputStream outputStream = new FileOutputStream("HelloWorld1.pdf");

            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);

            document.open();
            PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

            Barcode128 barcode128 = new Barcode128();
            barcode128.setCode(value);
            barcode128.setCodeType(Barcode128.CODE128);
            Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
            code128Image.setAbsolutePosition(10, 700);
            code128Image.scalePercent(100);
            document.add(code128Image);

            BarcodeQRCode barcodeQrcode = new BarcodeQRCode(value, 1, 1, null);
            Image qrcodeImage = barcodeQrcode.getImage();
            qrcodeImage.setAbsolutePosition(20, 500);
            qrcodeImage.scalePercent(100);
            document.add(qrcodeImage);

            document.close();
            return document.toString();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return "welcome";
    }

}
