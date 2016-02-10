/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie.
 * http://stackoverflow.com/questions/29563942/how-to-add-a-cover-pdf-in-a-existing-itext-document
 */
package com.itextpdf.samples.sandbox.merge;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class AddCover1 extends GenericTest {
    public static final String DEST
            = "./target/test/resources/sandbox/merge/add_cover.pdf";
    public static final String COVER
            = "./src/test/resources/sandbox/merge/hero.pdf";
    public static final String RESOURCE
            = "./src/test/resources/sandbox/merge/pages.pdf";
    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddCover1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        PdfMerger merger = new PdfMerger(pdfDoc);
        PdfDocument cover = new PdfDocument(new PdfReader(COVER));
        PdfDocument resource = new PdfDocument(new PdfReader(RESOURCE));
        merger.addPages(cover, 1, 1);
        merger.addPages(resource, 1, resource.getNumberOfPages());
        merger.merge();
        cover.close();
        resource.close();
        pdfDoc.close();
    }
}