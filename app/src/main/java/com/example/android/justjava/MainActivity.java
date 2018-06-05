package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {



    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // called when order is clicked
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox)findViewById(R.id.whippedCream);
        boolean hiswhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate);
        boolean haschocolate = chocolate.isChecked();

        EditText name = (EditText)findViewById(R.id.name);
        String name1 = name.getText().toString();

        int price = calculatePrice(hiswhippedCream, haschocolate);
        String priceMessage = createOrderSummary(name1, price, hiswhippedCream, haschocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name1);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
// returning total price

    private int calculatePrice (boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;

        if (addWhippedCream){
            basePrice = basePrice +1;
        }
         if (addChocolate){
            basePrice = basePrice +2;
        }
        int price = quantity * basePrice;
        return price;
    }



// called when plus is clicked
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot have more than 100 coffees ", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity +1;
        displayQuantity(quantity);
    }

    // called when minus is clicked
    public void decrement(View view) {

        if (quantity == 1){
            Toast.makeText(this, "You cannot have less than 1 coffees ", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    // method displays the given quantity value on the screen

    private void displayQuantity(int numberOfCoffees){
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }



    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: " + name;
        priceMessage+= "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage+= "\nAdd Chocolate? " + addChocolate;
         priceMessage =priceMessage + "\nQuantity: " +quantity;
         priceMessage = priceMessage + "\nTotal: GHC" + price;
         priceMessage = priceMessage + "\n" + getString(R.string.thankYou);
         return priceMessage;
    }


}
