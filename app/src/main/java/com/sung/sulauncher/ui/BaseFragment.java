package com.sung.sulauncher.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.sung.sulauncher.common.ActivityContext;
import com.sung.sulauncher.common.UiHandler;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS;

public class BaseFragment extends Fragment implements ActivityContext, UiHandler.UiCallback {

    protected Handler mHandler;
    protected UiHandler mUiHandler;

    public BaseFragment() {
        // Default Constructor is necessary!
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }

    /**
     * Return the LoaderManager for this fragment, creating it if needed.
     */
    public LoaderManager getSupportLoaderManager() {
        return getActivity() != null ? getActivity().getLoaderManager() : null;
    }

    /**
     * Return the FragmentManager for this fragment, creating if if needed.
     */
    public FragmentManager getSupportFragmentManager() {
        return getActivity() != null ? getActivity().getFragmentManager() : null;
    }

    @Override
    public boolean handleUiMessage(Message msg, int what, boolean enabled) {
        return false;
    }

    /**
     * If this fragment does not have retain state, and have already set a view with
     * {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, Bundle)}
     * this method is used to retrieve a specified child view.
     *
     * @return The view if found or null otherwise.
     * @see #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, Bundle)
     * @see #onViewCreated(View, Bundle)
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findView(@IdRes int id) {
        if (getView() != null) {
            return (T) getView().findViewById(id);
        }
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUiHandler = new UiHandler(this);
        mHandler = mUiHandler.getHandler();
    }

    /**
     * Set whether the activity do response to Back Button Pressed Event.
     * Default is true.
     */
    protected boolean enableBackPressed() {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Try to add Back Button Event listener.
        if (view != null && enableBackPressed()) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onFragmentResume();
    }

    /**
     * Callback to be invoked when the fragment needs to be resume without depends on activity's lifecycle.
     * With this callback, UI update can be done.
     */
    public void onFragmentResume() {
        mUiHandler.setEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        onFragmentPause();
    }

    /**
     * Callback to be invoked when this fragment needs to be paused independent on activity's lifecycle.
     */
    public void onFragmentPause() {
    }

    /**
     * Callback to be invoked when the fragment monitored the System back button pressed.
     * With this callback, exits prompt dialog can be shown.
     *
     * @return true if this fragment consumed this action, otherwise false.
     */
    public boolean onFragmentBackPressed() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUiHandler != null) {
            mUiHandler.setEnabled(false);
        }
        mUiHandler = null;
        mHandler = null;
    }

    /**
     * To hide keypad
     */
    protected void hideKeypad() {
        final View v = getActivity().getCurrentFocus();
        if (v != null) {
            final InputMethodManager imm = (InputMethodManager) getActivityContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), HIDE_NOT_ALWAYS);
        }
    }

    protected void hideKeyBoard(){
        InputMethodManager imm =  (InputMethodManager)getActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * show keyboard
     */
    protected  void showKeyBoard (View view) {
        InputMethodManager imm = (InputMethodManager) getActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    protected boolean isKeyBoardShown() {
        InputMethodManager imm = (InputMethodManager)getActivityContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm == null ? false : imm.isActive();
    }
}
