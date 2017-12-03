package vn.com.jackycore.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by sgvn213 on 9/21/2017.
 */

public class Utils {
    public static int getScreenWidth(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        return context.getResources().getDisplayMetrics().heightPixels;
    }

    private static float scale;

    public static int dpToPixel(float dp, Context context) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dp * scale);
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            Log.e(e);
        }
        return date.toString();
    }

    public static String convertLocationDate(String strDate) {
        //2017-11-23T00:00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);
    }

    public static Date getToday() {
        TimeZone tz = TimeZone.getTimeZone("GMT+08:00");
        Calendar c = Calendar.getInstance(tz);
        return (Date) c.getTime();
    }


    public static String formatLeftTime(long milisecond) {
        long second = milisecond / 1000;
        long minute = second / 60;
        long hours = minute / 60;
        long leftMinute = minute % 60;
        if (hours == 0) {
            return leftMinute + " minutes";
        } else if (leftMinute == 0) {
            return hours + " hours";
        } else {
            return hours + " hours " + leftMinute + " minutes";
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static File copyImageUri2File(Context context, Uri uri) {
        try {
            File file = File.createTempFile("image", ".jpg", context.getCacheDir());
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            copyInputStreamToFile(inputStream, file);
            return file;
        } catch (Exception e) {
            Log.e(e);
        }
        return null;
    }

    private static void copyInputStreamToFile(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[10 * 1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
    }

    public static String getImagePath(Context context, Uri uri) {
        String result = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }

    public static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.d(mCurrentPhotoPath);
        return image;
    }

/*        public static String dispatchTakePictureIntent(Activity activity, int requestCode) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(activity,
                            "sg.vinova.complaint.dev.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    activity.startActivityForResult(takePictureIntent, Constant.REQUEST_TAKE_PHOTO);
                }
            }
            return "";
        }*/

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        Log.d("Bitmap size B: " + BitmapCompat.getAllocationByteCount(inImage) / 1024 + " KB");
        long tim = System.currentTimeMillis();
        String path = insertImage(inContext.getContentResolver(), inImage, "Title_" + tim, null);
        return Uri.parse(path);
    }

    public static Bitmap getRotatedBitmap(String path, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Matrix m = new Matrix();
        ExifInterface exif = null;
        int orientation = 1;

        try {
            if (path != null) {
                // Getting Exif information of the file
                exif = new ExifInterface(path);
            }
            if (exif != null) {
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        m.preRotate(270);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        m.preRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        m.preRotate(180);
                        break;
                    default:
                        return bitmap;
                }
                // Rotates the image according to the orientation
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            }
        } catch (IOException e) {
            Log.e(e);
        }

        return null;
    }


    private static String insertImage(ContentResolver cr, Bitmap source,
                                      String title, String description) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null && url != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 100, imageOut);
                    Log.d("Bitmap size A: " + BitmapCompat.getAllocationByteCount(source) / 1024 + " KB");
                } catch (Exception e) {
                    Log.e(e);
                } finally {
                    if (imageOut != null) {
                        imageOut.flush();
                        imageOut.close();
                    }
                }

            } else {
                Log.d("Failed to create thumbnail, removing original");
                if (url != null) {
                    cr.delete(url, null, null);
                    url = null;
                }
            }
        } catch (Exception e) {
            Log.e(e);
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }


    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        assert cursor != null;
        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static long getAvailableMemory(Context context) {
//        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
//        activityManager.getMemoryInfo(mi);
//        long m = mi.availMem / 1024;// to kilobyte
        long m = Runtime.getRuntime().freeMemory() / 1024;
        Log.d("Available Memory: " + m + " KB");
        return m;
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            Log.e(e);
        }
        return "";
    }


    public static String getDeviceId(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("Android device id: " + android_id);
        return android_id;
    }

    public static int getNotificationId() {
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        if (tmpStr.length() <= 8) {
            return Integer.valueOf(tmpStr);
        }
        String last4Str = tmpStr.substring(tmpStr.length() - 8);
        return Integer.valueOf(last4Str);
    }

    public static String upperCaseFirstChar(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }

        if (s.length() == 1) {
            return s.toUpperCase();
        }

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static Integer validateRegister(String s1, String s2, String s3, String s4, String s5, boolean isChecked) {
        if (s1.equals(""))
            return 1;
        else if (s2.equals(""))
            return 2;
        else if (s3.equals(""))
            return 3;
        else if (s4.equals(""))
            return 4;
        else if (s5.equals(""))
            return 5;
        else if (!validateEmail(s2))
            return 6;
        else if (!s4.equals(s5))
            return 7;
        else if (s3.length() < 4)
            return 8;
        else if (!isChecked)
            return 9;
        else
            return 0;
//        if (!s1.equals("") && !s2.equals("") && !s3.equals("") && !s4.equals("") && !s5.equals("") && validateEmail(s2)
//                && (s4.equals(s5)) && s3.length() == 4) {
//            return true;
//        }
    }


    public static Boolean validateEmail(String s) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }


    public static void transparentStatusbar(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = context.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height
            int statusBarHeight = getStatusBarHeight(context);

            View view = new View(context);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    static float x = 0, y = 0;
    static float delta = 30;
    static boolean disableClick = false;

    public static void animateClickButton(View view, final View.OnClickListener onClickListener) {


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        view.animate().setDuration(100).scaleX(0.95f).scaleY(0.95f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        view.animate().setDuration(100).scaleX(1.0f).scaleY(1.0f);
                        if (!disableClick)
                            onClickListener.onClick(view);
                        return false;

                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(motionEvent.getX() - x) > delta || Math.abs(motionEvent.getY() - y) > delta) {
                            disableClick = true;
                            //  android.util.Log.d("animate view",( motionEvent.getX() - x) + " - " + (motionEvent.getY() - y));
                        } else {
                            disableClick = false;
                        }
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        view.animate().setDuration(100).scaleX(1.0f).scaleY(1.0f);
                        break;
                }

                return false;
            }
        });
    }


    public static boolean isSameMonth(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return false;
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
    }

    public static boolean isSameDate(Calendar c1, Calendar c2) {
        if (c1 == null || c2 == null)
            return false;
        return (c1.get(Calendar.ERA) == c2.get(Calendar.ERA)
                && c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    public static String saveBitmap(Context context, String filename, Bitmap bitmap) {

        String path = Environment.getExternalStorageDirectory().toString();

        // path to /data/data/nsa/app_data/imageDir
        File directory = new File(path + "/" + "Constant.IMAGE_DIR");
        if (!directory.exists()) {
            directory.mkdir();
        }
        // Create imageDir
        File mypath = new File(directory, filename + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();

    }

    public static String getImageDir(Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("Constant.IMAGE_DIR", Context.MODE_PRIVATE);
        return directory.getAbsolutePath();
    }

    public static void deleteTempFile(Context context) {
        File dir = new File(getImageDir(context));
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    public static Bitmap scaleBitmap(Bitmap realImage, int maxImageSize) {
        if (realImage.getHeight() <= maxImageSize && realImage.getWidth() <= maxImageSize) {
            return realImage;
        }
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, true);
        return newBitmap;
    }


    public static String formatDate(String strDate, String inputFormat, String outputFormat) {
        if (strDate == null) {
            return "";
        }
        try {
            Date date = parseDateWithGMT(strDate, inputFormat);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outputFormat);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            Log.e(e);
        }
        return strDate;
    }

    public static Map<String, String> formatDateAndTime(Context context, String strDate, String inputFormat, String outputFormat) {
        if (strDate == null) {
            return null;
        }

        Map<String, String> mapDateTime = new ArrayMap<>();

        try {
            Locale currentLocale = context.getResources().getConfiguration().locale;
            Date date = parseDateWithGMT(strDate, inputFormat);
            mapDateTime.put("date", date.getDate() + "-" + date.getMonth() + "-" + date.getYear());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(outputFormat, currentLocale);
            simpleDateFormat.format(date);
            mapDateTime.put("time", String.valueOf(date.getTime()));
        } catch (Exception e) {
            Log.e(e);
        }
        return mapDateTime;
    }

    public static String DateToStringWithInputFormat(Date date, String outputFormat) {
        Format formatter = new SimpleDateFormat(outputFormat);
        String strDate = formatter.format(date);
        return strDate;
    }


    public static int getBackgroundColor(View view) {
        Drawable drawable = view.getBackground();
        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            if (Build.VERSION.SDK_INT >= 11) {
                return colorDrawable.getColor();
            }
            try {
                Field field = colorDrawable.getClass().getDeclaredField("mState");
                field.setAccessible(true);
                Object object = field.get(colorDrawable);
                field = object.getClass().getDeclaredField("mUseColor");
                field.setAccessible(true);
                return field.getInt(object);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return Color.parseColor("#FFFFFF");
    }

    public static String convertToString(ArrayList<String> list) {

        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list) {
            sb.append(delim);
            sb.append(s);
            ;
            delim = ",";
        }
        return sb.toString();
    }

    public static ArrayList<String> convertToArray(String string) {

        ArrayList<String> list = new ArrayList<String>(Arrays.asList(string.split(",")));
        return list;
    }

  /*  public static <T> T deepCopy(T object, Class<T> type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(object, type), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static void sendMail(Activity activity, String toEmail, String toName) {

        Intent emailIntent = new Intent(
                Intent.ACTION_SEND);
        emailIntent.setAction(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{toEmail});
        //emailIntent.putExtra(android.content.Intent.EXTRA_CC, "");
        //emailIntent.putExtra(android.content.Intent.EXTRA_BCC, "");
        // emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Playlist Details");
        // emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(Detail));
        emailIntent.setType("text/html");

// FOLLOWING STATEMENT CHECKS WHETHER THERE IS ANY APP THAT CAN HANDLE OUR EMAIL INTENT
        activity.startActivity(Intent.createChooser(emailIntent,
                "Send Email Using: "));
    }

    public static String addSuffixesCount(String num) {
        if (num.endsWith("1"))
            return num + "st";
        if (num.endsWith("2"))
            return num + "nd";
        if (num.endsWith("3"))
            return num + "rd";
        return num + "th";
    }


    public static Boolean isEmpty(String s) {
        if (s != null && !s.equals(""))
            return false;
        return true;
    }

    public static Boolean compareString(String s, String s1) {
        if (s == null)
            return false;
        if (s.equals(""))
            return false;
        if (s1 == null)
            return false;
        if (s1.equals(""))
            return false;
        if (s.equals(s1))
            return true;
        return false;
    }

    public static Map<String, String> parseJsonObject(JSONObject json, Map<String, String> out) throws JSONException {
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String val = null;
            try {
                JSONObject value = json.getJSONObject(key);
                parseJsonObject(value, out);
            } catch (Exception e) {
                val = json.getString(key);
            }

            if (val != null) {
                out.put(key, val);
            }
        }
        return out;
    }

/*
    public static String calculateTime(Context context, String s) {
        String result = "";

        try {
            if (s == null)
                return "";
            if (s.equals(""))
                return "";

            Date date = parseDateWithGMT(s, ConstantApp.SERVER_DATETIME_INPUT_FORMAT);

            long val = date.getTime();
            long curent = new Date().getTime();


            if (TimeUnit.MILLISECONDS.toSeconds(curent) - TimeUnit.MILLISECONDS.toSeconds(val) < 0)
                //result = s.replace("T", " ").replace("Z", "");
                result = "Now";
            else if (TimeUnit.MILLISECONDS.toSeconds(curent) - TimeUnit.MILLISECONDS.toSeconds(val) < 60) {
                //  result = (TimeUnit.MILLISECONDS.toSeconds(curent) - TimeUnit.MILLISECONDS.toSeconds(val)) + " seconds ago";
                result = "Now";
            } else if (TimeUnit.MILLISECONDS.toSeconds(curent) - TimeUnit.MILLISECONDS.toSeconds(val) >= 60
                    && TimeUnit.MILLISECONDS.toMinutes(curent) - TimeUnit.MILLISECONDS.toMinutes(val) < 60) {
                result = TimeUnit.MILLISECONDS.toMinutes(curent) - TimeUnit.MILLISECONDS.toMinutes(val) + " min ago";
            } else if (TimeUnit.MILLISECONDS.toMinutes(curent) - TimeUnit.MILLISECONDS.toMinutes(val) >= 60
                    && TimeUnit.MILLISECONDS.toHours(curent) - TimeUnit.MILLISECONDS.toHours(val) < 24) {
                long hour = TimeUnit.MILLISECONDS.toHours(curent) - TimeUnit.MILLISECONDS.toHours(val);
                result = hour + (hour == 1 ? " hour" : " hours") + " ago";
            } else if (TimeUnit.MILLISECONDS.toHours(curent) - TimeUnit.MILLISECONDS.toHours(val) >= 24) {
                long d = TimeUnit.MILLISECONDS.toDays(curent) - TimeUnit.MILLISECONDS.toDays(val);
                result = d + (d == 1 ? " day" : " days") + " ago";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result == null ? "" : result;
    }*/


    public static Date parseDateWithGMT(String date, String format) {
        if (TextUtils.isEmpty(date)) {
            return new Date();
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return (Date) simpleDateFormat.parse(date);
        } catch (Exception e) {
            Log.e(e);
        }
        return new Date();
    }

    public static Date parseDate(String date, String format) {
        if (TextUtils.isEmpty(date)) {
            return new Date();
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return (Date) simpleDateFormat.parse(date);
        } catch (Exception e) {
            Log.e(e);
        }
        return new Date();
    }


    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "NoticeBoard-Image");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

      /*  public static String takePicture(Activity activity) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = getOutputMediaFile();
            Uri uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            activity.startActivityForResult(intent, Constant.REQUEST_TAKE_PHOTO);
            return file.getAbsolutePath();
        }*/

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Company Code", text);
        clipboard.setPrimaryClip(clip);
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        String path = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        //The above code will fail to get file path if the file is selected from google photos.
        //Then this will be the alternative approach
        if (path == null && contentUri.toString().startsWith("content://com.google.android.apps.photos.content")) {
            Bitmap requiredImage = null;
            //Below key should be used for saving the newly downloaded image path as
            //photoIdentificationKey as Key parameter and newly generated path as Value.
            //An additional check should be implemented on your storage that whether any local
            //path exists for corresponding photoIdentificationKey, if yes then use the existing
            //local path else download new image.
            String photoIdentificationKey = Uri.parse(contentUri.getLastPathSegment()).getLastPathSegment();
            BitmapFactory.Options options = new BitmapFactory.Options();
            InputStream inputStream;

            try {
                inputStream = context.getContentResolver().openInputStream(contentUri);
                requiredImage = BitmapFactory.decodeStream(inputStream, null, options);

                //Save the newly downloaded image to your isolated storage and return the path
                //on which this new image has been saved.

                inputStream.close();
                path = saveBitmap(context, String.valueOf(Calendar.getInstance().getTimeInMillis()), requiredImage);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return path;
    }

    public static File writeToExternal(Context context, String path, String newName) {
        try {
            File file = new File(path); //Get file location from external source
            InputStream is = new FileInputStream(context.getFilesDir() + File.separator + newName); //get file location from internal
            OutputStream os = new FileOutputStream(file); //Open your OutputStream and pass in the file you want to write to
            byte[] toWrite = new byte[is.available()]; //Init a byte array for handing data transfer
            android.util.Log.i("Available ", is.available() + "");
            int result = is.read(toWrite); //Read the data from the byte array
            android.util.Log.i("Result", result + "");
            os.write(toWrite); //Write it to the output stream
            is.close(); //Close it
            os.close(); //Close it
            android.util.Log.i("Copying to", "" + context.getExternalFilesDir(null) + File.separator + newName);
            android.util.Log.i("Copying from", context.getFilesDir() + File.separator + newName + "");
            return file;
        } catch (Exception e) {

            Toast.makeText(context, "File write failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show(); //if there's an error, make a piece of toast and serve it up
        }

        return null;
    }

   /* public static boolean isGooglePlayServicesAvailable(Context context){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }*/


    public static <T> byte[] parseObjectToByteArray(T object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            return yourBytes;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return null;
    }
}