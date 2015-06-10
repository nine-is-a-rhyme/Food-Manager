package rhyme.a.is.nine.foodmanager.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import rhyme.a.is.nine.foodmanager.R;

/**
 * Created by David on 10.06.2015.
 */
public class AddRecipeActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_input);

    Intent myIntent = getIntent();

  }
}
