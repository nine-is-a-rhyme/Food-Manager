package rhyme.a.is.nine.foodmanager.gui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by martinmaritsch on 06/05/15.
 */
public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TextView bestBeforeDate;
    private Activity activity;

    public DatePickerDialogFragment() {
        // nothing to see here, move along
    }

    public void setBestBeforeDateTextView(TextView view) {
        this.bestBeforeDate = view;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        return new DatePickerDialog(activity,
                this, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setCalendar(cal);
        bestBeforeDate.setText(sdf.format(cal.getTime()));
    }

}
