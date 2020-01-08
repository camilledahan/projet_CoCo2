package fr.cocoteam.co2co2.view;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.viewmodel.ConnectionViewModel;

import static android.content.ContentValues.TAG;

public class ConnectionFragment extends Fragment implements View.OnClickListener {

    private ConnectionViewModel mViewModel;
    public SignInButton signInButton;
    public TextView connectionTextView;
    public TextView welcomeTextView;
    public ImageButton validateProfilImageButton;
    public Context context;

    private GoogleSignInClient googleSignInClient;

    public static ConnectionFragment newInstance() {
        return new ConnectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connection_fragment, container, false);
        context = container.getContext();

        signInButton = view.findViewById(R.id.sign_in_button);
        connectionTextView = view.findViewById(R.id.connexionTextView);
        welcomeTextView = view.findViewById(R.id.welcomeTextView);
        validateProfilImageButton = view.findViewById(R.id.validateProfilImageButton);

        //Hide welcome message
        welcomeTextView.setVisibility(View.INVISIBLE);
        validateProfilImageButton.setVisibility(View.INVISIBLE);

        //Design Button
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        //set listeners
        signInButton.setOnClickListener(this);
        validateProfilImageButton.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(context,gso);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConnectionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        updateUI(account);
        Log.e("User","Already connected");
        //Log.e("Connected","Token "+account.getIdToken());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.validateProfilImageButton:
                Log.e("OK","Go app !!");
        }
    }
    private void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
            Log.e("Connected","User Connected "+account.getEmail());
            Log.e("Connected","Token "+account.getIdToken());

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }

    }

    public void updateUI (GoogleSignInAccount googleSignInAccount){
        if (googleSignInAccount != null) {
            welcomeTextView.setText(getString(R.string.userConnected, googleSignInAccount.getDisplayName()));
            welcomeTextView.setVisibility(View.VISIBLE);
            validateProfilImageButton.setVisibility(View.VISIBLE);
            connectionTextView.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
        }

    }
}



