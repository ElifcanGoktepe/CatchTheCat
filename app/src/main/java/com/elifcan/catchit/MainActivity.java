package com.elifcan.catchit;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timeText;
    TextView scoreText;
    int score;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // onCreate metodu ile uygulama açılır açılmaz oluşacak aksiyonlar başlatılır.
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        timeText = (TextView) findViewById(R.id.timeText); // id'sini kullanarak zaman ekranının tanımlanaması
        scoreText = (TextView) findViewById(R.id.scoreText); // id'si ile score ekranının tanımlanması
        // 9 kere kedi görselinin grid layout'a eklenmesi
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        // Görsellerden bir array oluşturulması
        imageArray = new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};

        hideImages(); // Tüm görsellerin gizlenmesi

        score = 0;

        new CountDownTimer(10000,1000){ // 10'dan geriye 1'er 1'er zaman geri sayımı

            @Override
            public void onTick(long millisUnitFinished){
                timeText.setText("Time : " + millisUnitFinished/1000); // Kalan zamanın TextView'de güncellenmesi
            }
            @Override
            public void onFinish(){ // Uygulama bittiğinde gerçekleşecek aksiyonlar

                timeText.setText("Time Off");
                handler.removeCallbacks(runnable); // Zaman bittiğinde uygulamanın durdurulması
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE); //  imageArray'de bulunanan tüm görsellerin tekrar görünmez olması
                }
                // Restart sorgusunu kullanıcıya sormak için bir pop-up ın oluşturulması
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart");
                alert.setCancelable(false); // ekran dışına basıldığında ekranın kapanmasının önlenmesi
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // restart
                        Intent intent = getIntent(); // Mevcut aktivitenin intent'ini al
                        /**
                         * Intent, Android'de uygulamalar arası veya uygulama içi bileşenlerin iletişim kurmasını
                         * sağlayan, veri taşıyabilen ve işlem başlatabilen bir mesaj nesnesidir.
                         *
                         * getIntent() metodu, bir aktivitenin kendisine gönderilen Intent nesnesini alarak,
                         * bu Intent aracılığıyla taşınan verilere ve talimatlara erişmesini sağlar.
                         */
                        finish(); // Mevcut aktivitenin kapatılması
                        startActivity(intent); // Aktivitenin yeniden başlatılması
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    /**
                     * new DialogInterface.OnClickListener(): Bu, bir anonim DialogInterface.OnClickListener nesnesi oluşturur.
                     * Bu nesne, kullanıcının düğmeye tıkladığında ne olacağını tanımlayan bir arabirimdir.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // MainActivity aktivitesinde "Game Over!" mesajını kısa bir süre görüntüleyen bir Toast mesajı oluşturulması
                        // Toast.LENGTH_SHORT belirlerken mesajın yaklaşık 2 sn görünmesini sağlarken, .show() metodu Toast nesnesini ekranda gösterir
                        Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show(); // pop-up'ın ekranda gösterilmesi
            }
        }.start(); // Metot içinde tanımlı aksiyonların başlatılması


    }
    public void increaseScore(View view){ // Görsellere tıklandığında scorun 1 artılmasını sağlayan method, onClick XML Attribute'una tanımlanmıştır
        score ++; // scorun 1 artılması
        scoreText.setText("Score : " + score); // artırılmış skorun score tablosunda gösterilmesi
    }

    public void hideImages(){ // Görsellerin gizlenmesi

        handler = new Handler(); // Runable kontrolü için handler nesnesinin tanımlanması
        runnable = new Runnable() { // İş parçacığının oluşturulması için nesne tanımlanması
            /**
             * Runnable'ı kullanarak, uygulamanızın ana iş parçacığını (UI thread) engellemeden arka
             * planda işlemler yapabilirsiniz. Bu, uygulamanızın daha akıcı ve hızlı çalışmasını sağlar.
             */
            @Override
            public void run() { // runnable nesnesi çalıştırıldığında gerçekleecek aksiyonlar
                for (ImageView image : imageArray) { // imageArraydeki elemanların hepsinin gizlenmesi
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random(); // rastgele nesnesinin oluştuulması
                int i = random.nextInt(9); // 9  adet nesnemin olduğu için 9'a kadar olan sayıların rasgele seçilmesi
                imageArray[i].setVisibility(View.VISIBLE); // Array'de ki elemanların restgele seçilmesi ve görünür yapılması
                handler.postDelayed(this,500); // 0.5 sn'de bir aksiyonun tekrar etmesi
            }
        };
        handler.post(runnable); // handlerın 0.5 saniyede bir runnableda tanımlı aksiyonları çalıştırması
    }
}