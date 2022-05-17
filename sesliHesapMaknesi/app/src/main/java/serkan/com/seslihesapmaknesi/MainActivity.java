package serkan.com.seslihesapmaknesi;

        import android.content.Intent;
        import android.speech.RecognizerIntent;
        import android.speech.tts.TextToSpeech;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    EditText sayi1, sayi2;
    TextToSpeech tts;
    TextView txt1;
    String dinlenen,islemturu;
    int sonuc;
    double sonuc2;

    static final int recordCheckNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sayi1 = (EditText) findViewById(R.id.editText);
        sayi2 = (EditText) findViewById(R.id.editText2);
        txt1 = (TextView) findViewById(R.id.textView);
        sayi1.requestFocus();

        tts = new TextToSpeech(this, this);
        tts.setLanguage(new Locale("tr", "TR"));
    }


    @Override
    public void onInit(int status) {

    }

    public void dinle(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Konuş..!");
        startActivityForResult(intent, recordCheckNumber);
    }

    public void konus(String islem){
        tts.speak(sayi1.getText().toString()+islem+sayi2.getText().toString()+"sonucu"+txt1.getText().toString(), TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == recordCheckNumber && resultCode == RESULT_OK) {

            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            dinlenen = results.get(0);
            if(dinlenen.equalsIgnoreCase("Topla")){
                sonuc=Integer.valueOf(sayi1.getText().toString())+Integer.valueOf(sayi2.getText().toString());
                txt1.setText(String.valueOf(sonuc));
                konus("artı");
            }
            else if(dinlenen.equalsIgnoreCase("Çıkar")){
                sonuc=Integer.valueOf(sayi1.getText().toString())-Integer.valueOf(sayi2.getText().toString());
                txt1.setText(String.valueOf(sonuc));
                konus("eksi");
            }
            else if(dinlenen.equalsIgnoreCase("Çarp")){
                sonuc=Integer.valueOf(sayi1.getText().toString())*Integer.valueOf(sayi2.getText().toString());
                txt1.setText(String.valueOf(sonuc));
                konus("çarpı");
            }
            else if(dinlenen.equalsIgnoreCase("bölme")){
                sonuc2=Double.valueOf(sayi1.getText().toString())/Double.valueOf(sayi2.getText().toString());
                txt1.setText(String.format("%.2f",sonuc2));
                konus("bölü");
            }else{
                Toast.makeText(this, "Hatalı Giriş", Toast.LENGTH_SHORT).show();;
            }

            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
