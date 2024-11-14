package com.esprit.wellnest.servicepub;

import android.content.Intent;
import android.os.Bundle;

import com.esprit.wellnest.databinding.ActivityIntroBinding;
import com.esprit.wellnest.ui.Event.MainActivity;


public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.introBtn.setOnClickListener(v-> startActivity(new Intent(IntroActivity.this, MainActivity.class)));

    }
}