package com.example.dragos.tasker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Domain.Person;
import Domain.TaskArray;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private TextView mError;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private Query muser;

    public void login(View v){
        mError.setText("");
        String email=mEmailView.getText().toString();
        String pass=mPasswordView.getText().toString();
        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(pass))
            mError.setText("Complete all fields");
        else
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.i("login", "success");
                    if(!task.isSuccessful()){
                        mError.setText("Incorect email or password.");
                    }
                    else{
                        finish();
                    }
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mError=(TextView)findViewById(R.id.errorTW);
        mAuth= FirebaseAuth.getInstance();
        final LoginActivity that=this;
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){

                    Log.i("login", "user");
                    Log.i("login",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    final String mail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    muser=TaskArray.getInstance().getReference("Persons");
                    muser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                for(DataSnapshot e:dataSnapshot.getChildren()){
                                    Person p=e.getValue(Person.class);
                                    if(p.getAddress().equals(mail)) {
                                        Log.i("login", "person");
                                        TaskArray.person = p;
                                        Log.i("login", p.getName());
                                        return;
                                    }
                                }
                                onCancelled(null);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("login", "really");
                            mError.setText("User not found.");
                        }
                    });
                }
            }
        });
        Button b=(Button)findViewById(R.id.email_sign_in_button);
    }

}

