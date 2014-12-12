package com.help.media.mediah3lp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.help.media.mediah3lp.R;
import com.help.media.mediah3lp.User;
import com.vk.sdk.dialogs.VKShareDialog;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexey.bukin on 12.12.2014.
 */
public class WelcomeFragment extends ListFragment {

    public interface Callback {
        void onLoginButtonClick();
    }

    private final List<User> users = new ArrayList<User>();
    private ArrayAdapter<User> listAdapter;

    private Callback callback;
    private Button loginButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (Callback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listAdapter = new ArrayAdapter<User>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, users) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                final User user = getItem(position);

                ((TextView) view.findViewById(android.R.id.text1)).setText(user.getName());

                String birthDateStr = "Не задано";

                DateTime dt = user.getBirthDate();

                if (dt != null) {
                    birthDateStr = dt.toString(DateTimeFormat.forPattern(user.getDateFormat()));
                }

                ((TextView) view.findViewById(android.R.id.text2)).setText(birthDateStr);
                return view;

            }
        };
        setListAdapter(listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onLoginButtonClick();
            }
        });

        return view;
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        if (listAdapter != null)
            listAdapter.notifyDataSetChanged();
    }

    public void setLoginVisible(boolean loginVisible) {
        loginButton.setVisibility(loginVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        final User user = listAdapter.getItem(position);

        new VKShareDialog()
                .setText("Я только что кликнул по " + user.getName())
                .setAttachmentLink("Отправлено из MediaHLPR", "Такие дела")
                .setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                    @Override
                    public void onVkShareComplete(int postId) {
                        //контент отправлен
                    }

                    @Override
                    public void onVkShareCancel() {
                        //отмена
                    }
                }).show(getFragmentManager(), "VK_SHARE_DIALOG");
    }

}