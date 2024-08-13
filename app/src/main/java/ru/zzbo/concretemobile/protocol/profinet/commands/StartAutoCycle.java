package ru.zzbo.concretemobile.protocol.profinet.commands;

import static ru.zzbo.concretemobile.utils.Constants.globalFactoryState;
import static ru.zzbo.concretemobile.utils.Constants.mPlayer;
import static ru.zzbo.concretemobile.utils.Constants.retrieval;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.db.DBUtilUpdate;
import ru.zzbo.concretemobile.utils.Constants;


public class StartAutoCycle {

    public boolean checkProc(Context context) {
        if (
                (retrieval.getHopper11RecipeValue() == 0) && (retrieval.getHopper12RecipeValue() == 0) &&
                (retrieval.getHopper21RecipeValue() == 0) && (retrieval.getHopper22RecipeValue() == 0) &&
                (retrieval.getHopper31RecipeValue() == 0) && (retrieval.getHopper32RecipeValue() == 0) &&
                (retrieval.getHopper41RecipeValue() == 0) && (retrieval.getHopper42RecipeValue() == 0) &&
                (retrieval.getCement1RecipeValue() == 0) && (retrieval.getCement1RecipeValue() == 0) &&
                (retrieval.getCement1RecipeValue() == 0) && (retrieval.getCement1RecipeValue() == 0) &&
                (retrieval.getWaterRecipeValue() == 0)
        ){
            Toast.makeText(context, "Не задан рецепт!", Toast.LENGTH_SHORT).show();
            Constants.mPlayer = MediaPlayer.create(context, R.raw.context_001_recepie_not_set);
            mPlayer.start();
            return false;
        }
        if (retrieval.isMixerCloseValue() == 0) {
            Toast.makeText(context, "Закройте смеситель!", Toast.LENGTH_SHORT).show();
            Constants.mPlayer = MediaPlayer.create(context, R.raw.context_002_close_mixer);
            mPlayer.start();
            return false;
        }
        if (retrieval.isMixerNotEmptyValue() == 1){
            Toast.makeText(context, "Смеситель не пуст!", Toast.LENGTH_SHORT).show();
            Constants.mPlayer = MediaPlayer.create(context, R.raw.context_003_mixer_not_empty);
            mPlayer.start();
            return false;
        }
        if (retrieval.getCurrentWeightDKValue() > 100) {
            Toast.makeText(context, "На весах конвейера лежит материал. Разгрузите весы!", Toast.LENGTH_SHORT).show();
            Constants.mPlayer = MediaPlayer.create(context, R.raw.context_004_dk_not_empty);
            mPlayer.start();
            return false;
        }
        if (retrieval.getCurrentWeightCementValue() > 50) {
            Toast.makeText(context, "На весах дозатора цемента лежит материал. Разгрузите весы!", Toast.LENGTH_SHORT).show();
            Constants.mPlayer = MediaPlayer.create(context, R.raw.context_005_dc_not_empty);
            mPlayer.start();
            return false;
        }
        if (retrieval.getCurrentWeightWaterValue() > 50) {
            Toast.makeText(context, "На весах дозатора воды лежит материал. Разгрузите весы!", Toast.LENGTH_SHORT).show();
            Constants.mPlayer = MediaPlayer.create(context, R.raw.context_006_dw_not_empty);
            mPlayer.start();

        }
        if (retrieval.getMixCounterValue() != 0) {
            Toast.makeText(context, "Счетчик циклов не обнулен!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
