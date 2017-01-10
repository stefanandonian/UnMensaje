package net.conciencia.mensajeandroid.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import net.conciencia.mensajeandroid.Adapters.MessageListAdapter;
import net.conciencia.mensajeandroid.ContentLoaders.MessageLoader;
import net.conciencia.mensajeandroid.R;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link MessageListInteraction}
 * interface.
 */
public class MessageListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private MessageListInteraction mListener;
    boolean isRefreshing;

    SwipeRefreshLayout fragmentSwipeRefreshLayout;
    ListView mListView;
    ListAdapter mListAdapter;
    MessageLoader messageLoader;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessageListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageLoader = new MessageLoader(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_message_list, container, false);
        mListView = (ListView)rootView.findViewById(R.id.lv_sermon_list);
        setOnRefreshLayout(rootView);
        onRefresh();
        return rootView;
    }

    private void setOnRefreshLayout(View rootView) {
        fragmentSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.messagelist_SwipeRefreshLayout);
        fragmentSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageListInteraction) {
            mListener = (MessageListInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MessageListInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onSermonSelected(messageLoader, position);
    }

    @Override
    public void onRefresh() {
        isRefreshing = true;
        fragmentSwipeRefreshLayout.setRefreshing(true);
        AsyncTask<Void, Void, Boolean> refreshSermons = new UpdateMessageTask();
        refreshSermons.execute();
    }

    public void onRefreshComplete(){
        fragmentSwipeRefreshLayout.setRefreshing(false);
        isRefreshing = false;
        mListAdapter = new MessageListAdapter(getContext(), messageLoader.getParcelable_messages());
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MessageListInteraction {
        void onSermonSelected(MessageLoader messageLoader, int sermonIndex);
    }

    public class UpdateMessageTask extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            return messageLoader.loadMessagesFromUnMensajeALaConcienciaRssFeed();
        }

        @Override
        protected void onPostExecute(Boolean successful) {
            super.onPostExecute(successful);
            onRefreshComplete();
        }
    }
}
