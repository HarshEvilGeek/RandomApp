package com.example.zaas.pocketbanker.fragments;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zaas.pocketbanker.R;
import com.example.zaas.pocketbanker.adapters.ChatBotAdapter;
import com.example.zaas.pocketbanker.models.local.ChatBotUIItem;
import com.example.zaas.pocketbanker.utils.Constants;

/**
 * Created by zaraahmed on 3/26/16.
 */
public class ChatBotFragment extends Fragment
{

    private static String LOG_TAG = ChatBotFragment.class.getSimpleName();

    private static Map<String, String> questionAnswerMap = new HashMap<>();
    static {
        questionAnswerMap.put("Whom do I contact for getting a Loan ?", "Loan Department");
        questionAnswerMap.put("Why is Akhil crazy ?", "Nooo one knows!!!");

    }

    Stack<ChatBotUIItem> mUiItems = new Stack<>();

    RecyclerView chatBotRecyclerView;
    ChatBotAdapter mAdapter;
    EditText chatBox;
    Button sendChatButton;
    final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.chat_bot_fragment_layout, container, false);
        chatBotRecyclerView = (RecyclerView) rootView.findViewById(R.id.chat_bot_recycler_view);
        chatBotRecyclerView.setScrollContainer(true);
        sendChatButton = (Button) rootView.findViewById(R.id.send_text_button);
        sendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                handleSendMsgClick();
            }
        });

        chatBox = (EditText) rootView.findViewById(R.id.message_editText);
        chatBox.setHint("Type a question...");
        chatBox.requestFocus();

        mAdapter = new ChatBotAdapter(getActivity(), null);
        chatBotRecyclerView.setAdapter(mAdapter);
        chatBotRecyclerView.setLayoutManager(new LinearLayoutManager(chatBotRecyclerView.getContext()));

        getActivity().setTitle("Chat Bot");
        return rootView;
    }

    private void handleSendMsgClick()
    {
        final String message = chatBox.getText().toString();
        ChatBotUIItem question = new ChatBotUIItem(Constants.CHAT_BOT_TYPE_QUESTION, message,
                System.currentTimeMillis());
        mUiItems.push(question);
        mAdapter.setUiItems(mUiItems);
        mAdapter.notifyDataSetChanged();
        chatBox.getText().clear();
        chatBox.setHint("Type a question...");
        chatBotRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {

                String answer = "Sorry can't answer this";

                if (questionAnswerMap.containsKey(message)) {
                    answer = questionAnswerMap.get(message);
                }

                ChatBotUIItem answerUIItem = new ChatBotUIItem(Constants.CHAT_BOT_TYPE_ANSWER, answer, System
                        .currentTimeMillis());
                mUiItems.push(answerUIItem);

                mAdapter.setUiItems(mUiItems);
                mAdapter.notifyDataSetChanged();

            }
        }, 2000);

    }

}
