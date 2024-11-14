package com.esprit.wellnest.ui.Event;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.esprit.wellnest.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeFragment extends Fragment {
    private static final String ARG_EVENT_ID = "event_id";
    private static final String ARG_EVENT_TITLE = "event_title";
    private static final String ARG_EVENT_DESCRIPTION = "event_description";
    private static final String ARG_EVENT_START_DATE = "event_start_date";
    private static final String ARG_EVENT_END_DATE = "event_end_date";

    public static QRCodeFragment newInstance(int eventId, String title, String description, String startDate, String endDate) {
        QRCodeFragment fragment = new QRCodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EVENT_ID, eventId);
        args.putString(ARG_EVENT_TITLE, title);
        args.putString(ARG_EVENT_DESCRIPTION, description);
        args.putString(ARG_EVENT_START_DATE, startDate);
        args.putString(ARG_EVENT_END_DATE, endDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);
        ImageView qrCodeImageView = view.findViewById(R.id.qrCodeImageView);

        if (getArguments() != null) {
            int eventId = getArguments().getInt(ARG_EVENT_ID);
            String title = getArguments().getString(ARG_EVENT_TITLE);
            String description = getArguments().getString(ARG_EVENT_DESCRIPTION);
            String startDate = getArguments().getString(ARG_EVENT_START_DATE);
            String endDate = getArguments().getString(ARG_EVENT_END_DATE);

            generateQRCode(eventId, title, description, startDate, endDate, qrCodeImageView);
        }

        return view;
    }

    private void generateQRCode(int eventId, String title, String description, String startDate, String endDate, ImageView imageView) {
        String qrText = " Good News , here the result  the Event is :" +
                "Title2: " + title + "\n" +"more Details :"+
                "Description: " + description + "\n" +
                    "this Event Start in:" + startDate + "\n" +
                "this Event Start in:" + endDate+"have Fun & good luck"+"\n"+
                "dont forget to get a reservation";

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = Bitmap.createBitmap(bitMatrix.getWidth(), bitMatrix.getHeight(), Bitmap.Config.RGB_565);
            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                for (int y = 0; y < bitMatrix.getHeight(); y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
