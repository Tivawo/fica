package com.domain.fica.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ListprintDocumentAdapter extends PrintDocumentAdapter {

    //Attributes
    private final String TAG = "ListprintDocumentadapter";
    private final Context context;
    private final ArrayList<Bitmap> bitmap;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    public int totalpages;
    private int itemcounter = 0;

    //Constructor
    public ListprintDocumentAdapter(Context context, ArrayList<Bitmap> bitmap) {
        this.context = context;
        this.bitmap = bitmap;
        bitmap.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
        int minsize = bitmap.size() / 2;
        Log.d("bitmap size/2", "" + minsize);

        if (minsize >= 25) {
            totalpages = 25;
        } else {
            totalpages = minsize;
        }

    }

    @Override
    public void onWrite(
            final PageRange[] pageRanges,
            final ParcelFileDescriptor destination,
            final CancellationSignal cancellationSignal,
            final PrintDocumentAdapter.WriteResultCallback callback) {

        Log.d(TAG, "onWrite: Called");
        for (int i = 0; i < totalpages; i++) {
            if (pageInRange(pageRanges, i)) {
                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                        pageHeight, i).create();

                PdfDocument.Page page =
                        myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                drawPage(page, i);
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pageRanges);
    }

    @Override
    public void onLayout(
            PrintAttributes oldAttributes,
            PrintAttributes newAttributes,
            CancellationSignal cancellationSignal,
            PrintDocumentAdapter.LayoutResultCallback callback,
            Bundle metadata) {

        Log.d(TAG, "onLayout: Called");

        myPdfDocument = new PrintedPdfDocument(context, newAttributes);

        pageHeight =
                newAttributes.getMediaSize().getHeightMils() / 750 * 80;
        pageWidth =
                newAttributes.getMediaSize().getWidthMils() / 750 * 80;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalpages > 0) {
            PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                    .Builder("print_output.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalpages);

            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            Log.d(TAG, "onLayout: Page count is zero.");
            callback.onLayoutFailed("Page count is zero.");
        }
    }

    private void drawPage(PdfDocument.Page page,
                          int pagenumber) {
        Log.d(TAG, "drawPage: Called with pagenumber: " + pagenumber);
        Canvas canvas = page.getCanvas();
        pagenumber++;
        // Make sure page numbers start at 1

        int titleBaseLine = 72;
        int leftMargin = 54;

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        if (itemcounter < bitmap.size()) {
            canvas.drawBitmap(
                    bitmap.get(itemcounter),
                    leftMargin,
                    titleBaseLine,
                    paint);
            itemcounter++;
        }
        if (itemcounter < bitmap.size()) {
            canvas.drawBitmap(
                    bitmap.get(itemcounter),
                    leftMargin,
                    titleBaseLine + 500,
                    paint);
            itemcounter++;
        }
        if(itemcounter == bitmap.size()-1){
            itemcounter = 0;
        }
    }


    private boolean pageInRange(PageRange[] pageRanges, int page) {
        for (int i = 0; i < pageRanges.length; i++) {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }
}
