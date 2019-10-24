package com.tabian.tcoetkiosk;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.UserInfo;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*import com.beproject.firebaselogin.Firebase.FirebaseDatabaseHelper;*/

public class LetterGenerator extends AppCompatActivity {
    private static final String TAG = "PdfCreatorActivity";
    private Button mCreateButton;
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_generator);
       /* mContentEditText = (EditText) findViewById(R.id.edit_text_content);*/
        mCreateButton = (Button) findViewById(R.id.button_create);  //remove this
        try{
        if (UserInformation.publicName.isEmpty()){
            Toast.makeText(getApplicationContext(),"Data Might Not Be Loaded Yet.", Toast.LENGTH_LONG).show();
            /* mContentEditText.requestFocus();*/
            return;
        }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        try {
            createPdfWrapper();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        /*GENERATE PDF CODE
        * createPDFWrapper
        *
        * CHECK Temp value
        *
        * If temp==1
        * create
        * */






    }
    private void createPdfWrapper() throws FileNotFoundException,DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }


                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            if(LetterConnection.check==1){
               createPdfTrans();
            }
            else if(LetterConnection.check==2)
            {
                createPdfLOR();
            }
            else if(LetterConnection.check==3)
            {
                createPdfdegreecerti();
            }
            else if(LetterConnection.check==4)
            {
                createPdfCharactercerti();
            }
            else if(LetterConnection.check==5)
            {
                createPdfBonafideCerti();
            }
            else if(LetterConnection.check==6)
            {
                createIDCard();
            } else if(LetterConnection.check==7)
            {
                createPdfReeval();
            } else if(LetterConnection.check==8)
            {
                createPdfTransferCerti();
            } else if(LetterConnection.check==9)
            {
                createPdfLeavingCerti();
            } else if(LetterConnection.check==10)
            {
                createPdfLeavingCertiMed();
            }
            else if(LetterConnection.check==11)
            {
                createPdfOthers();
            }













           /* createPdf();*/
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

   /* private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"HelloWorld.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("Hello, My Name is "+FirebaseDatabaseHelper.publicName+"This is some random " +
                "letter that i have generated. My DOB is"+FirebaseDatabaseHelper.publicDiv));
        document.add(new Paragraph("I like making new friends, you can call me on "+FirebaseDatabaseHelper.publicPhone+"Or also mail me at "
        +FirebaseDatabaseHelper.publicEmail));
        document.add(new Paragraph("My hobbies are "+FirebaseDatabaseHelper.publicDept));

        document.add(new Paragraph("see all the contests are from firebase <3"));


        document.close();
        previewPdf();

    }*/


    //Trans Is generated here
    private void createPdfTrans() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);


        pdfFile = new File(docsFolder.getAbsolutePath(),"Transcript.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for "+Transcript.copies+" copy of Transcript.",font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you '"+Transcript.copies+"' copy" +
                " of the official transcript from Semester "+Transcript.semstrart+" to Semester "+Transcript.semend+" for the purpose" +
                " of applying for Masters of Science in a foreign University.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
      /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Phone no: "+ViewDatabase.sphone,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. All Semester Marksheets.",font2));
        document.add(new Paragraph("                                       2. GRE/TOEFL Scorecard.",font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Transcript.pdf' in your Files.", Toast.LENGTH_SHORT).show();
        finish();

    }


    //LOR Is generated here
    private void createPdfLOR() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"LOR.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for "+LOR.copies+" copy of Letter of recommendation.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you '"+LOR.copies+"' copy" +
                " of the official Letter of recommendation for the purpose" +
                " of applying for Masters of Science in a foreign University.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
       /* document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. Letters of each Professor..",font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'LOR.pdf' in your Files.", Toast.LENGTH_SHORT).show();
        finish();

    }



    //provisional degree certi Is generated here
    private void createPdfdegreecerti() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"Provisional_Degree_Certificate.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for copy of provisional degree certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to get my Provisional degree certificates."+
               " I have applied for a job position recently and I need to submit my qualification certificates So I request you to kindly provide me with my provisional degree certificates.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. All Semester Marksheets.",font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Provisional_Degree_Certificate.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }



    //CGPI Is generated here
   /* private void createPdfCGPI() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"CGPI_Verification_letter.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for CGPI Verification letter.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to get my CGPI Verified."+
                "I received my CGPI and I think the CGPI is not calculated correctly. So I request you to Verify CGPI again.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        *//*  document.add( Chunk.NEWLINE );*//*
        *//*document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "Please Locate the file named 'CGPI_Verification_letter.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }


    //Medium of Instruction Is generated here
    private void createPdfMoI() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"Transcript.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph(UserInformation.publicDept));
        document.add(new Paragraph(UserInformation.publicAd2));
        document.add(new Paragraph(UserInformation.publicAd3));
        document.add(new Paragraph(UserInformation.publicAd4));
        document.add(new Paragraph("To,"));
        document.add(new Paragraph("The Principal,"));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,"));
        document.add(new Paragraph("Kandivali-E,"));
        document.add(new Paragraph("Mumbai-400101"));
        *//*android:gravity="right"*//*document.add(new Paragraph("Subject: Request for copy of Transcript."));
        document.add(new Paragraph("Respected Sir,"));
        document.add(new Paragraph("I, "+UserInformation.publicName+" student of "+UserInformation.publicDiv+", am writing this letter requesting you copy of the official transcript" +
                " from Semester 1 to Semester 6 for the purpose of applying for Masters of Science in a foreign University."));
        document.add(new Paragraph("Kindly do the needful,"));
        document.add(new Paragraph("Thanking you."));
        document.add(new Paragraph("Yours sincerely,"));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
       *//* document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(UserInformation.publicName));
        document.add(new Paragraph(UserInformation.publicDiv));
        document.add(new Paragraph(UserInformation.publicPhone));

        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT).show();
        finish();

    }*/


    //Character certi Is generated here
    private void createPdfCharactercerti() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"Character_Certificate.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph(""));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Character certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to get my Character Certificate. "+
                "I am in need of a Character Certificate. I request you to issue the Character certificate at the earliest possible time.",font1));
                document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. Passing Certificate.",font2));
        document.add(new Paragraph("                                       2. All Semester Marksheets.",font2));
        document.add(new Paragraph("                                       3. Leaving Certificate.",font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Character_Certificate.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }



    /*//NOC Is generated here
    private void createPdfNoObjectionCerti() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"No_Objection_Certificate.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for No objection certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to get my No objection certificate. "+
                "I am in need of a No objection certificate. I request you to issue the No objection certificate at the earliest possible time.",font1));
                document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        *//*  document.add( Chunk.NEWLINE );*//*
        *//*document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "Please Locate the file named 'No_Objection_Certificate.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }*/




    //Bonafide certi Is generated here
    private void createPdfBonafideCerti() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"Bonafide_Certificate.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Bonafide certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to get my Bonafide certificate."+
               " I am in need of a Bonafide certificate as a proof of my residence. I request you to issue the Bonafide certificate at the earliest possible time.",font1));
                document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. Birth Certificate.",font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Bonafide_Certificate.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }


    //leaving certi Is generated here
    private void createPdfLeavingCerti() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"Leaving_Certificate.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph(""));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for leaving certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you for the issue of Leaving Certificate. "+
                "I am in need of Leaving Certificate. I request you to issue the leaving certificate  at the earliest possible time.",font1));
                document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. First Year Original Fee Receipt.",font2));
        document.add(new Paragraph("                                       2. 8th Semester Marksheet Xerox.",font2));
        document.add(new Paragraph("                                       3. No Dues Form.",font2));
        document.add(new Paragraph("                                       4. Cancelled Cheque.",font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Leaving_Certificate.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }





    //leaving certi Is generated here
    private void createPdfLeavingCertiMed() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"Leaving_Certificate_Medical.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for leaving certificate (medical) .",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you for the approval of intermittent medical leave. "+
                "I hope that you will sanction my leave. I request you to issue the leaving certificate  at the earliest possible time.",font1));
                document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. Proof of the Reason.",font2));




        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Leaving_Certificate_Medical.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }




    //Transfer Certi Is generated here
    private void createPdfTransferCerti() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"Transfer_Certificate.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Transfer Certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to issue me a Transfer Certificate",font1));
        document.add(new Paragraph("             I hereby request you to issue the same as soon as possible as I require it for attending College. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. All Semester Marksheets.",font2));
        document.add(new Paragraph("                                       2. Leaving Certificate.",font2));




        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Transfer_Certificate.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }



