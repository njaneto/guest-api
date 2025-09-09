package com.church.guest.orders.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

public class QrService {
    public static String toPngBase64( String payload, int size ) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode( payload, BarcodeFormat.QR_CODE, size, size );
            BufferedImage image = MatrixToImageWriter.toBufferedImage( matrix );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( image, "PNG", baos );
            return Base64.getEncoder().encodeToString( baos.toByteArray() );
        } catch( WriterException | java.io.IOException e ) {
            throw new RuntimeException( "Erro ao gerar QR Code", e );
        }
    }
}
