

package vn.chapp.vn24h.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.chapp.vn24h.MainApp;
import vn.chapp.vn24h.R;
import vn.chapp.vn24h.models.service.Service;


public final class CommonUtils {

    private static final String TAG = CommonUtils.class.getSimpleName();
    private final static DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0");

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static String getAddrLatLng(Context context, double lat, double lng) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses = null; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                return addresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static Dialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Calendar formatYYYYMMDDHHmmssToCalendar(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static String formatCalendarToDDMMYYYY(Calendar calendar) {
        if (calendar == null) return "";
        SimpleDateFormat sdfFormatDDMMYY = new SimpleDateFormat("dd-MM-yyyy");
        return sdfFormatDDMMYY.format(calendar.getTime());
    }

    public static String formatCalendarToYYYYMMDD(Calendar calendar) {
        if (calendar == null) return "";
        SimpleDateFormat sdfFormatDDMMYY = new SimpleDateFormat("yyyy-MM-dd");
        return sdfFormatDDMMYY.format(calendar.getTime());
    }

    public static String formatCalendarToYYYYMM(Calendar calendar){
        if (calendar == null) return "";
        SimpleDateFormat sdfFormatYYYYMM = new SimpleDateFormat("yyyy-MM");
        return sdfFormatYYYYMM.format(calendar.getTime());
    }

    public static String formatCalendarToYYYYMMSlash(Calendar calendar){
        if (calendar == null) return "";
        SimpleDateFormat sdfFormatYYYYMM = new SimpleDateFormat("MM/yyyy");
        return sdfFormatYYYYMM.format(calendar.getTime());
    }

    public static String formatCurrentChatTime(){
        SimpleDateFormat sdfFormatYYYYMM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdfFormatYYYYMM.format(Calendar.getInstance().getTime());
    }

    public static Calendar formatYYYYMMToCalendar(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar formatDDMMYYYYToCalendar(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static Calendar formatYYYYMMDDToCalendar(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static String formatCalendarToYYYYMMddHHmmSlash(Calendar calendar){
        if (calendar == null) return "";
        SimpleDateFormat sdfFormatYYYYMM = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        return sdfFormatYYYYMM.format(calendar.getTime());
    }

    public static Calendar formatYYYYMMDDHHmmToCalendar(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String parseMoney(Object money) {
        try {
            double number = Double.parseDouble(money.toString());
            money = decimalFormat.format(number).replaceAll(",", ".");
        } catch (Exception e) {
            return "";
        }
        return money.toString();
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String formatCalendarToHHmm(Calendar calendar) {
        if (calendar == null) return "";
        SimpleDateFormat sdfFormatDDMMYY = new SimpleDateFormat("HH:mm");
        return sdfFormatDDMMYY.format(calendar.getTime());
    }

    public static Calendar formatHHmmToCalendar(String time) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            cal.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static String formatTimeServer(String inFormat, String outFormat, String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(inFormat);
        try {
            SimpleDateFormat sdfFormatDDMMYY = new SimpleDateFormat(outFormat);
            return sdfFormatDDMMYY.format(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date formatDateTimeToDate (String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, hh:mm:ss Z yyyy");

        Date date = new Date();
        try {
            date = inputFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatStringToDateforScheduled (String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddd MMM dd HH':'mm':'ss 'GMT+07:00' yyyy", Locale.getDefault());
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        Date date = new Date();
        try {
//            date = dateFormat.parse(strDate);
            date = inputFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateToStringforScheduled (Date dateInput) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddd MMM dd HH':'mm':'ss 'GMT+07:00' yyyy", Locale.getDefault());
//        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

        String strDate = "";
        try {
//            strDate = dateFormat.format(dateInput);
            strDate = inputFormat.format(dateInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String formatDateToDayddHHyyyyHHmm(Date date) {

        String strDate = "";
        if (date == null) return strDate;

        try {
            Calendar calendarInput = Calendar.getInstance();
            calendarInput.setTime(date);

            Calendar calendarInputWithoutTime = Calendar.getInstance();
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTimeInMillis(calendarInputWithoutTime.getTimeInMillis());

            calendarInputWithoutTime.set(Calendar.DAY_OF_MONTH, calendarInput.get(Calendar.DAY_OF_MONTH));
            calendarInputWithoutTime.set(Calendar.MONTH, calendarInput.get(Calendar.MONTH));
            calendarInputWithoutTime.set(Calendar.YEAR, calendarInput.get(Calendar.YEAR));
            Date dateInputWithoutTime = calendarInputWithoutTime.getTime();

            Date dateNow = calendarNow.getTime();
            calendarNow.add(Calendar.DATE, -1);
            Date dateYesterday = calendarNow.getTime();
            calendarNow.add(Calendar.DATE, 2);
            Date dateTomorrow = calendarNow.getTime();

            SimpleDateFormat sdfFormatDDMMYY = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            if(dateInputWithoutTime.equals(dateTomorrow)) {
                strDate = String.format("Ngày mai, %s",sdfFormatDDMMYY.format(date));
            } else if(dateInputWithoutTime.equals(dateYesterday)) {
                strDate += String.format("Hôm qua, %s", sdfFormatDDMMYY.format(date));
            } else if(dateInputWithoutTime.equals(dateNow)){
                strDate = String.format("Hôm nay, %s", sdfFormatDDMMYY.format(date));
            } else {
                strDate = sdfFormatDDMMYY.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String formatDateSendServer (String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddd MMM dd HH':'mm':'ss 'GMT+07:00' yyyy", Locale.getDefault());
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        try {
            date = inputFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFormat.format(date);
    }

    public static String formatTimeSendServer (String strDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("ddd MMM dd HH':'mm':'ss 'GMT+07:00' yyyy", Locale.getDefault());
        SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");

        Date date = new Date();
        try {
            date = inputFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFormat.format(date);
    }

    public static Date formatServerToDate (String strDate, String strTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        Date date = new Date();
        try {
            date = inputFormat.parse(strDate + " " + strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDateServerToView (String strDate) {
        if(strDate.contains("0000-00-00")) return "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = new Date();
        String outputStr = "";
        try {
            date = inputFormat.parse(strDate);
            outputStr = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStr;
    }

    public static String getServiceName(int idService) {
        if(MainApp.newInstance().getListService()==null) return "";
        Service service = null;
        for(Service s : MainApp.newInstance().getListService()) {
            if(s.getId()==idService) {
                service = s;
            }
        }
        if(service!=null && service.getName()!=null) {
            return service.getName();
        }
        return "";
    }

    public static String formatDateServerToViewWallet (String strDate) {
        if(strDate.contains("0000-00-00")) return strDate;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

        Date date = new Date();
        String outputStr = strDate;
        try {
            date = inputFormat.parse(strDate);
            outputStr = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStr;
    }

    public static void openGoogleMap(Context context, Double latitude, Double longitude) {
        try {
            String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%f,%f", latitude, longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
        }
    }
}
