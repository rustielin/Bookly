package com.example.rustie.bookly.Fragments;

import com.sun.istack.internal.Nullable;

public class NewPostFragment extends Fragment {
    private View v;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        v = inflater.inflate(R.layout.newpostfragment_mail.xml);
        return v;
    }
}