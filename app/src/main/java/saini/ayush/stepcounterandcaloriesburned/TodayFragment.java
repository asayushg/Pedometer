package saini.ayush.stepcounterandcaloriesburned;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodayFragment extends Fragment{
    public TextView textView, start, pause;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.today_fragment_layout, container, false);
        textView = (TextView) view.findViewById(R.id.stepView);
        start = (TextView) view.findViewById(R.id.start);
        pause = (TextView) view.findViewById(R.id.pause);
       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
       if(sharedPreferences.getBoolean("start",false))start();
        return view;
    }

    public void setTextView(String value) {

        textView.setText(value);
    }

    public void start(){
        start.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.VISIBLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("start",true);
        editor.apply();
    }

    public void pause(){
        pause.setVisibility(View.INVISIBLE);
        start.setVisibility(View.VISIBLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("start",false);
        editor.apply();

    }
}