/*


    //Migration Certi Is generated here
    private void createMigration() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"Migration_Certificate.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                            "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Migration Certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to issue me a Migration Certificate",font1));
        document.add(new Paragraph("             I hereby request you to issue the same as soon as possible as I require it for attending College. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        */
/*  document.add( Chunk.NEWLINE );*//*

        */
/*document.add( Chunk.NEWLINE );*//*

        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        */
/*document.add(new Paragraph(UserInformation.publicEmail));*//*



        document.close();
        previewPdf();
        */
/*TOAST*//*

        Toast.makeText(this, "Please Locate the file named 'Migration_Certificate.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }




*/


    //ID card Letter Is generated here
    private void createIDCard() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"IDCard.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for ID Card.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to issue me an ID Card" +
                " as I have lost my original ID Card. ",font1));
        document.add(new Paragraph("             I hereby request you to issue the same as soon as possible as I require it for attending College. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. F.I.R.",font2));






        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'IDCard.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }



/*

    //Duplicate Marksheet Is generated here
    private void createPdfDupMarksheet() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"DuplicateMarksheet.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                            "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Duplicate Marksheet.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to issue me a duplicate Marksheet" +
                " as I have lost my original Marksheet. ",font1));
        document.add(new Paragraph("             I hereby request you to issue the same as soon as possible as I require it for applying for Masters/Placements. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        *//*  document.add( Chunk.NEWLINE );*//*
        *//*document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "Please Locate the file named 'DuplicateMarksheet.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }








    //Verification Education Is generated here
    private void createPdfVerifiEdu() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"EducationVerification.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                            "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Education Verification Certificate.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to issue me a Education Verification Certificate" +
                " as I have to submit it.",font1));
        document.add(new Paragraph("             I hereby request you to issue the same as soon as possible as I need the Education Verification Certificate. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        *//*  document.add( Chunk.NEWLINE );*//*
        *//*document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "Please Locate the file named 'EducationVerification.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }







    //Duplicate Fee Receipt Is generated here
    private void createPdfDupFeeReceipt() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"DuplicateFeeReceipt.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                            "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Duplicate Fee-Receipt.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to issue me a duplicate Fee-Receipt" +
                " as I have lost my original Fee-Receipt. ",font1));
        document.add(new Paragraph("             I hereby request you to issue the same as soon as possible as I need the fee receipt. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        *//*  document.add( Chunk.NEWLINE );*//*
        *//*document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "Please Locate the file named 'DuplicateFeeReceipt.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }




    //Internship Is generated here
    private void createPdfIntern() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"Internship.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                            "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for applying to Internship.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter for applying to an Internship.",font1));
        document.add(new Paragraph("             I hereby request you to grant me permission for doing an internship for getting work experience.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        *//*  document.add( Chunk.NEWLINE );*//*
        *//*document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "Please Locate the file named 'Internship.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }





    //Duplicate HallTicket Is generated here
    private void createPdfDupHallTicket() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"DuplicateHallTicket.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                            "+ViewDatabase.sad3));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Duplicate Hall-Ticket.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you to issue me a duplicate Hall-Ticket" +
                " as I have lost my original Hall-Ticket. ",font1));
        document.add(new Paragraph("             I hereby request you to issue the same as soon as possible as my exam dates are near. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        *//*  document.add( Chunk.NEWLINE );*//*
        *//*document.add( Chunk.NEWLINE );*//*
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        *//*document.add(new Paragraph(UserInformation.publicEmail));*//*


        document.close();
        previewPdf();
        *//*TOAST*//*
        Toast.makeText(this, "Please Locate the file named 'DuplicateHallTicket.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }*/




    //Re-eval Is generated here
    private void createPdfReeval() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"Re-evaluationMarksheet.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: Request for Re-evaluation of Marksheet.",font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdiv+", Division-" +
                ""+ViewDatabase.sdept+", Roll No-"+ViewDatabase.srno+", am writing this letter requesting you for the re-evaluation of my marksheets" +
                " as I received my marksheet today and I am dissatisfied with the marks obtained.",font1));
        document.add(new Paragraph("             I hereby request you to re-evaluate my marksheet. I am ready to" +
                " pay the necessary fees for the same.",font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdiv+" "+ViewDatabase.sdept,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));
        document.add( Chunk.NEWLINE );
        document.add(new Paragraph("                          Enclosure:",font2));
        document.add(new Paragraph("                                       1. Marksheet.",font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'Re-evaluationMarksheet.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }

    //Others Is generated here
    private void createPdfOthers() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MMM-yyyy");
        String formatedDate = curFormater.format(c);

        pdfFile = new File(docsFolder.getAbsolutePath(),"LetterKiosk.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        Font font1 = new Font(Font.FontFamily.TIMES_ROMAN  , 15);
        Font font2 = new Font(Font.FontFamily.TIMES_ROMAN , 15, Font.BOLD);
        document.open();
        //this is one letter. copy paste for multiple letters :))
        document.add(new Paragraph("                                                           " +
                "                                                                        ID: "+ts));
        document.add(new Paragraph("\n\n\n"));
        document.add(new Paragraph("                                                           " +
                "                                                        "+ViewDatabase.sname));
        document.add(new Paragraph("                                               " +
                "                                                                    "+ViewDatabase.sad1));
        document.add(new Paragraph("                                                               " +
                "                                                    "+ViewDatabase.sad2));
        document.add(new Paragraph("                                                                      " +
                "                                             "+ViewDatabase.sad3));
        document.add(new Paragraph("                                                                      " +
                "                                             "+formatedDate));
        document.add(new Paragraph("To,",font1));
        document.add(new Paragraph("The Principal,",font1));
        document.add(new Paragraph("Thakur College Of Engineering and Technology,",font1));
        document.add(new Paragraph("Kandivali-E,",font1));
        document.add(new Paragraph("Mumbai-400101",font1));
        document.add(new Paragraph("                          Subject: "+Others.sub,font2));
        document.add(new Paragraph("Respected Sir,",font1));
        document.add(new Paragraph("             I, "+ViewDatabase.sname+" student of "+ViewDatabase.sclass+" "+ViewDatabase.sdept+", Division-" +
                ""+ViewDatabase.sdiv+", Roll No-"+ViewDatabase.srno+". ",font1));

        document.add(new Paragraph("            "+Others.content,font1));
        document.add(new Paragraph("             Kindly do the needful,",font1));
        document.add(new Paragraph("             Thanking you.",font1));
        document.add( Chunk.NEWLINE );
       /* document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph("Yours sincerely,",font1));
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        /*  document.add( Chunk.NEWLINE );*/
        /*document.add( Chunk.NEWLINE );*/
        document.add(new Paragraph(ViewDatabase.sname,font2));
        document.add(new Paragraph(ViewDatabase.sclass+", "+ViewDatabase.sdept+" "+ViewDatabase.sdiv,font2));
        document.add(new Paragraph("Roll no: "+ViewDatabase.srno,font2));
        document.add(new Paragraph("Contact no: "+ViewDatabase.sphone,font2));
        document.add(new Paragraph("Email: "+ViewDatabase.semail,font2));
        document.add(new Paragraph("Academic Year: "+ViewDatabase.sacad,font2));





        /*document.add(new Paragraph(UserInformation.publicEmail));*/


        document.close();
        previewPdf();
        /*TOAST*/
        Toast.makeText(this, "Please Locate the file named 'LetterKiosk.pdf' in your Files.", Toast.LENGTH_LONG).show();
        finish();

    }





    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        } else {
            Toast.makeText(this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }




}


