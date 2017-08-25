package com.lefer.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author web
 * @version 1.0
 * @since 2017/08/25 09:41
 */
public class Test {
    //自定义中文字体位置
    private static final String fontPath = "./src/main/resources/Deng.ttf";

    public static void main(String[] args) throws DocumentException, IOException {
        Test test=new Test();
        test.creatPdf();
        test.manipulatePdf("test_cn.pdf", "test_cn_2.pdf");
    }

    private void creatPdf()throws DocumentException, IOException {
        //创建基础字体
        BaseFont bf = BaseFont.createFont(fontPath,BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        //自定义字体属性
        Font font = new Font(bf,30);
        Font smallFont = new Font(bf,24);

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("test_cn.pdf"));
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        //Make document tagged
        //writer.setTagged();
        //===============
        writer.setViewerPreferences(PdfWriter.DisplayDocTitle);
        document.addTitle("样例PDF");
        writer.createXmpMetadata();
        //footer and header
        MyFooter event = new MyFooter();
        writer.setPageEvent(event);
        //=====================
        document.open();
        //paragraph段落
        Paragraph p1 = new Paragraph("第一章",font);
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);
        //phrase
        Phrase ph = new Phrase("一个短语\n",smallFont);
        document.add(ph);
        //separator
        DottedLineSeparator separator = new DottedLineSeparator();
        //separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);
        document.add(linebreak);
        //paragraph
        Paragraph p2 = new Paragraph("第二章",font);
        p2.setAlignment(Element.ALIGN_CENTER);
        p2.setSpacingBefore(10f);
        document.add(p2);
        //list
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(200);
        table.setWidths(new int[]{ 1, 10 });
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell cell;
        cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.addElement(new Paragraph("标签",smallFont));
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        List list = new List(List.UNORDERED);
        list.add(new ListItem(new Chunk("数值1",smallFont)));
        list.add(new ListItem(new Chunk("数值2",smallFont)));
        list.add(new ListItem(new Chunk("数值3",smallFont)));
        cell.addElement(list);
        table.addCell(cell);
        table.setSpacingBefore(50f);
        document.add(table);

        Paragraph p3 = new Paragraph("第三章",font);
        p3.setAlignment(Element.ALIGN_LEFT);
        p3.add("\n");
        p3.setTabSettings(new TabSettings(100f));
        p3.add(new Chunk("卫宁健康",smallFont));
        p3.add(Chunk.TABBING);
        p3.add(new Chunk("卫宁健康",smallFont));
        p3.add(Chunk.TABBING);
        p3.add(new Chunk("卫宁健康",smallFont));
        document.add(p3);
        //table
        PdfPTable table2 = new PdfPTable(8);
        for(int aw = 0; aw < 16; aw++){
            table2.addCell("hi");
        }
        table2.setSpacingBefore(10f);
        document.add(table2);
        document.close();
    }

    class MyFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase header = new Phrase("this is a header", ffont);
            Phrase footer = new Phrase("this is a footer", ffont);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    }

    private void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.setEncryption("Hello".getBytes(), "World".getBytes(),
                PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
        stamper.close();
        reader.close();
    }

}
