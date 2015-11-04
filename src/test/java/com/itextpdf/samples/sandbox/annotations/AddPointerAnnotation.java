/**
 * This example was written by Bruno Lowagie in answer to the following question:
 * http://stackoverflow.com/questions/26752663/adding-maps-at-itext-java
 */
package com.itextpdf.samples.sandbox.annotations;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.*;
import com.itextpdf.core.pdf.annot.PdfLineAnnotation;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class AddPointerAnnotation extends GenericTest {
    public static final String IMG = "./src/test/resources/sandbox/annotations/map_cic.png";
    public static final String DEST = "./target/test/resources/sandbox/annotations/add_pointer_annotation.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddPointerAnnotation().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Image img = new Image(ImageFactory.getImage(IMG));
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document document = new Document(pdfDoc, new PageSize(img.getImageWidth(), img.getImageHeight()));
        img.setFixedPosition(0, 0);
        document.add(img);
        Rectangle rect = new Rectangle(220, 350, 475 - 220, 595 - 350);
        // "Cambridge Innovation Center" == contents;
        PdfLineAnnotation lineAnnotation = new PdfLineAnnotation(pdfDoc, rect, new float[]{225, 355, 470, 590});
        PdfArray le = new PdfArray();
        le.add(new PdfName("OpenArrow"));
        le.add(new PdfName("None"));
        lineAnnotation.setTitle(new PdfString("You are here:"));
        lineAnnotation.setContents("Cambridge Innovation Center");
        // TODO Why not to implement setColor (Color). (in this particular case setColor(Color#getColorValues) seems strange)
        lineAnnotation.setColor(Color.RED.getColorValue());
        // TODO There is no PdfAnnotation.FLAGS_PRINT flag
        //lineAnnotation.setFlags(PdfAnnotation.FLAGS_PRINT);
        PdfDictionary borderStyle = new PdfDictionary();
        borderStyle.put(PdfName.S, PdfName.S);
        borderStyle.put(PdfName.W, new PdfNumber(5));
        lineAnnotation.setBorderStyle(borderStyle);
        lineAnnotation.put(new PdfName("LE"), le);
        lineAnnotation.put(new PdfName("IT"), new PdfName("LineArrow"));
        pdfDoc.getFirstPage().addAnnotation(lineAnnotation);
        pdfDoc.close();
    }
}
