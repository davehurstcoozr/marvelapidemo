package com.marvelapps.marveldemo.utils;

/**
 * Created by Dave
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.marvelapps.marveldemo.constants.Consts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Dave on 2015-06-24.
 */
public class Common {

    /**
     * Represents a failed index search.
     */
    public static final int INDEX_NOT_FOUND = -1;
    public static String TAG="COMMON";
    private static String externalFilesDir=null;
    private static String COOKIESID = "APPCOOKIES";
    private static Context appContext=null;
    private static SharedPreferences cookies=null;


    //Not recommended - try to pass in the context instead
    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        Common.appContext = appContext;
    }

    public static PackageManager getPackageManager() {
        if (appContext==null) return null;
        return appContext.getPackageManager();
    }

    public static int getCookieInt(Context context, String key, int defaultValue) {
        String s = getCookie(context, key, null);
        if (s==null || s.isEmpty()) {
            return defaultValue;
        }

        return Integer.valueOf(s);

    }

    public static boolean getCookieBool(Context context, String key, boolean defaultValue) {
        String s = getCookie(context, key, null);
        if (s==null || s.isEmpty()) {
            return defaultValue;
        }

        return (s.toLowerCase().equals("true") || s.equals("1"));

    }

    public static String getCookie(Context context, String key) {
        return getCookie(context, key, "");
    }
    public static String getCookie(Context context, String key, String defaultValue) {
        if (cookies==null) {
            cookies = context.getSharedPreferences(COOKIESID, 0);
        }
        String s = cookies.getString(key,defaultValue);
        return s;
    }

    public static void setCookie(Context context, String key, String value) {
        if (cookies==null) {
            cookies = context.getSharedPreferences(COOKIESID, 0);
        }
        SharedPreferences.Editor editor = cookies.edit();
        editor.putString(key, value);
        editor.commit(); // Commit the edits!

    }

    public static boolean deleteFile(String filePath)
    {

        try {
            // delete the original file
            File file = getFile(filePath);
            if (file==null || !file.exists()) return false;
            file.delete();
            return true;

        }
        catch (Exception err) {
            Log.e(TAG, err.getMessage());
            return false;
        }
    }

    public static boolean moveFile(String filePathFrom, String filePathTo) {
        if (copyFile(filePathFrom, filePathTo)) {
            return deleteFile(filePathFrom);
        }
        else {
            return false;
        }
    }

    public static boolean copyFile(String filePathFrom, String filePathTo) {
        File fileFrom = getFile(filePathFrom);
        File fileTo = getFile(filePathTo);
        if (fileFrom.getAbsolutePath().equals(fileTo.getAbsolutePath())) {
            //Same paths
            return false;
        }
        if (!fileFrom.exists()) return false;
        try {
            InputStream in = new FileInputStream(fileFrom);
            OutputStream out = new FileOutputStream(fileTo);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            return true;
        }
        catch (Exception err) {
            err.printStackTrace();
            return false;
        }

    }

    public static boolean fileExists(String filePath)
    {
        File file = getFile(filePath);
        return file.exists();
    }

    /**
     * Translates any file path (i.e. with special xs:/ etc into an absolute path that you can use for other libraries, image loaders etc
     * @param filepath
     * @return
     */
    public static String getFilePath(String filepath)
    {
        File f = getFile(filepath);
        if (f==null) return null;
        return f.getAbsolutePath();
    }

    public static String getFileExtension(String filepath)
    {
        return filepath.substring(filepath.lastIndexOf(".")+1); //for jpg,png
    }

    public static String getFileExtension(File file)
    {
        String filepath = file.getAbsolutePath();
        return filepath.substring(filepath.lastIndexOf(".")+1); //for jpg,png
    }

    public static String getFileMimeType(File file)
    {
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtension(file).toLowerCase());
        return mimeType;
    }
    public static String getFileMimeType(String filepath)
    {
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(filepath.toLowerCase());
        return mimeType;
    }


    public static String addSlashToEndPathIfNeeded(String path) {
        String sLast = String.valueOf(path.charAt(path.length() - 1));
        if (sLast.equals("/")) return path;
        if (sLast.equals("\\")) return path;
        return path + "/";
    }


    public static File getNewImageFile(String filepath) {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image=null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }
        catch (IOException error)
        {
            Log.e(TAG, "getNewImageFile ERROR " + error.getMessage());
        }

        return image;
    }

    public static File getFile(String filepath, boolean makeParentFolders)
    {
        File file = getFile(filepath);
        if (file!=null && file.getParentFile()!=null && !file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /**
     * Gets a file object using special paths such as xs:/ userdata:/ xsphotos:/ app-files:/ etc...
     * @param filepath
     * @return
     */
    public static File getFile(String filepath)
    {
        //http://developer.android.com/training/basics/data-storage/files.html
        if (filepath.indexOf(":/")!=-1)
        {
            filepath = filepath.replace(Consts.PATH_EXTERNALSTORAGEDIR, Environment.getExternalStorageDirectory().getAbsolutePath()+"/");
            filepath = filepath.replace(Consts.PATH_USERDATA, Environment.getDataDirectory().getAbsolutePath()+"/");
            filepath = filepath.replace(Consts.PATH_EXTERNALPUBLICSTORAGE_PHOTOS, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/");
            if (filepath.indexOf(Consts.PATH_APP_EXTERNALFILES)==0)
            {
                if (appContext == null)
                {
                    throw new Error("To use app-files:/ you need to set Common.appContext first with your app context - Do this on app init");
                }

                filepath = filepath.replace(Consts.PATH_APP_EXTERNALFILES, appContext.getExternalFilesDir(null) +"/");
            }

        }

        File f=null;
        try {
            f = new File(filepath);
        }
        catch (Error error)
        {
            Log.d(TAG, "getFile FAILED "+error.getMessage());
        }

        return f;
    }

    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     *
     * <pre>
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not empty and not null
     * @since 3.0 Changed signature from isNotEmpty(String) to isNotEmpty(CharSequence)
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }


    /**
     * NOTE - this method is faster than android String replace (for big strings like XML)
     * (Copy of method from apache StringUtils (for some reason the class was not found at runtime)
     *
     * <p>Replaces all occurrences of a String within another String.</p>
     *
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     *
     * <pre>
     * StringUtils.replace(null, *, *)        = null
     * StringUtils.replace("", *, *)          = ""
     * StringUtils.replace("any", null, *)    = "any"
     * StringUtils.replace("any", *, null)    = "any"
     * StringUtils.replace("any", "", *)      = "any"
     * StringUtils.replace("aba", "a", null)  = "aba"
     * StringUtils.replace("aba", "a", "")    = "b"
     * StringUtils.replace("aba", "a", "z")   = "zbz"
     * </pre>
     *
     * @see #replace(String text, String searchString, String replacement, int max)
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for, may be null
     * @param replacement  the String to replace it with, may be null
     * @return the text with any replacements processed,
     *  {@code null} if null String input
     */
    public static String replace(final String text, final String searchString, final String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    /**
     * NOTE - this method is faster than android String replace (for big strings like XML)
     * (Copy of method from apache StringUtils (for some reason the class was not found at runtime)
     * <p>Replaces a String with another String inside a larger String,
     * for the first {@code max} values of the search String.</p>
     *
     * <p>A {@code null} reference passed to this method is a no-op.</p>
     *
     * <pre>
     * StringUtils.replace(null, *, *, *)         = null
     * StringUtils.replace("", *, *, *)           = ""
     * StringUtils.replace("any", null, *, *)     = "any"
     * StringUtils.replace("any", *, null, *)     = "any"
     * StringUtils.replace("any", "", *, *)       = "any"
     * StringUtils.replace("any", *, *, 0)        = "any"
     * StringUtils.replace("abaa", "a", null, -1) = "abaa"
     * StringUtils.replace("abaa", "a", "", -1)   = "b"
     * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
     * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
     * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
     * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
     * </pre>
     *
     * @param text  text to search and replace in, may be null
     * @param searchString  the String to search for, may be null
     * @param replacement  the String to replace it with, may be null
     * @param max  maximum number of values to replace, or {@code -1} if no maximum
     * @return the text with any replacements processed,
     *  {@code null} if null String input
     */
    public static String replace(final String text, final String searchString, final String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == INDEX_NOT_FOUND) {
            return text;
        }
        final int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= max < 0 ? 16 : max > 64 ? 64 : max;
        final StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != INDEX_NOT_FOUND) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String makeFirstCharUppercase(String s) {
        if (s==null) return null;
        if (s.length()<=1) return s.toUpperCase();
        s = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
        return s;
    }

    /**
     * Useful to return the absolute path i.e. after intent to pick an image from the gallery
     * http://stackoverflow.com/questions/28002907/copy-picked-image-through-intent-action-pick-to-a-file
     * @param cxt
     * @param uri
     * @return
     */
    public static File getAbsoluteFileFromUri(Context cxt, Uri uri) {

        Cursor cursor = cxt.getContentResolver().query(uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
        cursor.moveToFirst();

        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        String selectedImagePath = cursor.getString(idx);
        cursor.close();

        return new File(selectedImagePath);
    }

    //Basic encryption - do not use for secure
    public static String getEncryptedValueBasic(String s) {
        return getEncryptedValueBasic(s, false, false);
    }

    public static String getEncryptedValueBasic(String s, boolean bCaseInSensitive, boolean bFirst8DigitsOnly) {

        if (isEmpty(s)) return "";
        if (bCaseInSensitive) s = s.toUpperCase();
        String result = null;

        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            result = hexString.toString();

            if (isNotEmpty(result) && bFirst8DigitsOnly) result = result.substring(0, 8);
            if (isNotEmpty(result) && bCaseInSensitive) result = result.toUpperCase();

            return result;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }



        return result;

    }


    public static void socialShare(Context context, String titleSubject, String body) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = body;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titleSubject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getFirstName(String fullname) {
        if (Common.isEmpty(fullname)) return fullname;
        return fullname.split(" ")[0];
    }


}
