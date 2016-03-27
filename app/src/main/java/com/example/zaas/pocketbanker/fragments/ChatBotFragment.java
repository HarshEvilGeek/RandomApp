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
    private static Map<StringKey, String> keywordsMap = new HashMap<>();

    static {
        questionAnswerMap.put("Whom do I contact for getting a Loan?".toLowerCase(), "Loan Department");
        questionAnswerMap.put("Why is Akhil crazy?".toLowerCase(), "Nooo one knows!!!");
        questionAnswerMap.put("Where can I use Pockets?".toLowerCase(),
                "Pockets can be used with merchants that are listed in Recharge/shop section.");
        questionAnswerMap.put("I have lost my card what should I do?".toLowerCase(), "Contact us immediately.");
        questionAnswerMap.put("Card is about to expire what should I do?".toLowerCase(),
                "We will send a new card immediately on expiry, unless you want to change the type of card.");
    }

    static {
        keywordsMap.put(new StringKey("hi"), "Hello, how can I help you?");
        keywordsMap.put(new StringKey("hello"), "Hello, how can I help you?");
        keywordsMap.put(new StringKey("watsup"), "Hello, how can I help you?");
        keywordsMap.put(new StringKey("sup"), "Hello, how can I help you?");
        keywordsMap
                .put(new StringKey("pockets"),
                        "Create a Pocket account and enjoy unlimited exciting offers. Check out the Pockets section of the app");
        keywordsMap.put(new StringKey("apply", "home", "loan"),
                "You can apply for home loan online at https://loans.icicibank.com/home-loan.html");
        keywordsMap
                .put(new StringKey("apply", "personal", "loan"),
                        "You can find information about personal loan here http://www.icicibank.com/Personal-Banking/loans/personal-loan/index.page");
        keywordsMap.put(new StringKey("apply", "credit", "card"),
                "You can apply for credit card online at https://loans.icicibank.com/credit-card.html");
        keywordsMap
                .put(new StringKey("apply", "savings", "account"),
                        "You can open a Savings Account online at http://www.icicibank.com/Personal-Banking/account-deposit/savings-account/apply-now.page");
        keywordsMap
                .put(new StringKey("open", "savings", "account"),
                        "You can open a Savings Account online at http://www.icicibank.com/Personal-Banking/account-deposit/savings-account/apply-now.page");
        keywordsMap
                .put(new StringKey("open", "account"),
                        "You can open a Savings Account online at http://www.icicibank.com/Personal-Banking/account-deposit/savings-account/apply-now.page");
        keywordsMap.put(new StringKey("feedback"), "Visit our feeback page http://www.icicibank.com/feedback.page");
        keywordsMap.put(new StringKey("complaint"),
                "Submit a complaint http://www.icicibank.com/complaints/complaints.page");
        keywordsMap
                .put(new StringKey("service", "request"),
                        "To raise a service request visit http://www.icicibank.com/Personal-Banking/insta-banking/internet-banking/list-of-service-requests.page");
        keywordsMap.put(new StringKey("offers"), "Browse to the Offers section of the app to see exciting offers");
        keywordsMap.put(new StringKey("transfer", "funds"), "Browse to the Transfer Funds section of the app");
        keywordsMap.put(new StringKey("nearby", "branch"),
                "Check out our new ATM/Branch locator in the app for easily reaching the nearest ICICI Bank branch");
        keywordsMap.put(new StringKey("nearby", "atm"),
                "Check out our new ATM/Branch locator in the app for easily reaching the nearest ICICI Bank ATM");
        keywordsMap.put(new StringKey("stolen", "card"),
                "Contact ICICI Bank Customer Care to report stolen/lost Credit Card");
        keywordsMap.put(new StringKey("lost", "card"),
                "Contact ICICI Bank Customer Care to report stolen/lost Credit Card");
    }

    final Handler handler = new Handler();
    Stack<ChatBotUIItem> mUiItems = new Stack<>();
    RecyclerView chatBotRecyclerView;
    ChatBotAdapter mAdapter;
    EditText chatBox;
    Button sendChatButton;

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
        final String question = chatBox.getText().toString();
        final ChatBotUIItem questionUIItem = new ChatBotUIItem(Constants.CHAT_BOT_TYPE_QUESTION, question,
                System.currentTimeMillis());
        mUiItems.push(questionUIItem);
        mAdapter.setUiItems(mUiItems);
        mAdapter.notifyDataSetChanged();
        chatBox.getText().clear();
        chatBox.setHint("Type a question...");
        chatBotRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {

                String answer = "Sorry, I didn't understand that";

                if (questionAnswerMap.containsKey(question.toLowerCase())) {
                    answer = questionAnswerMap.get(question);
                }
                else {
                    for (StringKey key : keywordsMap.keySet()) {
                        if (question.toLowerCase().contains(key.str1) && question.toLowerCase().contains(key.str2)) {
                            answer = keywordsMap.get(key);
                        }
                    }
                }

                ChatBotUIItem answerUIItem = new ChatBotUIItem(Constants.CHAT_BOT_TYPE_ANSWER, answer, System
                        .currentTimeMillis());
                mUiItems.push(answerUIItem);

                mAdapter.setUiItems(mUiItems);
                mAdapter.notifyDataSetChanged();

            }
        }, 2000);

    }

    private static class StringKey
    {
        private String str1;
        private String str2;
        private String str3;

        public StringKey()
        {
            this.str1 = "";
            this.str2 = "";
            this.str3 = "";
        }

        public StringKey(String str1)
        {
            this.str1 = str1.toLowerCase();
            this.str2 = "";
            this.str3 = "";
        }

        public StringKey(String str1, String str2)
        {
            this.str1 = str1.toLowerCase();
            this.str2 = str2.toLowerCase();
            this.str3 = "";
        }

        public StringKey(String str1, String str2, String str3)
        {
            this.str1 = str1.toLowerCase();
            this.str2 = str2.toLowerCase();
            this.str3 = str3.toLowerCase();
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj != null && obj instanceof StringKey) {
                StringKey s = (StringKey) obj;
                return str1.equals(s.str1) && str2.equals(s.str2) && str3.equals(s.str3);
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            return (str1 + str2 + str3).hashCode();
        }
    }
}
