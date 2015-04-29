package rhyme.a.is.nine.foodmanager;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FridgeFragment extends ListFragment {

    private FragmentActivity myContext;

    private String items[];


    public FridgeFragment() {
        // Required empty public constructor
        items = new String[] {
                "Milch",
                "Salami",
                "Käse",
                "Eier"
        };
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_single_choice, items);

        /** Setting the array adapter to the listview */
        setListAdapter(adapter);
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_tab, menu);
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        Toast.makeText(getActivity(), getListView().getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        /** Setting the multiselect choice mode for the listview */
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        CharSequence text;

        int id = item.getItemId();


        switch (id) {
            case R.id.action_add:
                DialogFragment scannerDialog = new AddItemDialog();
                scannerDialog.show(myContext.getFragmentManager(), "test");
                return true;
            case R.id.action_edit:
                text = "Edit clicked!";
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                text = "Delete clicked!";
                Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
