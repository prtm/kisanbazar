package preetam.jecrc.kishaanbazaar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by preetam on 3/26/16.
 */
public class NetworkError extends Fragment {
    Button retry;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nerror, container, false);
        retry = (Button) v.findViewById(R.id.networkretry);
        progressBar = (ProgressBar) v.findViewById(R.id.networkprogress);
        Bundle b = getArguments();
        final String s = b.getString("url");


        final NetworkErrorInterface networkErrorInterface = (NetworkErrorInterface) getActivity();
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (s.equals("url1")) {
                    networkErrorInterface.networkRetry();
                } else {
                    networkErrorInterface.networkRetry2();
                }
            }
        });
        return v;
    }
}
