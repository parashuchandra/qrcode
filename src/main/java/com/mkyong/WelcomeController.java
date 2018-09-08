package com.mkyong;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WelcomeController {
	public static final String DEST = "./target/test/resources/sandbox/tables/barcodes.pdf";
	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}

	@RequestMapping("/qr")
	public String qr(Map<String, Object> model) throws FileNotFoundException{
		try
		{
			Document document = new Document();
			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));

			document.open();
			PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

			Barcode128 barcode128 = new Barcode128();
			barcode128.setCode("Parashuram");
			barcode128.setCodeType(Barcode128.CODE128);
			Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
			code128Image.setAbsolutePosition(10, 700);
			code128Image.scalePercent(100);
			document.add(code128Image);

			BarcodeQRCode barcodeQrcode = new BarcodeQRCode("Parashuram", 1, 1, null);
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
        model.put("message", this.message);
		return "welcome";
	}


}