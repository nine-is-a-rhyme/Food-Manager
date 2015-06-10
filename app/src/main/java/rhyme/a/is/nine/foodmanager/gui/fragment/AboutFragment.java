package rhyme.a.is.nine.foodmanager.gui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rhyme.a.is.nine.foodmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);

        TextView attr1 = (TextView) view.findViewById(R.id.tw_attr1);
        attr1.setMovementMethod(LinkMovementMethod.getInstance());

        TextView attr2 = (TextView) view.findViewById(R.id.tw_attr2);
        attr2.setMovementMethod(LinkMovementMethod.getInstance());

        TextView attr3 = (TextView) view.findViewById(R.id.tw_attr3);
        attr3.setMovementMethod(LinkMovementMethod.getInstance());

        TextView attr4 = (TextView) view.findViewById(R.id.tw_attr4);
        attr4.setMovementMethod(LinkMovementMethod.getInstance());

        TextView attr5 = (TextView) view.findViewById(R.id.tw_attr5);
        attr5.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }


}
